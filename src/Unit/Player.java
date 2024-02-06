package Unit;

import etc.Delay;

public class Player {
    Delay dl = new Delay();

    public String id;
    public String name;
    public int power;
    public int defense;   
    public int HP;
    public int MP;
    public int speed;
    public Boolean turn = true;    

    public Player(String id, String name, int power, int defense, int HP, int MP, int speed){
        this.id = id;
        this.name = name;
        this.power = power;
        this.defense = defense;
        this.HP = HP;
        this.MP = MP;
        this.speed = speed;
    }

    public Player() {
    }

    public boolean run(){
        if(Math.random()<=0.7){
            return true;
        }else{
            return false;
        }
    }

    public void Infoplayer(Player player) {
        System.out.println("이름 : "+player.name);
        System.out.println("공격력 : "+player.power);
        System.out.println("방어력 : "+player.defense);
        System.out.println("체력 : "+player.HP);
        System.out.println("마나 : "+player.MP);
        System.out.println("속도 : "+player.speed);
    }
}
