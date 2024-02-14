
package Unit;

import java.util.Scanner;

public class Monster extends Unit{
    Scanner sc = new Scanner(System.in);
    public int randmonsters = 0;
    public Monster[] appearmonster;
    public Monster selectedMonsterIndex;

    public Monster() {
    }

    public Monster(String id, String name, int power, int defense, int HP, int speed){
        super(id, name, power, defense, HP, 0, speed);
    }


    public void Info(Monster monster) {
        System.out.println("● 이름 : "+monster.name);
        System.out.println("● 공격력 : "+monster.power);
        System.out.println("● 방어력 : "+monster.defense);
        System.out.println("● 체력 : "+monster.HP);
        System.out.println("● 속도 : "+monster.speed);
    }

    public void appear(){ // 몬스터 출현
        randmonsters = (int)(Math.random()*3)+3; // 몬스터 마리수 (3~5)
        appearmonster = new Monster[randmonsters]; // 나타난 몬스터 배열
        System.out.println(randmonsters+"마리 몬스터가 나타났다");
        dl.Sleep();
        for(int i = 0; i<randmonsters; i++){
            int randomIndex = randommonster(); //몬스터 종류
            appearmonster[i] = new Monster(GM.monsterlist[randomIndex].id, GM.monsterlist[randomIndex].name, GM.monsterlist[randomIndex].power, GM.monsterlist[randomIndex].defense, GM.monsterlist[randomIndex].HP,GM.monsterlist[randomIndex].speed);
            //나타난 몬스터 객체 생성
            System.out.println((i+1)+". "+appearmonster[i].name);
            dl.Sleep();
        }
    }

    public int randommonster(){
        int randommonsterindex = 0;
            if(Math.random()<=0.5){
                randommonsterindex = 0;
            }else if(Math.random()<=0.3){
                randommonsterindex = 1;
            }else if(Math.random()<=0.2){
                randommonsterindex = 2;
            }else{
                randommonster();
            }
        return randommonsterindex;

    }

    public void MonsterInfo(){ // 몬스터 정보 확인
        for(int i = 0; i<randmonsters;i++){
            if(appearmonster[i].HP<=0){
                System.out.println("===사망===");
            }else{
                System.out.println((i+1) +". "+ appearmonster[i].name);
               }   
          }
          System.out.print(">> ");
          int choice = sc.nextInt();
          if(1<=choice && choice <=randmonsters){
              selectedMonsterIndex = appearmonster[choice-1]; // 정보확인을 원하는 몬스터
              Info(selectedMonsterIndex);
            }
    }

    
    
}
