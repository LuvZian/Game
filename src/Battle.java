import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Unit.Monster;
import Unit.Player;
import Unit.Unit;
import etc.Delay;
import etc.GameManager;

public class Battle{
    GameManager GM = GameManager.getInstance();
    Scanner sc = new Scanner(System.in);
    Delay dl = new Delay();
    Unit unit = new Unit();
    int randmonsters;
    Monster[] appearmonster;
    Monster monsters;
    Monster selectedMonsterIndex;
    Player targetplayerIndex;
    Player player;
    int round = 1;

    public Battle(){
        this.monsters = new Monster();
    }

    public void appear(){ // 몬스터 출현
        randmonsters = (int)(Math.random()*3)+3; // 몬스터 마리수 (3~5)
        appearmonster = new Monster[randmonsters]; // 나타난 몬스터 배열
        System.out.println(randmonsters+"마리 몬스터가 나타났다");
        dl.Sleep();
        for(int i = 0; i<randmonsters; i++){
            int randomIndex = (int)(Math.random()*GM.monster_count); //몬스터 종류
            appearmonster[i] = new Monster(GM.monsterlist[randomIndex].id, GM.monsterlist[randomIndex].name, GM.monsterlist[randomIndex].power, GM.monsterlist[randomIndex].defense, GM.monsterlist[randomIndex].HP,GM.monsterlist[randomIndex].speed);
            //나타난 몬스터 객체 생성
            System.out.println((i+1)+". "+appearmonster[i].name);
            dl.Sleep();
        }
    }
    public boolean win() {
        for (int i = 0; i < randmonsters; i++) {
            if (appearmonster[i].HP > 0) // 하나의 몬스터라도 체력이 0 초과인 경우
                return false; // 아직 플레이어의 승리는 아님
        }
        return true; // 모든 몬스터가 죽었을 때 플레이어의 승리
    }
    

    public boolean lose(){
        for(int i = 0;i<GM.selectedplayer.length;i++){
            if(GM.selectedplayer[i].HP > 0){
                return false;
            }
        }
        return true;
    }

    public void attackchoice(Unit currentUnit){ // 몬스터 선택 및 공격
        int choice = sc.nextInt();
        if(1<=choice && choice<=randmonsters){
            selectedMonsterIndex = appearmonster[choice-1]; // 선택된 몬스터
            if(selectedMonsterIndex.HP <=0){ // hp가 0인 몬스터 선택 시
                System.out.println("이미 죽은 몬스터입니다!");
                dl.Sleep();
            }else{ // 살아있는 몬스터 선택 시
                unit.focus(selectedMonsterIndex.name); 
                dl.Sleep();
                selectedMonsterIndex.HP = unit.attack(currentUnit.name, selectedMonsterIndex.name, currentUnit.power, selectedMonsterIndex.HP); 
                dl.Sleep();
            }
        }else{
            System.out.println("거긴 몬스터가 없어요!"); // 선택지 외를 입력시
        }
    }

    public void MonsterInfo(){ // 몬스터 정보 확인
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
    public void PartyInfo(){
        for(int i = 0; i < GM.selectedplayer.length;i++){
            if(GM.selectedplayer[i].HP <= 0){
                System.out.println("===사망===");
            }else{
                System.out.println((i+1)+". " + GM.selectedplayer[i].name);
            }
            int choice = sc.nextInt();
            if(1<=choice && choice<=GM.selectedplayer.length){
                targetplayerIndex = GM.selectedplayer[choice-1];
                player.Infoplayer(targetplayerIndex);
            }
        }
    }

    public void fight_start(){
        System.out.println("-전투 시작-");
        dl.Sleep();
        System.out.println( "====Round" + round + "====");
        appear();// 몬스터 등장
    }

    public void fight_end() throws Exception{
        System.out.println("전투 종료! 수고하셨습니다!");
        if(win()==true){
            System.out.println("플레이어의 승리!");
        }else if(lose()==true){
            System.out.println("플레이어의 패배,,,");
        }
    }

    public LinkedList<Integer> speedsort(){ // 스피드 비교 linkedlist 생성, 내림차순 정렬
        LinkedList<Integer> speedlist = new LinkedList<Integer>();
        for(int i = 0;i<randmonsters;i++){
            if(appearmonster[i].turn == true && appearmonster[i].HP >0){
                speedlist.add(appearmonster[i].speed);
            }
        }
        for(int i = 0; i<GM.selectedplayer.length;i++){
            if(GM.selectedplayer[i].turn == true && GM.selectedplayer[i].HP > 0){
                speedlist.add(GM.selectedplayer[i].speed);
            }
        }
        Collections.sort(speedlist,Collections.reverseOrder());
        return speedlist;
    }

    public void turn() throws Exception{ //현재 공격할 차례가 누구인지
        Unit currentUnit = null;        
        try {
            if(win()==true){
                speedsort().clear();
                System.out.println("플레이어의 승리!");
            }else if (lose()==true){
                speedsort().clear();
                System.out.println("플레이어의 패배,,,");
            }else{
                for(int i = 0; i<randmonsters;i++){
                    if(appearmonster[i].speed == speedsort().element() && appearmonster[i].HP>0){
                            System.out.println(appearmonster[i].name+"의 턴입니다!");
                            appearmonster[i].turn = false; 
                            currentUnit = appearmonster[i];
                            if(currentUnit.ischaracter()){
                                PlayerAttack(currentUnit);
                            }else{
                                MonsterAttack(currentUnit);
                            }
                        }
                    }
                    for(int i = 0; i<GM.selectedplayer.length;i++){
                        if(GM.selectedplayer[i].speed == speedsort().element() && GM.selectedplayer[i].HP>0){
                            System.out.println(GM.selectedplayer[i].name+"의 턴입니다!");
                            GM.selectedplayer[i].turn = false;  
                            currentUnit = GM.selectedplayer[i];
                            if(currentUnit.ischaracter()){
                                PlayerAttack(currentUnit);
                            }else{
                                MonsterAttack(currentUnit);
                            }
                        }
                    }
            }
        } catch (NoSuchElementException e) { // speedlist 배열에 남은 값이 없을때
            for(int i = 0;i<randmonsters;i++){
                if(appearmonster[i].HP >0){
                    appearmonster[i].turn = true;
                }
            }
            for(int i = 0; i<GM.selectedplayer.length;i++){
                if(GM.selectedplayer[i].HP > 0){
                    GM.selectedplayer[i].turn =true;
                }
            }
            round++;
        }
    }

    public void MonsterAttack(Unit currentUnit){
        int randomplayer =(int)(Math.random()*GM.selectedplayer.length);
        targetplayerIndex = GM.selectedplayer[randomplayer];
        if(targetplayerIndex.HP <=0){
            System.out.println(currentUnit.name+"가 실수했다!");
            dl.Sleep();
        }else{
            currentUnit.focus(targetplayerIndex.name);
            dl.Sleep();
            targetplayerIndex.HP = unit.attack(currentUnit.name, targetplayerIndex.name, currentUnit.power, targetplayerIndex.HP);
            dl.Sleep();
        }
    }

    public void PlayerAttack(Unit currentUnit) throws Exception{
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
                        attackchoice(currentUnit); // 공격할 몬스터 선택 및 공격격
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
                                case 1: PartyInfo();
                                        break;
                                case 2 : MonsterInfo();
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
                    PlayerAttack(currentUnit);
                }
            
    }


    public void battlefight() throws Exception {
        
            fight_start();
            while(!win() && !lose()){
                turn();
            }
            fight_end();

    }
}