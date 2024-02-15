import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import Unit.Monster;
import Unit.Player;
import Unit.Skill;
import Unit.Unit;
import etc.Delay;
import etc.GameManager;

public class Battle{
    GameManager GM = GameManager.getInstance();
    Scanner sc = new Scanner(System.in);
    Delay dl = new Delay();
    Unit unit = new Unit();
    Skill sk = new Skill();
    Start st = new Start();
    Monster monsters;
    Player player;
    int round = 1;
    int stage = 1;
    public boolean result = false;

    public Battle(){
        this.monsters = new Monster();
        this.player = new Player();
    }

    public boolean win() {
        for (int i = 0; i < monsters.randmonsters; i++) {
            if (monsters.appearmonster[i].HP > 0) // 하나의 몬스터라도 체력이 0 초과인 경우
                return false; // 아직 플레이어의 승리는 아님
        }
        return true; // 모든 몬스터가 죽었을 때 플레이어의 승리
    }
    
    public boolean lose(){
        for(int i = 0;i<GM.selectedplayer.length;i++){
            if(GM.selectedplayer[i].HP > 0)
                return false;
        }
        return true;
    }

    public void fight_start(){
        System.out.println("-전투 시작-");
        dl.Sleep();
        System.out.println("=== "+stage+" Stage ===");
        monsters.appear();// 몬스터 등장
        
    }

    public void fight_end() throws Exception{
        System.out.println("전투 종료! 수고하셨습니다!");
        if(win()==true){
            System.out.println("플레이어의 승리!");
        }else if(lose()==true){
            System.out.println("플레이어의 패배,,,");
        }
    }

    public void attackchoice(Player currentPlayer){ // 몬스터 선택 및 공격
        System.out.print(">> ");
        int choice = sc.nextInt();
        if(1<=choice && choice<=monsters.randmonsters){
            monsters.selectedMonsterIndex = monsters.appearmonster[choice-1]; // 선택된 몬스터
            if(monsters.selectedMonsterIndex.HP <=0){ // hp가 0인 몬스터 선택 시
                System.out.println("이미 죽은 몬스터입니다!");
                currentPlayer.turn=true;
                dl.Sleep();
            }else{ // 살아있는 몬스터 선택 시
                unit.focus(monsters.selectedMonsterIndex.name); 
                dl.Sleep();
                monsters.selectedMonsterIndex.HP = unit.attack(currentPlayer.name, monsters.selectedMonsterIndex.name, currentPlayer.power-monsters.selectedMonsterIndex.defense, monsters.selectedMonsterIndex.HP); 
                dl.Sleep();
            }
        }else{
            System.out.println("거긴 몬스터가 없어요!"); // 선택지 외를 입력시
            currentPlayer.turn=true;
            dl.Sleep();
        }
    }

    public LinkedList<Unit> unitspeed(){ // 스피드 비교 linkedlist 생성, 내림차순 정렬
        LinkedList<Unit> unitspeed = new LinkedList<Unit>();
        int maxIndex = Math.max(monsters.randmonsters, GM.selectedplayer.length);

        for(int i=0; i<maxIndex;i++){
            if(i < monsters.randmonsters && monsters.appearmonster[i].HP >0){
                unitspeed.add(monsters.appearmonster[i]);
            }
            if(i<GM.selectedplayer.length && GM.selectedplayer[i].HP > 0){
                unitspeed.add(GM.selectedplayer[i]);
            }
        }  
        Collections.sort(unitspeed,new Comparator<Unit>() {  
            public int compare(Unit a, Unit b){
                return b.speed - a.speed;
            }
        });
        return unitspeed;
    }

    public void turn(LinkedList<Unit> speedlist){ //현재 공격할 차례가 누구인지
        Monster currentMonster = null;
        Player currentPlayer = null;
        int i = 0;
        while(i!=speedlist.size()){
            Unit unit = speedlist.get(i); //filter
            if(unit instanceof Monster && unit.HP>0 && unit.turn){
                unit.turn = false;
                currentMonster = (Monster) unit;
                System.out.println(currentMonster.name+"의 턴입니다!");
                MonsterAttack(currentMonster);
                break;
            } else if (unit instanceof Player && unit.HP > 0 && unit.turn){
                unit.turn = false;
                currentPlayer = (Player) unit;
                System.out.println(currentPlayer.name+"의 턴입니다!"); 
                try {
                    PlayerAttack(currentPlayer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            else{
                i++;
            }
        }
        if(i ==speedlist.size()){
            for(int j = 0; j<speedlist.size();j++){
                Unit unit = speedlist.get(j);
                unit.turn = true;
            }
        }
    }

    public void Skillattackchoice(Player currentPlayer){ // 몬스터 선택 및 공격
        System.out.print(">> ");
        int choice = sc.nextInt();
        if(1<=choice && choice<=monsters.randmonsters){
            monsters.selectedMonsterIndex = monsters.appearmonster[choice-1]; // 선택된 몬스터
            if(monsters.selectedMonsterIndex.HP <=0){ // hp가 0인 몬스터 선택 시
                System.out.println("이미 죽은 몬스터입니다!");
                currentPlayer.turn=true;
                dl.Sleep();
            }else{ // 살아있는 몬스터 선택 시
                currentPlayer.focus(monsters.selectedMonsterIndex.name); 
                dl.Sleep();
                monsters.selectedMonsterIndex.HP = sk.skillattack(currentPlayer, monsters.selectedMonsterIndex);
                currentPlayer.MP =sk.restmp(currentPlayer);
                dl.Sleep();
            }
        }else{
            System.out.println("거긴 몬스터가 없어요!"); // 선택지 외를 입력시
            currentPlayer.turn=true;
            dl.Sleep();
        }
    }

    public void MonsterAttack(Monster currentMonster){ //몬스터 공격 시 , playerattack과 달리 선택지가 존재 X
        int randomplayer =(int)(Math.random()*GM.selectedplayer.length);
        player.targetplayerIndex = GM.selectedplayer[randomplayer];
        if(player.targetplayerIndex.HP <=0){
            System.out.println(currentMonster.name+"가 실수했다!");
            dl.Sleep();
            System.out.println("----------------------------------------------------------  --");
        }else{
            currentMonster.focus(player.targetplayerIndex.name);
            dl.Sleep();
            player.targetplayerIndex.HP = unit.attack(currentMonster.name, player.targetplayerIndex.name, currentMonster.power-player.targetplayerIndex.defense, player.targetplayerIndex.HP);
            dl.Sleep();
        }
    }

    

    public void PlayerAttack(Player currentPlayer) throws Exception{
            System.out.println("어떤 행동을 취할까요?");
            System.out.println("1. 일반 공격");
            System.out.println("2. 스킬 사용");
            System.out.println("3. 도망");
            System.out.println("4. 정보 확인");
            try {
                System.out.print(">> ");
                int move = sc.nextInt(); // 행동 선택
                switch(move){
                    case 1 : System.out.println("누구를 공격하시겠습니까?"); // 일반공격
                        for(int i = 0; i<monsters.randmonsters;i++){ // 몬스터 리스트 출력
                            if(monsters.appearmonster[i].HP<=0){
                                System.out.println("-- 사망 --");
                            }else{
                                System.out.println(i+1 +". "+ monsters.appearmonster[i].name); 
                            }
                        }
                        attackchoice(currentPlayer); // 공격할 몬스터 선택 및 공격격
                        break;
                    case 2 : if(currentPlayer.MP<40){//그 플레이어의 스킬 중 가장 낮은 MP 소모량
                            System.out.println("마나가 부족하여 스킬을 사옹할 수 없습니다!!!");
                            currentPlayer.turn=true;
                            break;
                        }else{
                            System.out.println("어떤 스킬을 사용하시겠습니까?");
                            sk.skillchoice(currentPlayer);
                            System.out.println("누구를 공격하시겠습니까?");
                            for(int i = 0; i<monsters.randmonsters;i++){ // 몬스터 리스트 출력
                                if(monsters.appearmonster[i].HP<=0){
                                    System.out.println("-- 사망 --");
                                }else{
                                    System.out.println(i+1 +". "+ monsters.appearmonster[i].name); 
                                }
                            }
                            Skillattackchoice(currentPlayer);
                            break;
                        }
                        
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
                            System.out.print(">> ");
                            int choices = sc.nextInt();
                            
                            switch(choices){
                                case 1: player.PartyInfo();
                                        currentPlayer.turn=true;
                                        break;
                                case 2 : monsters.MonsterInfo();
                                        currentPlayer.turn=true;
                                        break;
                                default : System.out.println("잘못된 선택지입니다!");
                                        currentPlayer.turn=true;
                                        break;
                            }
                        break;
                    default:    System.out.println("잘못된 선택지입니다!");
                                currentPlayer.turn=true;
                            break;
                    }
                } catch (InputMismatchException e) {
                    sc = new Scanner(System.in);
                    System.out.println("잘못된 선택지입니다!");
                    dl.Sleep();
                    PlayerAttack(currentPlayer);
                }
            
    }

    public void Next() throws Exception{   
        if(stage<5){
            System.out.println("다음 스테이지로 가시겠습니까?");
            System.out.println("1. 계속하기");
            System.out.println("2. 게임 종료");
            int choice = sc.nextInt();
            System.out.print(">> ");
            switch(choice){
                case 1 : 
                        stage++;
                        System.out.println(stage + " 스테이지로 이동");
                        battlefight();
                        break;
                case 2 : 
                        System.exit(0);
                        break;
                default : 
                        System.out.println("1, 2 중에서 선택해주세요!!");
                        dl.Sleep();
                        Next();
                        break;
            }
        }else{
            System.out.println("모든 스테이지를 클리어하셨습니다!");
        }
        
    }

    public boolean Restart() throws Exception{   
            
            System.out.println("다시 시작하시겠습니까?");
            System.out.println("1. 다시 시작");
            System.out.println("2. 게임 종료");
            int choice = sc.nextInt();
            System.out.print(">> ");
            switch(choice){
                case 1 : 
                        result = true;
                        break;
                case 2 : 
                        result = false;
                        System.exit(0);
                        break;
                default : 
                        System.out.println("1, 2 중에서 선택해주세요!!");
                        dl.Sleep();
                        Next();
                        break;
            }
            return result;
    }


    public void battlefight() throws Exception {
            fight_start();
            LinkedList<Unit> speedlist = unitspeed();
            System.out.println( "====Round" + round + "====");
            while(!win() && !lose()){
                turn(speedlist);
                round++;
            }
            fight_end();
            if(win()){
                round = 1;
                Next();
            }else if(lose()){
                Restart();
            }
    }
}