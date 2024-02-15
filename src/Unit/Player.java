package Unit;

import java.util.Scanner;

public class Player extends Unit{
    public Skill[] skills;
    public Player targetplayerIndex;
    Scanner sc = new Scanner(System.in);
    Skill sk = new Skill();

    public Player() {
    }

    public Player(String id, String name, int power, int defense, int HP, int MP, int speed){
        super(id, name, power, defense, HP, MP, speed);
    }

    public Skill[] getskills(){
        return skills;
    }
    public void setskills(Skill[] skills){
        this.skills = skills;
    }

    public boolean run(){
        if(Math.random()<=0.7){
            return true;
        }else{
            return false;
        }
    }

    public void Info(Player player) throws Exception {
        System.out.println("===유닛 정보===");
        System.out.println("● 이름 : "+player.name);
        System.out.println("● 공격력 : "+player.power);
        System.out.println("● 방어력 : "+player.defense);
        System.out.println("● 체력 : "+player.HP);
        System.out.println("● 마나 : "+player.MP);
        System.out.println("● 속도 : "+player.speed);
            sk.SkillInfo(player);
    }

    public void PartyInfo() throws Exception{ //  파티원 정보
        for(int i = 0; i < GM.selectedplayer.length;i++){
            if(GM.selectedplayer[i].HP <= 0){
                System.out.println("===사망===");
            }else{
                System.out.println((i+1)+". " + GM.selectedplayer[i].name);
            }
        }
            System.out.print(">> ");
            int choice = sc.nextInt();
            if(1<=choice && choice<=GM.selectedplayer.length){
                targetplayerIndex = GM.selectedplayer[choice-1];
                Info(targetplayerIndex);
            }
    }
    
}
