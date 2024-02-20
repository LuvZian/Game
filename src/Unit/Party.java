package Unit;
import java.util.*;

import etc.Delay;
import etc.GameManager;
import etc.Logs;

public class Party {
    GameManager GM = GameManager.getInstance();
    Scanner sc= new Scanner(System.in);
    Delay dl = new Delay();
    int member = 0;
    public int maxmemeber = 3;
    
    
    public Party(){
    }    

    public void Member(){
        GM.selectedplayer = new Player[maxmemeber];
        while(member != maxmemeber){
            Logs.log("당신의 파티를 선택해주세요! ( "+(maxmemeber-member)+"명 남았습니다 )");
            for(int j = 0; j<GM.playerlist.length; j++){ // player 리스트 출력
                System.out.println(j+1 + ". " + GM.playerlist[j].name);
            }
            try {
                System.out.print(">> ");
                int choice = sc.nextInt();
                if(choice <= GM.playerlist.length){
                    GM.selectedplayer[member] = new Player(GM.playerlist[choice-1].id, GM.playerlist[choice-1].name, GM.playerlist[choice-1].power, GM.playerlist[choice-1].defense, GM.playerlist[choice-1].HP, GM.playerlist[choice-1].MP, GM.playerlist[choice-1].speed);
                    member++;
                    Logs.log("========현재 선택한 파티=======");
                    for (int i = 0; i<member;i++){
                        Logs.log(i+1+". " + GM.selectedplayer[i].name);
                    }
                }else{
                    Logs.log("잘못된 선택지를 고르셨습니다!!");
                }
            } catch (InputMismatchException e) {
                sc = new Scanner(System.in);
                Logs.log("잘못된 선택지를 고르셨습니다!!");
                dl.Sleep();
                Member();
            }
        }

        Logs.log("파티 구성이 끝났습니다.");
        Logs.log("---당신의 파티원---");
        for (int i = 0; i<member;i++){
            Logs.log(i+1 + ". " + GM.selectedplayer[i].name);
        }
        Logs.log("------------------------------------------------------------");
    }

}
