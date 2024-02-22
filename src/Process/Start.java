package Process;

import java.util.InputMismatchException;
import java.util.Scanner;

import etc.GameManager;
import etc.Logs;

public class Start {
    GameManager GM = GameManager.getInstance(); 
    Scanner sc;
    public Start(){
        New();
    }
    
    public void New(){
        Logs.log("=== RPG Game ===");
            GM.sleep();
            System.out.println("1. 게임 시작 ");
            System.out.println("2. 게임 종료 ");
            try {
                System.out.print(">> ");
                sc = new Scanner(System.in);
                int select = sc.nextInt();
                switch (select) {
                    case 1: Logs.log("게임을 시작합니다.");
                        GM.sleep();
                        break;
                    case 2:
                        System.exit(0);
                        break;
                    default:                
                        Logs.log("1, 2 중에서 선택해주세요!!");
                        GM.sleep();
                        New();
                        break;
                }
            } catch (InputMismatchException e) {
                Logs.log("1, 2 중에서 선택해주세요!!");
                GM.sleep();
                New();
            }
    }

    
}
