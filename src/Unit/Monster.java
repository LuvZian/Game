
package Unit;

import java.util.Scanner;

public class Monster extends Unit{
    Scanner sc = new Scanner(System.in);
    public int randmonsters = 0;
    public Monster[] appearmonster;
    public Monster selectedMonsterIndex;
    public String nature;

    public Monster() {
    }

    public Monster(String id, String name, int power, int defense, int HP, int speed, String nature){
        super(id, name, power, defense, HP, 0, speed);
        this.nature = nature;
    }

    public Monster(Monster mon){
        super(mon.id, mon.name, mon.power, mon.defense, mon.HP, 0, mon.speed);
        this.nature = mon.nature;
    }


    public void Info(Monster monster) {
        System.out.println("● 이름 : "+monster.name);
        System.out.println("● 공격력 : "+monster.power);
        System.out.println("● 방어력 : "+monster.defense);
        System.out.println("● 체력 : "+monster.HP);
        System.out.println("● 속도 : "+monster.speed);
        System.out.println("● 속성 :"+ monster.nature);
    }

    public void appear(){ // 몬스터 출현
        randmonsters = (int)(Math.random()*3)+3; // 몬스터 마리수 (3~5)
        appearmonster = new Monster[randmonsters]; // 나타난 몬스터 배열
        System.out.println(randmonsters+"마리 몬스터가 나타났다");
        dl.Sleep();
        for(int i = 0; i<randmonsters; i++){
            appearmonster[i] = randommonster(); //나타난 몬스터 객체 생성
            System.out.println((i+1)+". "+appearmonster[i].name);
            dl.Sleep();
        }
    }

    public Monster randommonster(){//랜덤 몬스터 생성
        int randomIndex = 0;
        Monster mon = null;
        double randoms =  Math.random();
            if(randoms<=0.5){ //50% 확률로 마녀
                randomIndex = 0;
            }else if(0.5<randoms &&randoms<=0.8){ //30% 확률로 좀비
                randomIndex = 1;
            }else if(0.8<randoms &&randoms<=1){//20%확률로 악마
                randomIndex = 2;
            }else{
                
            }
            
            mon = new Monster(GM.monsterlist[randomIndex]);

            if (Math.random() <=0.1) {//10% 확률로 엘리트몹
                eliteMonster(mon);
            }
            if(Math.random()<=0.01){//1% 확률로 보스몹
                BossMonster(mon);
            }
            return mon;

    }

    public Monster eliteMonster(Monster mon){
        //엘리트 몬스터
        mon.name = "[Elite]"+mon.name;
        mon.power = (int) (mon.power*1.2);;
        mon.defense = (int) (mon.defense*1.2);;
        mon.HP = (int) (mon.HP*1.2);
        mon.speed = (int) (mon.speed*2);;
            return mon;
    }
    public Monster BossMonster(Monster mon){
        //보스몬스터
        mon.name = "[Boss]"+mon.name;
        mon.power = (int) (mon.power*1.5);;
        mon.defense = (int) (mon.defense*1.5);;
        mon.HP = (int) (mon.HP*1.5);
        mon.speed = (int) (mon.speed*2.5);;
            return mon;
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
