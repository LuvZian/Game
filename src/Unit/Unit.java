package Unit;

import etc.Database;
import etc.Delay;

public class Unit {

    Delay dl = new Delay();
    Database db;
    Party pt = new Party(db);

    public void focus(String target){
        System.out.println(target + "을 목표로 잡았다!");
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
        }else{
            System.out.println(attacker + "가 " + damage + "의 데미지를 입혔다.");
            dl.Sleep();
            HP = HP - damage;
            System.out.println(defender + "의 남은 체력 :  " + HP);
        }
        return HP;
    }
    
}

