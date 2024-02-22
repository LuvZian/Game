package Process;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import Unit.Monster;
import Unit.Player;
import Unit.Skill;
import Unit.Unit;
import etc.GameManager;
import etc.Logs;

public class Battle{
    GameManager GM = GameManager.getInstance();
    Scanner sc;
    Unit unit = new Unit();
    Skill sk = new Skill();
    Monster monster;
    Player player;

    public Player targetPlayerIndex;
    public Player selectedPlayerIndex;
    public Monster[] appearMonster;
    public Monster selectedMonsterIndex;
    public int randMonster = 0;
    int stage = 1;
    public boolean result = false;

    public Battle(){
        this.monster = new Monster();
        this.player = new Player();
    }

    public boolean win() {  // 승리조건
        for (int i = 0; i < randMonster; i++) {
            if (appearMonster[i].HP > 0) // 하나의 몬스터라도 체력이 0 초과인 경우
                return false; // 아직 플레이어의 승리는 아님
        }
        return true; // 모든 몬스터가 죽었을 때 플레이어의 승리
    }
    
    public boolean lose(){ // 패배조건
        for(int i = 0;i<GM.selectedPlayer.length;i++){
            if(GM.selectedPlayer[i].HP > 0)
                return false;
        }
        return true;
    }

    public void fightStart(){
        Logs.log("-전투 시작-");
        GM.sleep();
        Logs.log("=== "+stage+" Stage ===");
        appear();// 몬스터 등장
        
    }

    public void fightEnd() throws Exception{
        Logs.log("전투 종료! 수고하셨습니다!");
        if(win()==true){
            Logs.log("플레이어의 승리!");
        }else if(lose()==true){
            Logs.log("플레이어의 패배,,,");
        }
    }

    public void appear(){ // 몬스터 출현
        randMonster = (int)(Math.random()*3)+3; // 몬스터 마리수 (3~5)
        appearMonster = new Monster[randMonster]; // 나타난 몬스터 배열
        Logs.log(randMonster+"마리 몬스터가 나타났다");
        GM.sleep();
        for(int i = 0; i<randMonster; i++){
            appearMonster[i] = randomMonsters(); //나타난 몬스터 객체 생성
            System.out.println((i+1)+". "+appearMonster[i].name);
            GM.sleep();
        }
    }
    public Monster randomMonsters(){//랜덤 몬스터 생성
        int randomIndex = 0;
        Monster mon = null;
        double randoms =  Math.random();
        if(randoms<=0.5){ //50% 확률로 마녀
            randomIndex = 0;
        }else if(0.5<randoms &&randoms<=0.8){ //30% 확률로 좀비
            randomIndex = 1;
        }else if(0.8<randoms &&randoms<=1){//20%확률로 악마
            randomIndex = 2;
        }
        mon = new Monster(GM.monsterList.get(randomIndex));
        if (Math.random() <=0.1) {//10% 확률로 엘리트몹
            eliteMonster(mon);
        }
        if(Math.random()<=0.01){//1% 확률로 보스몹
            bossMonster(mon);
        }
        return mon;
    }

    public Monster eliteMonster(Monster mon){
        //엘리트 몬스터
        mon.name = "[Elite]"+mon.name;
        mon.power = (int) (mon.power*1.2);
        mon.defense = (int) (mon.defense*1.2);
        mon.originalHP = (int) (mon.HP*1.2);
        mon.HP = (int) (mon.HP*1.2);
        mon.speed = (int) (mon.speed*2);
            return mon;
    }
    
    public Monster bossMonster(Monster mon){
        //보스몬스터
        mon.name = "[Boss]"+mon.name;
        mon.power = (int) (mon.power*1.5);
        mon.defense = (int) (mon.defense*1.5);
        mon.originalHP = (int) (mon.HP*1.5);
        mon.HP = (int) (mon.HP*1.5);
        mon.speed = (int) (mon.speed*2.5);
        return mon;
    }

    public void printMonster(){ // 몬스터 리스트 출력
        for(int i = 0; i<randMonster;i++){ 
            if(appearMonster[i].HP<=0){
                System.out.println(i+1 +". "+ appearMonster[i].name + " (사망)");
            }else{
                System.out.println(i+1 +". "+ appearMonster[i].name + " ["+appearMonster[i].HP+"/"+appearMonster[i].originalHP+"]"); 
            }
        }
    }

    public void printPlayer(){
        for(int i = 0; i < GM.selectedPlayer.length;i++){
            if(GM.selectedPlayer[i].HP <= 0){
                System.out.println((i+1)+". " + GM.selectedPlayer[i].name + " (사망)");
            }else{
                System.out.println((i+1)+". " + GM.selectedPlayer[i].name+" ["+GM.selectedPlayer[i].HP+"/"+GM.selectedPlayer[i].originalHP+"]");
            }
        }
    }

    public void MonsterInfo(){ // 몬스터 정보 확인
        printMonster();
          System.out.print(">> ");
          sc = new Scanner(System.in);
          int choice = sc.nextInt();
          if(1<=choice && choice <=randMonster){
            selectedMonsterIndex = appearMonster[choice-1]; // 정보확인을 원하는 몬스터
              monster.info(selectedMonsterIndex);
            }
    }

    public void partyInfo() throws Exception{ //  파티원 정보
        printPlayer();
            System.out.print(">> ");
            sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if(1<=choice && choice<=GM.selectedPlayer.length){
                targetPlayerIndex = GM.selectedPlayer[choice-1];
                player.info(targetPlayerIndex);
            }
    }

    public void attackchoice(Player currentPlayer){ // 몬스터 선택 및 공격
        System.out.print(">> ");
        sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(1<=choice && choice<=randMonster){
            selectedMonsterIndex = appearMonster[choice-1]; // 선택된 몬스터
            if(selectedMonsterIndex.HP <=0){ // hp가 0인 몬스터 선택 시
                Logs.log("이미 죽은 몬스터입니다!");
                currentPlayer.turn=true;
                GM.sleep();
            }else{ // 살아있는 몬스터 선택 시
                unit.focus(selectedMonsterIndex.name); 
                GM.sleep();
                selectedMonsterIndex.HP = unit.normalattack(currentPlayer,selectedMonsterIndex); 
                GM.sleep();
            }
        }else{
            Logs.log("거긴 몬스터가 없어요!"); // 선택지 외를 입력시
            currentPlayer.turn=true;
            GM.sleep();
        }
    }

    public LinkedList<Unit> unitspeed(){ // 스피드 비교 linkedlist 생성, 내림차순 정렬
        LinkedList<Unit> unitspeed = new LinkedList<Unit>();
        int maxIndex = Math.max(randMonster, GM.selectedPlayer.length);

        for(int i=0; i<maxIndex;i++){
            if(i < randMonster && appearMonster[i].HP >0){
                unitspeed.add(appearMonster[i]);
            }
            if(i<GM.selectedPlayer.length && GM.selectedPlayer[i].HP > 0){
                unitspeed.add(GM.selectedPlayer[i]);
            }
        }  
        Collections.sort(unitspeed,new Comparator<Unit>() {  
            public int compare(Unit a, Unit b){
                return b.speed - a.speed;
            }
        });
        return unitspeed;
    }

    public void upgrade(Player upgradePlayer){
        if(upgradePlayer.id.equals("C5") && upgradePlayer.grade.equals("special")){
            if(Math.random()<=0.404){
                upgradePlayer.grade = "normal";
                Logs.log("퇴근하고 싶은 개발자의 마음이 기적을 일으켰다!");
                upgradePlayer.name = "각성한 "+upgradePlayer.name;
                upgradePlayer.power = upgradePlayer.power*404;
                upgradePlayer.defense = (int) (upgradePlayer.defense*404);
                upgradePlayer.HP = (int) (upgradePlayer.HP*404);
                upgradePlayer.speed = (int) (upgradePlayer.speed*404);
            }
        }
    }

    public void turn(Unit unit){ //현재 공격할 차례가 누구인지
        Monster currentMonster = null;
        Player currentPlayer = null;
        
        if (unit instanceof Monster) {
            unit.turn = false;
            currentMonster = (Monster) unit;
            Logs.log(currentMonster.name+"의 턴입니다!");
            MonsterAttack(currentMonster);
        } else if (unit instanceof Player) {
            unit.turn = false;
            currentPlayer = (Player) unit;
            upgrade(currentPlayer);
            Logs.log(currentPlayer.name+"의 턴입니다!"); 
            try {
                PlayerAttack(currentPlayer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Skillattackchoice(Player currentPlayer){ // 몬스터 선택 및 공격
            Logs.log("누구를 공격하시겠습니까?");
            printMonster();
            System.out.print(">> ");
            sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if(1<=choice && choice<=randMonster){
                selectedMonsterIndex = appearMonster[choice-1]; // 선택된 몬스터
                if(selectedMonsterIndex.HP <=0){ // hp가 0인 몬스터 선택 시
                    Logs.log("이미 죽은 몬스터입니다!");
                    currentPlayer.turn=true;
                    GM.sleep();
                }else{ // 살아있는 몬스터 선택 시
                    currentPlayer.focus(selectedMonsterIndex.name); 
                    GM.sleep();
                    selectedMonsterIndex.HP = sk.skillAttack(currentPlayer, selectedMonsterIndex);
                    currentPlayer.MP =sk.restMp(currentPlayer);
                    GM.sleep();
                }
            }else{
                Logs.log("거긴 몬스터가 없어요!"); // 선택지 외를 입력시
                currentPlayer.turn=true;
                GM.sleep();
            }
    }

    public void skillHealchoice(Player currentPlayer){ // 회복시킬 플레이어 선택 및 공격
        Logs.log("누구를 치료하시겠습니까?");
        printPlayer();
        System.out.print(">> ");
        sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(1<=choice && choice<=GM.selectedPlayer.length){
            selectedPlayerIndex = GM.selectedPlayer[choice-1]; // 선택된 파티원
            if(selectedPlayerIndex.HP <=0){ // hp가 0인 파티원 선택 시
                Logs.log("이미 죽은 파티원입니다!");
                currentPlayer.turn=true;
                GM.sleep();
            }else{ // 살아있는 파티원 선택 시
                currentPlayer.focus(selectedPlayerIndex.name); 
                GM.sleep();
                selectedPlayerIndex.HP = sk.skillHeal(currentPlayer, selectedPlayerIndex);
                currentPlayer.MP =sk.restMp(currentPlayer);
                GM.sleep();
            }
        }else{
            Logs.log("잘못 선택하셨습니다!"); // 선택지 외를 입력시
            currentPlayer.turn=true;
            GM.sleep();
        }
    }
    public void skillResurrChoice(Player currentPlayer){ // 회복시킬 플레이어 선택 및 공격
        Logs.log("누구를 부활시키시겠습니까?");
        printPlayer();
        System.out.print(">> ");
        sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(1<=choice && choice<=GM.selectedPlayer.length){
            selectedPlayerIndex = GM.selectedPlayer[choice-1]; // 선택된 파티원
            currentPlayer.focus(selectedPlayerIndex.name); 
            GM.sleep();
            sk.resurrection(currentPlayer, selectedPlayerIndex);
            currentPlayer.MP =sk.restMp(currentPlayer);
            GM.sleep();
        }else{
            Logs.log("잘못 선택하셨습니다!"); // 선택지 외를 입력시
            currentPlayer.turn=true;
            GM.sleep();
        }
    }

    public void MonsterAttack(Monster currentMonster){ //몬스터 공격 시 , playerattack과 달리 선택지가 존재 X
        int randomplayer =(int)(Math.random()*GM.selectedPlayer.length);
        targetPlayerIndex = GM.selectedPlayer[randomplayer];
        if(targetPlayerIndex.HP <=0){
            Logs.log(currentMonster.name+"가 실수했다!");
            GM.sleep();
            Logs.log("----------------------------------------------------------  --");
        }else{
            currentMonster.focus(targetPlayerIndex.name);
            GM.sleep();
            targetPlayerIndex.HP = unit.normalattack(currentMonster, targetPlayerIndex);
            GM.sleep();
        }
    }

    public void PlayerAttack(Player currentPlayer) throws Exception{
            Logs.log("어떤 행동을 취할까요?");
            System.out.println("1. 일반 공격");
            System.out.println("2. 스킬 사용");
            System.out.println("3. 도망");
            System.out.println("4. 정보 확인");
            try {
                System.out.print(">> ");
                sc = new Scanner(System.in);
                int move = sc.nextInt(); // 행동 선택
                switch(move){
                    case 1 : 
                        Logs.log("누구를 공격하시겠습니까?"); // 일반공격
                        printMonster();
                        attackchoice(currentPlayer); // 공격할 몬스터 선택 및 공격격
                        break;
                    case 2 : if(currentPlayer.MP<sk.minMp(currentPlayer)){//그 플레이어의 스킬 중 가장 낮은 MP 소모량
                            Logs.log("마나가 부족하여 스킬을 사옹할 수 없습니다!!!");
                            currentPlayer.turn=true;
                            break;
                        }else{
                            Logs.log("어떤 스킬을 사용하시겠습니까? (현재 MP : "+currentPlayer.MP+")");
                            Skill selectedSkill = sk.skillchoice(currentPlayer);
                            if(selectedSkill != null){
                                if(selectedSkill.nature.equals("heal")){
                                    skillHealchoice(currentPlayer);
                                }else if(selectedSkill.nature.equals("Run")){
                                    sk.run(currentPlayer);
                                    currentPlayer.MP =sk.restMp(currentPlayer);
                                }else if(selectedSkill.nature.equals("selfbuff")){
                                    sk.selfbuff(currentPlayer);
                                    currentPlayer.MP =sk.restMp(currentPlayer);
                                }else if(selectedSkill.nature.equals("splash")){
                                    sk.splash(currentPlayer, appearMonster);
                                    currentPlayer.MP =sk.restMp(currentPlayer);
                                }else if(selectedSkill.nature.equals("resurrection")){
                                    skillResurrChoice(currentPlayer);
                                    currentPlayer.MP =sk.restMp(currentPlayer);
                                }
                                else{
                                    Skillattackchoice(currentPlayer);
                                }
                            }
                            
                            break;
                        }
                        
                    case 3 : player.run();
                            break;
                    case 4 : Logs.log("누구의 정보를 확인할까요?"); // 정보확인
                            GM.sleep();
                            Logs.log("1. 파티원");
                            GM.sleep();
                            Logs.log("2. 몬스터");
                            System.out.print(">> ");
                            sc = new Scanner(System.in);
                            int choices = sc.nextInt();
                            
                            switch(choices){
                                case 1: partyInfo();
                                        currentPlayer.turn=true;
                                        break;
                                case 2 : MonsterInfo();
                                        currentPlayer.turn=true;
                                        break;
                                default : Logs.log("잘못된 선택지입니다!");
                                        currentPlayer.turn=true;
                                        break;
                            }
                        break;
                    default:    Logs.log("잘못된 선택지입니다!");
                                currentPlayer.turn=true;
                            break;
                    }
                } catch (InputMismatchException e) {
                    Logs.log("잘못된 선택지입니다!");
                    GM.sleep();
                    PlayerAttack(currentPlayer);
                }
    }

    public void Next() throws Exception{//승리시 다음 스테이지로
        if(stage<5){
            Logs.log("다음 스테이지로 가시겠습니까?");
            System.out.println("1. 계속하기");
            System.out.println("2. 게임 종료");
            sc = new Scanner(System.in);
            int choice = sc.nextInt();
            System.out.print(">> ");
            switch(choice){
                case 1 : 
                        stage++;
                        Logs.log(stage + " 스테이지로 이동");
                        battlefight();
                        break;
                case 2 : 
                        System.exit(0);
                        break;
                default : 
                        Logs.log("1, 2 중에서 선택해주세요!!");
                        GM.sleep();
                        Next();
                        break;
            }
        }else{
            Logs.log("모든 스테이지를 클리어하셨습니다!");
        }
    }

    public void Restart() throws Exception{//패배시 다시 시작
            try {
                Logs.log("다시 시작하시겠습니까?");
                System.out.println("1. 다시 시작");
                System.out.println("2. 게임 종료");
                sc = new Scanner(System.in);
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
                        Logs.log("1, 2 중에서 선택해주세요!!");
                        GM.sleep();
                        Restart();
                        break;
                }
            } catch (InputMismatchException e) {
                Logs.log("잘못된 선택지입니다!");
                GM.sleep();
                Restart();
            }
    }

    public void battlefight() throws Exception {
        int round = 1;
        fightStart();
        LinkedList<Unit> speedlist = new LinkedList<>();
        while(!win() && !lose()){
            if (speedlist.size() == 0) {
                Logs.log( "====Round" + round + "====");
                speedlist = unitspeed();
                for(int i = 0; i<speedlist.size();i++){
                    if(speedlist.get(i).HP*1.2>speedlist.get(i).originalHP){
                        speedlist.get(i).HP = speedlist.get(i).originalHP;
                    }else{
                        speedlist.get(i).HP = (int) (speedlist.get(i).HP*1.2);
                    }
                }
                round++;
            }
            Unit turnUnit = speedlist.get(0);

            turn(turnUnit);
            if (!turnUnit.turn || turnUnit.HP<=0) {
                speedlist.remove(0);
            }
            for(int i = 0; i<speedlist.size();i++){
                if(speedlist.get(i).HP <=0){
                    speedlist.remove(i);
                }
            }   
        }
        fightEnd();
        if(win()){
            Next();
        }else if(lose()){
            Restart();
        }
    }
}