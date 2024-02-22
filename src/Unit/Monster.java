
package Unit;

import java.util.Scanner;

import etc.Logs;

public class Monster extends Unit{
    Scanner sc = new Scanner(System.in);
    public String nature;
    public int originalHP;

    public Monster() {
    }

    public Monster(String id, String name, int power, int defense, int HP, int speed, String nature){
        super(id, name, power, defense, HP, 0, speed);
        this.nature = nature;
        this.originalHP = HP;
    }

    public Monster(Monster mon){
        super(mon.id, mon.name, mon.power, mon.defense, mon.HP, 0, mon.speed);
        this.nature = mon.nature;
        this.originalHP = mon.originalHP;
    }

    public void info(Monster monster){
        super.info(monster);
        Logs.log("● 속성 : "+monster.nature);
    }

    public int getOriginalHP(){
        return this.originalHP;
    }
    
}
