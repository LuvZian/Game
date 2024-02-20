
import java.util.InputMismatchException;
import java.util.Scanner;

import etc.Delay;
import etc.Logs;

public class Start {
    Delay dl = new Delay();

    Scanner sc = new Scanner(System.in);
    
    public void New(){
        Logs.log("=== RPG Game ===");
            dl.Sleep();
            System.out.println("1. 게임 시작 ");
            System.out.println("2. 게임 종료 ");
            try {
                System.out.print(">> ");
                int select = sc.nextInt();
                switch (select) {
                case 1: Logs.log("게임을 시작합니다.");
                    dl.Sleep();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    Logs.log("1, 2 중에서 선택해주세요!!");
                    dl.Sleep();
                    New();
                    break;
                }
            } catch (InputMismatchException e) {
                sc = new Scanner(System.in);
                Logs.log("1, 2 중에서 선택해주세요!!");
                dl.Sleep();
                New();
            }
    }

    
}
