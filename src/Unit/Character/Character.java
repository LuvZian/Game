package Unit.Character;

import Unit.*;
import etc.Delay;

public class Character extends Unit{
    Delay dl = new Delay();
    int MP;
    public Character (String name , int power, int defense, int HP, int MP, int speed, boolean turn){
        this.name = name;
        this.power = power;
        this.defense = defense;
        this.HP = HP;
        this.MP = MP;   
        this.speed = speed;
        this.turn = turn;
    }
    public void focus(String monster){
        System.out.println(monster + "을 목표로 잡았다!");
    }
    public int attack(String Player, String Monster, int PlayerPower , int MonsterHp){
        System.out.println(Player + "가 "+ Monster+ "을 공격했다!");
        dl.Sleep();
        System.out.println(Player + "가 " + PlayerPower + "의 데미지를 입혔다.");
        dl.Sleep();
        MonsterHp = MonsterHp - PlayerPower;
        System.out.println(Monster + "의 남은 체력 :  " + MonsterHp);
        return MonsterHp;
    }
    public void attacked(String player, String monster){
        System.out.println(player + "가 공격당했다!");
    }
    public void kill(String name, int HP){
        if(HP == 0){
            System.out.println(name + "가 쓰러졌습니다!");
        }
        
    }
}
