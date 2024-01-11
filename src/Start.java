import java.util.Scanner;

import etc.Delay;

public class Start {
    Delay dl = new Delay();
    Scanner sc = new Scanner(System.in);
    public void New(){
        System.out.println("게임을 시작합니다.");
        dl.Sleep();
        System.out.println("1. 게임 시작 ");
        System.out.println("2. 게임 종료 ");
        int select = sc.nextInt();
        if(select == 2){
            System.exit(0);
        }
    }
}
