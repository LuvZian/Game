package Unit;

public class Player extends Unit{

    public Player(String id, String name, int power, int defense, int HP, int MP, int speed){
        super(id, name, power, defense, HP, MP, speed);
    }

    public Player() {
        //TODO Auto-generated constructor stub
    }

    public boolean run(){
        if(Math.random()<=0.7){
            return true;
        }else{
            return false;
        }
    }

    public void Info(Player player) {
        System.out.println("이름 : "+player.name);
        System.out.println("공격력 : "+player.power);
        System.out.println("방어력 : "+player.defense);
        System.out.println("체력 : "+player.HP);
        System.out.println("마나 : "+player.MP);
        System.out.println("속도 : "+player.speed);
    }
}
