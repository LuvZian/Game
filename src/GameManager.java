public class GameManager {
    private GameManager(){
    
    }
    private static class GameManagerHelper{
        private static final GameManager GMAEMANAGER = new GameManager();
    }
    public static GameManager getInstance(){
        return GameManager.GAMEMANAGER;
    }

    
}
