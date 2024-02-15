package Unit;

import java.util.Scanner;

import etc.Delay;
import etc.GameManager;

public class Skill {
    Scanner sc = new Scanner(System.in);
    GameManager GM = GameManager.getInstance();
    Delay dl = new Delay();
    Monster monsters;
    Player player;

    public String id;
    public String name;
    public int power;
    public int mp;
    public String nature;
    public Skill selectedSkill;

    int damage = 0;
    
    public Skill(){
    }

    public Skill(String id, String name, int power, int mp, String nature) {
        this.id=  id;
        this.name= name;
        this.power= power;
        this.mp= mp;
        this.nature = nature;
    }
    
    public void SkillInfo(Player player) throws Exception{
        Skill[] skills = GM.getskill(player.id);
        System.out.println("===스킬 정보===");
        for(int i = 0; i<skills.length; i++){
            System.out.println("● 이름 : "+skills[i].name);
            System.out.println("● 공격력 : "+skills[i].power);
            System.out.println("● MP : "+skills[i].mp);
            System.out.println("● 속성 : "+skills[i].nature);
        }
    }

    public Skill skillchoice(Player player) throws Exception{
        Skill[] skills = GM.getskill(player.id);
        for(int i = 0; i<skills.length; i++){
            System.out.println((i+1)+". "+skills[i].name);
        }
        System.out.print(">> ");
        int choiceskill = sc.nextInt();
        if(1<=choiceskill && choiceskill<=GM.skill_count){
            selectedSkill = skills[choiceskill-1];
            System.out.println(selectedSkill.name+"을 선택하셨습니다!");
        }
        return selectedSkill;
    }

    public int skillattack(Player player, Monster monster){
        System.out.println(player.name + "가 "+ monster.name+"에게 "+selectedSkill.name+ "을 사용했다!");
        dl.Sleep();
            damage = selectedSkill.power-monster.defense;
        if(damage >= monster.HP){
            System.out.println(player.name + "가 " + monster.HP + "의 데미지를 입혔다.");
            monster.HP = 0;
            dl.Sleep();
            System.out.println(monster.name + "의 남은 체력 :  " + monster.HP);
            dl.Sleep();
            System.out.println(monster.name + "을 쓰러트렸습니다!");
            System.out.println("------------------------------------------------------------");
        }else if(damage<=0){
            System.out.println(player.name+"은 "+monster.name+"에게 흠집도 내지 못했다!");
        }
        else{
            System.out.println(player.name + "가 " + damage + "의 데미지를 입혔다.");
            dl.Sleep();
            monster.HP = monster.HP - damage;
            System.out.println(monster.name + "의 남은 체력 :  " + monster.HP);
            System.out.println("------------------------------------------------------------");
        }
        return monster.HP;
    }
    public int restmp(Player player){
        player.MP = player.MP-selectedSkill.mp;
        return player.MP;
    }


}
