import java.util.*;

import Unit.Character.*;
import etc.Delay;

public class Party {
    Delay dl = new Delay();
    Warrior wr = new Warrior();
    Mage mg = new Mage();
    Bishop bs = new Bishop();
    Assassin as = new Assassin();
    Developer de = new Developer();
    
    public void Teaming(){
        int count = 0;
        int member = 3;
        String[] party = new String[3];
        for(int i = 0; i<member; i++){
            System.out.println("당신의 파티를 선택해주세요! ( "+(member-count)+"명 남았습니다 )");
            System.out.println("1. 전사");
            System.out.println("2. 마법사");
            System.out.println("3. 비숍");
            System.out.println("4. 도적");
            System.out.println("5. 개발자");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            count++;
            switch(choice){
                case 1 : System.out.print("1. 전사");
                            party[i] = wr.name;
                            break;
                case 2 : System.out.print("2. 마법사");
                            party[i] = mg.name;
                            break;
                case 3 : System.out.print("3. 비숍");
                            party[i] = bs.name;
                            break;
                case 4 : System.out.print("4. 도적");
                            party[i] = as.name;
                            break;
                case 5 : System.out.print("5. 개발자");
                            party[i] = de.name;
                            break;            
            }
            System.out.println("를 선택하셨습니다!");
            dl.Sleep();
        }
        System.out.println("당신의 파티원 : ");
        for(int i = 0;i<member;i++){
                System.out.println((i+1)+". "+party[i]);
        }
        System.out.println("\n");
        
    }
}
