import Unit.Party;
import etc.GameManager;

public class Main {
    public static void main(String[] args) throws Exception {
        GameManager GM = GameManager.getInstance();
        Battle bt = new Battle();
        GM.connect();
        GM.getMonster(); 
        GM.getCharacter();
        do{
            Start st = new Start();
            Party pt = new Party();        
            st.New();
            pt.Member();
            bt.battlefight();
            GM.close();
        }while(bt.result);
    }
}
