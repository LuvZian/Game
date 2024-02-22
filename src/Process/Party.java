package Process;
import java.util.*;

import Unit.Player;
import etc.GameManager;
import etc.Logs;

public class Party {
    GameManager GM = GameManager.getInstance();
    Scanner sc = new Scanner(System.in);
    int member = 0;
    public int maxmemeber = 3;
    public Party(){
        Member();
    }

    public void Member(){
        GM.selectedPlayer = new Player[maxmemeber];
        while(member != maxmemeber){
            Logs.log("당신의 파티를 선택해주세요! ( "+(maxmemeber-member)+"명 남았습니다 )");
            for(int j = 0; j<GM.playerList.size(); j++){ // player 리스트 출력
                System.out.println(j+1 + ". " + GM.playerList.get(j).name);
            }
            try {
                System.out.print(">> ");
                //sc = new Scanner(System.in);
                int choice = sc.nextInt();
                if(choice <= GM.playerList.size()){
                    GM.selectedPlayer[member] = new Player(GM.playerList.get(choice-1).id, GM.playerList.get(choice-1).name, GM.playerList.get(choice-1).power, GM.playerList.get(choice-1).defense, GM.playerList.get(choice-1).HP, GM.playerList.get(choice-1).MP, GM.playerList.get(choice-1).speed, GM.playerList.get(choice-1).grade);
                    member++;
                    Logs.log("========현재 선택한 파티=======");
                    for (int i = 0; i<member;i++){
                        Logs.log(i+1+". " + GM.selectedPlayer[i].name);
                    }                    
                }else{
                    Logs.log("잘못된 선택지를 고르셨습니다!!");
                }            
            } catch (InputMismatchException e) {
                Logs.log("잘못된 선택지를 고르셨습니다!!");
                GM.sleep();
            }
        }

        Logs.log("파티 구성이 끝났습니다.");
        Logs.log("---당신의 파티원---");
        for (int i = 0; i<member;i++){
            Logs.log(i+1 + ". " + GM.selectedPlayer[i].name);
        }
        Logs.log("------------------------------------------------------------");
    }
    
}
