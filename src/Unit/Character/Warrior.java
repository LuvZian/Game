package Unit.Character;

public class Warrior extends Character {
    public Warrior(){
        super("전사", 100, 50, 500, 100, 5, true);
    }
    public double skill1(){
        System.out.println("스킬1번입니다!");
        double damage = power *1.2;
        return damage;
    }
}
