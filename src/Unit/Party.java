package Unit;
import java.util.*;

import etc.Database;
import etc.Delay;

public class Party {
    Scanner sc= new Scanner(System.in);
    Delay dl = new Delay();
    Database db;
    Player player;
    Player[] selectedplayer;
    int member = 0;
    int maxmemeber = 3;
    
    
    public Party(Database db){
        this.db = db;
    }    

    public void Member(){
        selectedplayer = new Player[maxmemeber];
        while(member != maxmemeber){
            System.out.println("당신의 파티를 선택해주세요! ( "+(maxmemeber-member)+"명 남았습니다 )");
            for(int j = 0; j<db.playerlist.length; j++){ // player 리스트 출력
                System.out.println(j+1 + ". " + db.playerlist[j].name);
            }
            try {
                int choice = sc.nextInt();
                if(choice <= db.playerlist.length){
                    selectedplayer[member] = new Player(db.playerlist[choice-1].id, db.playerlist[choice-1].name, db.playerlist[choice-1].power, db.playerlist[choice-1].defense, db.playerlist[choice-1].HP, db.playerlist[choice-1].MP, db.playerlist[choice-1].speed);
                    member++;
                    System.out.println("========현재 선택한 파티=======");
                    for (int i = 0; i<member;i++){
                        System.out.println(i+1+". " + selectedplayer[i].name);
                    }
                }else{
                    System.out.println("잘못된 선택지를 고르셨습니다!!");
                }
            } catch (InputMismatchException e) {
                sc = new Scanner(System.in);
                System.out.println("잘못된 선택지를 고르셨습니다!!");
                dl.Sleep();
                Member();
            }
        }

        System.out.println("파티 구성이 끝났습니다.");
        System.out.println("---당신의 파티원---");
        for (int i = 0; i<member;i++){
            System.out.println(i+1 + ". " + selectedplayer[i].name);
        }
    }

}
