import Unit.Party;
import etc.GameManager;

public class Main {
    public static void main(String[] args) throws Exception {
        GameManager GM = GameManager.getInstance();
        Start st = new Start();
        Party pt = new Party();
        Battle bt = new Battle();

        GM.connect();
        GM.getMonster();
        GM.getCharacter();
        GM.close();
        
        st.New();
        pt.Member();
        bt.battlefight();
        
        
    }
}
