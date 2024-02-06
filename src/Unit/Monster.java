
package Unit;

import etc.*;


public class Monster {
    Delay dl = new Delay();

    public String id;
    public String name;
    public int power;
    public int defense;
    public int HP;
    public int speed;
    public Boolean turn = true;

    public Monster(String id, String name, int power, int defense, int HP, int speed){
        this.id = id;
        this.name = name;
        this.power = power;
        this.defense = defense;
        this.HP = HP;
        this.speed = speed;
    }

    public Monster() {
        //TODO Auto-generated constructor stub
    }

    public void Info(Monster monster) {
        System.out.println("이름 : "+monster.name);
        System.out.println("공격력 : "+monster.power);
        System.out.println("방어력 : "+monster.defense);
        System.out.println("체력 : "+monster.HP);
        System.out.println("속도 : "+monster.speed);
    }

    
    
}
