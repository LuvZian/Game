package Unit;

import java.util.List;
import java.util.Scanner;

import etc.Logs;

public class Player extends Unit{
    public List<Skill> skills;
    
    Scanner sc = new Scanner(System.in);
    Skill sk = new Skill();

    public String grade;

    public Player() {
    }

    public Player(String id, String name, int power, int defense, int HP, int MP, int speed, String grade){
        super(id, name, power, defense, HP, MP, speed);
        this.grade = grade;
    }

    public List<Skill> getSkills(){
        return skills;
    }
    public void setSkills(List<Skill> list){
        this.skills = list;
    }

    public void run(){
        if(Math.random()<=0.5){
            Logs.log("무사히 도망치셨습니다!");
            System.exit(0);
        }else{
            Logs.log("안타깝게도 실패하셨습니다!");
            Logs.log("------------------------------------------------------------");
        }
    }

    public void info(Player player) throws Exception {
            super.info(player);
    }
    
}
