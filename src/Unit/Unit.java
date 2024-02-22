package Unit;

import etc.GameManager;
import etc.Logs;

public class Unit {
    GameManager GM = GameManager.getInstance(); 

    public String id;
    public String name;
    public int power;
    public int defense;   
    public int HP;
    public int MP;
    public int speed;
    public Boolean turn = true; 
    public int originalHP;
    public int originalMP;

    public Unit(){
        
    }

    public Unit(String id, String name, int power, int defense, int HP, int MP, int speed) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.defense = defense;
        this.HP = HP;
        this.MP = MP;
        this.speed = speed;
        this.originalHP = HP;
        this.originalMP = MP;
    }

    public void focus(String target){
        Logs.log(target + "을 목표로 잡았다!");
    }

    public int normalattack(Unit attack, Unit defence){
        Logs.log(attack.name + "가 "+ defence.name+ "을 공격했다!");
        GM.sleep();
        if(attack.power >= defence.HP){
            Logs.log(attack.name + "가 " + defence.HP + "의 데미지를 입혔다.");
            defence.HP = 0;
            GM.sleep();
            Logs.log(defence.name + "의 남은 체력 :  " + defence.HP);
            GM.sleep();
            Logs.log(defence.name + "을 쓰러트렸습니다!");
            Logs.log("------------------------------------------------------------");
        }else if (attack.power<=defence.defense){
            Logs.log(attack.name + "는 "+ defence.name+ "에게 흠집도 내지 못했다!");
            GM.sleep();
        }else{
            Logs.log(attack.name + "가 " + attack.power + "의 데미지를 입혔다.");
            GM.sleep();
            defence.HP = defence.HP - attack.power;
            Logs.log(defence.name + "의 남은 체력 :  " + defence.HP);
            Logs.log("------------------------------------------------------------");
        }
        return defence.HP;
    }

    public void info(Unit unit) {
        Logs.log("==="+unit.name+" 정보===");
        Logs.log("● 이름 : "+unit.name);
        Logs.log("● 공격력 : "+unit.power);
        Logs.log("● 방어력 : "+unit.defense);
        Logs.log("● 체력 : "+unit.HP+"/"+unit.originalHP);
        if(unit instanceof Player){
            Logs.log("● 마나 : "+unit.MP+"/"+unit.originalMP);
        }
        Logs.log("● 속도 : "+unit.speed);
        
    }
}

