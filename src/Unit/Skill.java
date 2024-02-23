package Unit;

import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import etc.GameManager;
import etc.Logs;

public class Skill {
    Scanner sc;
    GameManager GM = GameManager.getInstance();
    Monster monsters;
    Player player;
    public Skill selectedSkill;

    public String id;
    public String name;
    public int power;
    public int mp;
    public String nature;
    int damage = 0;
    
    public Skill(){
    }

    public Skill(String id, String name, int power, int mp, String nature) {
        this.id= id;
        this.name= name;
        this.power= power;
        this.mp= mp;
        this.nature = nature;
    }
    
    public void skillInfo(Player player) throws Exception{
        List<Skill> skills = GM.getSkill(player.id);
        Logs.log("===스킬 정보===");
        for(int i = 0; i<skills.size(); i++){
            Logs.log((i+1)+". "+skills.get(i).name);
            Logs.log("● 공격력 : "+skills.get(i).power);
            Logs.log("● MP : "+skills.get(i).mp);
            Logs.log("● 유형 : "+skills.get(i).nature);
            Logs.log("\n");
        }
    }
    public Skill skillchoice(Player player) throws Exception{//사용할 스킬 선택
        List<Skill> skills = GM.getSkill(player.id);
            try {
                for(int i = 0; i<skills.size(); i++){
                    System.out.println((i+1)+". "+skills.get(i).name+"(MP : "+skills.get(i).mp+")");
                }
                System.out.println(skills.size()+1 + ". 스킬 정보");
                    System.out.print(">> ");
                    sc = new Scanner(System.in);
                    int choiceSkill = sc.nextInt();
                    if(1<=choiceSkill && choiceSkill<=GM.skillList.size()){
                        selectedSkill = skills.get(choiceSkill-1);
                        Logs.log(selectedSkill.name+"을 선택하셨습니다! (MP : "+selectedSkill.mp+")");
                    }else if (choiceSkill == skills.size()+1){
                        skillInfo(player);
                        player.turn = true;
                    }else{
                        System.out.println("잘못된 선택지입니다!");
                        skillchoice(player);
                    }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 선택지입니다!");
                skillchoice(player);
            }
        return selectedSkill;
    }

    public int skillAttack(Player player, Monster monster){//attack 스킬 사용
        Logs.log(player.name + "가 "+ monster.name+"에게 "+selectedSkill.name+ "을 사용했다!");
        GM.sleep();
        damage = selectedSkill.power-monster.defense;
        if(damage >= monster.HP){
            Logs.log(player.name + "가 " + monster.HP + "의 데미지를 입혔다.");
            monster.HP = 0;
            GM.sleep();
            Logs.log(monster.name + "의 남은 체력 :  " + monster.HP);
            GM.sleep();
            Logs.log(monster.name + "을 쓰러트렸습니다!");
            Logs.log("------------------------------------------------------------");
        }else if(damage<=0){
            Logs.log(player.name+"은 "+monster.name+"에게 흠집도 내지 못했다!");
        }
        else{
            Logs.log(player.name + "가 " + damage + "의 데미지를 입혔다.");
            GM.sleep();
            monster.HP = monster.HP - damage;
            Logs.log(monster.name + "의 남은 체력 :  " + monster.HP);
            Logs.log("------------------------------------------------------------");
        }
        return monster.HP;
    }
    public int restMp(Player player){ //스킬 사용 후 남은 mp 계산
        player.MP = player.MP-selectedSkill.mp;
        return player.MP;
    }

    public int skillHeal(Player Healer, Player player){// Heal 스킬 사용
        
        GM.sleep();
        int heal = selectedSkill.power;
        if(player.HP + heal >= player.originalHP){
            Logs.log(Healer.name + "가 "+ player.name+"에게 "+selectedSkill.name+ "을 사용했다!");
            GM.sleep();
            Logs.log(Healer.name + "가 " + player.name +"를"+ (player.originalHP-player.HP) + "만큼 회복시켰다.");
            GM.sleep();
            player.HP = player.originalHP;
            Logs.log(player.name + "의 체력 :  " + player.HP);
            GM.sleep();
            Logs.log("------------------------------------------------------------");
        }else if(player.HP<=0){
            Logs.log("이미 죽은 파티원은 살릴 수 없어요,,,");
            Healer.turn = true;
        }else if (Healer.equals(player)){
            Logs.log("자기 자신은 회복시킬 수 없어요,,,");
            Healer.turn = true;
        }
        else{
            Logs.log(Healer.name + "가 "+ player.name+"에게 "+selectedSkill.name+ "을 사용했다!");
            GM.sleep();
            Logs.log(Healer.name + "가 " + player.name +"를"+ heal + "만큼 회복시켰다.");
            GM.sleep();
            player.HP = player.HP + heal;
            Logs.log(player.name + "의 남은 체력 :  " + player.HP);
            GM.sleep();
            Logs.log("------------------------------------------------------------");
        }
        return player.HP;
    }
    public void run(Player player){//run 스킬 사용
        Logs.log(player.name+"가"+selectedSkill.name+"을 시전했다!");
        if(Math.random()<=0.8){
            Logs.log("무사히 도망치셨습니다!");
            System.exit(0);
        }else{
            Logs.log("안타깝게도 실패하셨습니다!");
            Logs.log("------------------------------------------------------------");
        }
    }

    public void selfbuff(Player player) throws Exception{ //selfbuff 사용
        Logs.log(player.name + "가 " + selectedSkill.name+"을 시전했다!");
        player.HP += selectedSkill.power;
        player.power += selectedSkill.power;
        player.defense +=selectedSkill.power;
        player.info(player);
    }

    public void splash(Player player, Monster monsters[]){ //splash 스킬 사용
        Logs.log(player.name+"가" + selectedSkill.name+"을 시전했다!");
        Logs.log("모든 적에게 "+selectedSkill.power+"의 데미지가 들어갔다!");

        for(int i = 0; i< monsters.length;i++){
            if(monsters[i].HP <=selectedSkill.power){
                monsters[i].HP = 0;
            }else{
                monsters[i].HP = monsters[i].HP - selectedSkill.power;
            }
        }
    }

    public void resurrection(Player Healer, Player Dead){//resurrection 스킬 사용
        Logs.log(Healer.name + "가 "+ Dead.name+"에게 "+selectedSkill.name+ "을 사용했다!");
        if(Dead.HP >0){
            Logs.log("죽지 않은 사람에게는 시전할 수 없습니다!");
            Healer.turn = true;
        }else{
            Dead.HP = Dead.originalHP;
            Logs.log(Dead.name + "을 부활시키셨습니다!");
        }
    }
    
    public void special(Player player, Monster monster){ //special 스킬 사용
        if(Math.random()<=0.404){
            Logs.log("크리티컬!");
            selectedSkill.power = selectedSkill.power*404;
        }else{
        }
    }

    public int minMp(Player player) throws Exception{//플레이어 스킬 중 가장 소모값이 적은 스킬 출력
        int minMp = 0;
        List<Skill> skills = GM.getSkill(player.id);
        LinkedList<Skill> skillMp = new LinkedList<Skill>();

        for(int i = 0; i<skills.size();i++){
            skillMp.add(skills.get(i));
        }
        Collections.sort(skillMp, new Comparator<Skill>(){
            public int compare(Skill a, Skill b){
                return b.mp-a.mp;
            }
        });
        return minMp;
    }
}
