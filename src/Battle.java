import java.util.Scanner;

import Unit.Character.*;
import Unit.Monster.*;
import etc.Delay;

public class Battle{
    Scanner sc = new Scanner(System.in);
    Delay dl = new Delay();
    Warrior warrior = new Warrior();
    Witch witch = new Witch();
    Zombie zombie = new Zombie();
    Devil devil = new Devil();
    
    
    public void check(int HP){
        if(HP == 0){
            System.out.println("대상이 없습니다.");
        }

    }


    public void fight(){
        System.out.println("-전투 시작-");
        dl.Sleep();
        System.out.println( "적을 마주쳤다."); // 몬스터 3마리 배열
        dl.Sleep();
        while(witch.HP + zombie.HP + devil.HP !=0){
            System.out.println("어떤 행동을 취할까요?");
            System.out.println("1. 일반 공격");
            System.out.println("2. 스킬 사용");
            System.out.println("3. 도망");
            System.out.println("4. 정보 확인");
            int move = sc.nextInt();
            switch(move){
                case 1 : System.out.println("누구를 공격하시겠습니까?");
                        System.out.println("1. 마녀");
                        System.out.println("2. 좀비");
                        System.out.println("3. 악마");
                        int choice = sc.nextInt();
                        switch(choice){
                            case 1 :    warrior.focus(witch.name); 
                                        dl.Sleep();

                                        if(witch.HP == 0){
                                            check(witch.HP);
                                        }else{
                                            witch.HP = warrior.attack(warrior.name, witch.name, warrior.power, witch.HP); 
                                            warrior.kill(witch.name,witch.HP);
                                            dl.Sleep();                                           
                                        }
                                        warrior.turn = false;
                                        break;
                            case 2 :    warrior.focus(zombie.name); 
                                        dl.Sleep();
                                        
                                        if(zombie.HP == 0){
                                            check(zombie.HP);
                                        }else{
                                            zombie.HP = warrior.attack(warrior.name, zombie.name, warrior.power, zombie.HP);
                                            warrior.kill(zombie.name,zombie.HP);
                                            dl.Sleep();
                                        }
                                        warrior.turn = false;
                                        break;
                            case 3 :    warrior.focus(devil.name); 
                                        dl.Sleep();
                                        
                                        if(devil.HP == 0){
                                            check(devil.HP);
                                        }else{
                                            devil.HP = warrior.attack(warrior.name, devil.name, warrior.power, devil.HP);
                                            warrior.kill(devil.name, devil.HP);
                                            dl.Sleep();
                                        }
                                        warrior.turn = false;
                                        break;
                        }
                        break;
                case 2 :
                        break;
                case 3 : System.out.println("무사히 도망쳤다!");
                            System.exit(0);
                        break;
                case 4 : System.out.println("누구의 정보를 확인할까요?");
                            System.out.println("1. 마녀 ");
                            System.out.println("2. 좀비");
                            System.out.println("3. 악마");
                            int monsterchoice = sc.nextInt();
                            switch(monsterchoice){
                                case 1 :
                                        break;
                                case 2 :
                                case 3 :
                            }
                        break;
            }
        }
        
    }
}