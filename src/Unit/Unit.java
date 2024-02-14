package Unit;

import etc.Delay;
import etc.GameManager;

public class Unit {
    GameManager GM = GameManager.getInstance();
    Delay dl = new Delay();    
    Party pt = new Party();

    public String id;
    public String name;
    public int power;
    public int defense;   
    public int HP;
    public int MP;
    public int speed;
    public Boolean turn = true; 


    public Unit(String id, String name, int power, int defense, int HP, int MP, int speed) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.defense = defense;
        this.HP = HP;
        this.MP = MP;
        this.speed = speed;
        
    }
    public Unit(){
        
    }

    public void focus(String target){
        System.out.println(target + "을 목표로 잡았다!");
    }

    public int normalattack(Unit attack, Unit defence){
        System.out.println(attack.name + "가 "+ defence.name+ "을 공격했다!");
        dl.Sleep();
        if(attack.power >= defence.HP){
            System.out.println(attack.name + "가 " + defence.HP + "의 데미지를 입혔다.");
            defence.HP = 0;
            dl.Sleep();
            System.out.println(defence.name + "의 남은 체력 :  " + defence.HP);
            dl.Sleep();
            System.out.println(defence.name + "을 쓰러트렸습니다!");
            System.out.println("------------------------------------------------------------");
        }else if (attack.power<=defence.defense){
            System.out.println(attack.name + "는 "+ defence.name+ "에게 흠집도 내지 못했다!");
            dl.Sleep();
        }else{
            System.out.println(attack.name + "가 " + attack.power + "의 데미지를 입혔다.");
            dl.Sleep();
            defence.HP = defence.HP - attack.power;
            System.out.println(defence.name + "의 남은 체력 :  " + defence.HP);
            System.out.println("------------------------------------------------------------");
        }
        return defence.HP;
    }

    public int attack(String attacker, String defender, int damage , int HP){
        System.out.println(attacker + "가 "+ defender+ "을 공격했다!");
        dl.Sleep();
        if(damage >= HP){
            System.out.println(attacker + "가 " + HP + "의 데미지를 입혔다.");
            HP = 0;
            dl.Sleep();
            System.out.println(defender + "의 남은 체력 :  " + HP);
            dl.Sleep();
            System.out.println(defender + "을 쓰러트렸습니다!");
            System.out.println("------------------------------------------------------------");
        }else{
            System.out.println(attacker + "가 " + damage + "의 데미지를 입혔다.");
            dl.Sleep();
            HP = HP - damage;
            System.out.println(defender + "의 남은 체력 :  " + HP);
            System.out.println("------------------------------------------------------------");
        }
        return HP;
    }
}

