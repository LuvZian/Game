import Process.Battle;
import Process.Party;
import Process.Start;
import etc.GameManager;
import etc.Logs;

public class Main {

    
    public static void main(String[] args) throws Exception {
        GameManager GM = GameManager.getInstance();
        Battle bt = new Battle();
        Logs.check();

        GM.connect();
        GM.getMonster(); 
        GM.getCharacter();

        do{
            new Start();
            new Party();
            bt.battlefight();
            GM.close();
        }while(bt.result);
    }


}
