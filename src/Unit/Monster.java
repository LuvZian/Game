
package Unit;

public class Monster extends Unit{

    public Monster(String id, String name, int power, int defense, int HP, int speed){
        super(id, name, power, defense, HP, 0, speed);
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
