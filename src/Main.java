import Unit.Party;
import etc.Database;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Database db = new Database();
        Start st = new Start();
        Party pt = new Party(db);
        Battle bt = new Battle(db);

        db.connect();
        db.getMonster();
        db.getCharacter();
        db.close();
        
        st.New();
        pt.Member();
        bt.fight_start();
        bt.fight();
        bt.fight_end();
        
        
    }
}
