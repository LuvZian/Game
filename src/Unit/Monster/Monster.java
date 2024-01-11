
package Unit.Monster;

import Unit.Unit;
import etc.*;


public class Monster extends Unit{
    Delay dl = new Delay();
    
    public Monster (String name , int power, int defense, int HP, int speed, boolean turn){
        this.name = name;
        this.power = power;
        this.defense = defense;
        this.HP = HP;
        this.speed = speed;
        this.turn = turn;
    }
    public void focus(String player){
        System.out.println(player + "을 목표로 잡았다!");
    }
    public int attack(String Monster, String Player, int MonsterPower , int PlayerHp){
        System.out.println(Monster + "가 "+ Player+ "을 공격했다!");
        dl.Sleep();
        System.out.println(Monster + "가 " + MonsterPower + "의 데미지를 입혔다.");
        dl.Sleep();
        PlayerHp = PlayerHp - MonsterPower;
        System.out.println(Player + "의 남은 체력 :  " + PlayerHp);
        return PlayerHp;
    }
    public void Info(int number){
    }
    
}
