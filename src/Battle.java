import java.util.InputMismatchException;
import java.util.Scanner;

import Unit.Monster;
import Unit.Player;
import Unit.Unit;
import etc.Database;
import etc.Delay;

public class Battle{
    Scanner sc = new Scanner(System.in);
    Delay dl = new Delay();
    Unit unit = new Unit();
    Database db;
    int randmonsters;
    Monster[] appearmonster;
    Monster monsters;
    Monster selectedMonsterIndex;
    Player player;


    public Battle(Database db){
        this.db = db;
        this.player = new Player();
        this.monsters = new Monster();
    }

    public void appear(){ // 몬스터 출현
        randmonsters = (int)(Math.random()*3)+3; // 몬스터 마리수 (3~5)
        appearmonster = new Monster[randmonsters]; // 나타난 몬스터 배열
        System.out.println(randmonsters+"마리 몬스터가 나타났다");
        dl.Sleep();
        for(int i = 0; i<randmonsters; i++){
            int randomIndex = (int)(Math.random()*db.monster_count); //몬스터 종류
            appearmonster[i] = new Monster(db.monsterlist[randomIndex].id, db.monsterlist[randomIndex].name, db.monsterlist[randomIndex].power, db.monsterlist[randomIndex].defense, db.monsterlist[randomIndex].HP,db.monsterlist[randomIndex].speed);
            //나타난 몬스터 객체 생성
            System.out.println((i+1)+". "+appearmonster[i].name);
            dl.Sleep();
        }
    }

    public boolean win(){ //승리 조건
        for(int i = 0; i<randmonsters; i++){ // 모든 몬스터 반복
            if(appearmonster[i].HP > 0) //나타난 몬스터의 HP가 0일떄
                return true;
        }
        return false;
    }

    public boolean lose(){
        if(db.playerlist[0].HP == 0){
            return true;
        }
        return false;
    }

    public void attackchoice(){ // 몬스터 선택 및 공격
        int choice = sc.nextInt();
        if(1<=choice && choice<=randmonsters){
            selectedMonsterIndex = appearmonster[choice-1]; // 선택된 몬스터
            if(selectedMonsterIndex.HP <=0){ // hp가 0인 몬스터 선택 시
                System.out.println("이미 죽은 몬스터입니다!");
                dl.Sleep();
            }else{ // 살아있는 몬스터 선택 시
                unit.focus(selectedMonsterIndex.name); 
                dl.Sleep();
                selectedMonsterIndex.HP = unit.attack(db.playerlist[0].name, selectedMonsterIndex.name, db.playerlist[0].power, selectedMonsterIndex.HP); 
                dl.Sleep();                                           
                db.playerlist[0].turn = false;
            }
        }else{
            System.out.println("거긴 몬스터가 없어요!"); // 선택지 외를 입력시
        }
    }

    public void Infochoice(){ // 몬스터 정보 확인
        for(int i = 0; i<randmonsters;i++){
            if(appearmonster[i].HP<=0){
                System.out.println("===사망===");
            }else{
                System.out.println((i+1) +". "+ appearmonster[i].name);
               }   
          }
                          
          int choice = sc.nextInt();
          if(1<=choice && choice <=randmonsters){
              selectedMonsterIndex = appearmonster[choice-1]; // 정보확인을 원하는 몬스터
              monsters.Info(selectedMonsterIndex);
        }
    }

    public void fight_start(){
        System.out.println("-전투 시작-");
        dl.Sleep();
        appear();// 몬스터 등장
    }

    public void fight_end() throws Exception{
        System.out.println("전투 종료! 수고하셨습니다!");
        if(win()==false){
            System.out.println("플레이어의 승리!");
        }else if(lose()==false){
            System.out.println("플레이어의 패배,,,");
        }
    }

    public void fight() throws Exception{
        while(win() || lose()){
            System.out.println("어떤 행동을 취할까요?");
            System.out.println("1. 일반 공격");
            System.out.println("2. 스킬 사용");
            System.out.println("3. 도망");
            System.out.println("4. 정보 확인");
            try {
                int move = sc.nextInt(); // 행동 선택
                switch(move){
                    case 1 : System.out.println("누구를 공격하시겠습니까?"); // 일반공격
                        for(int i = 0; i<randmonsters;i++){ // 몬스터 리스트 출력
                            if(appearmonster[i].HP<=0){
                                System.out.println("-- 사망 --");
                            }else{
                                System.out.println(i+1 +". "+ appearmonster[i].name); 
                            }
                        }
                        attackchoice(); // 공격할 몬스터 선택 및 공격격
                        break;
                    case 2 :
                        break;
                    case 3 : if(player.run()){ //도망치기
                            System.out.println("무사히 도망치셨습니다!");
                            System.exit(0);
                            } else{
                                System.out.println("안타깝게도 실패하셨습니다!");
                                break;
                            }
                    case 4 : System.out.println("누구의 정보를 확인할까요?"); // 정보확인
                            dl.Sleep();
                            System.out.println("1. 파티원");
                            dl.Sleep();
                            System.out.println("2. 몬스터");
                            int choices = sc.nextInt();
                            switch(choices){
                                case 1: 
                                        break;
                                case 2 : Infochoice();
                                        break;
                                default : System.out.println("잘못된 선택지입니다!");
                                        break;
                            }
                        break;
                    default:    System.out.println("잘못된 선택지입니다!");
                            break;
                    }
                } catch (InputMismatchException e) {
                    sc = new Scanner(System.in);
                    System.out.println("잘못된 선택지입니다!");
                    dl.Sleep();
                    fight();
                }
            
        } 
    }

    public void battlefight() {
        fight_start();
        try {
            
            fight();
            fight_end();
        } catch (Exception exception) {

        }
        

    }
}