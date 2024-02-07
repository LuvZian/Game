package etc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Unit.Monster;
import Unit.Player;

public class GameManager {
    private GameManager(){}

    private static class GameManagerHolder{
        private static final GameManager INSTANCE = new GameManager();
    }
    public static GameManager getInstance(){
        return GameManagerHolder.INSTANCE;
    }

    Connection con;
    public int monster_count;
    public Monster monsterlist[];
    public Monster monsters;
    public Player playerlist[];
    public Player[] selectedplayer;

    public void connect(){ //DB 연결
        String url = "jdbc:mysql://127.0.0.1:3306/game_db";
        String user = "root";
        String passwd = "1234";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("---로딩중---");
        }catch(Exception e){
        }
        try{
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println("---연결 완료 ---");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            if(con != null){
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Monster[] getMonster() throws Exception{
        
        Statement stmt = con.createStatement();
        String count = "Select Count(MonsterId) From monster_db"; // db에 저장된 몬스터 종류
        ResultSet cnt = stmt.executeQuery(count);
        cnt.next();
        monster_count = cnt.getInt(1);

        String query = "Select * FROM monster_db";
        ResultSet rs = stmt.executeQuery(query);

        monsterlist = new Monster[monster_count];
        for(int i = 0; i<monsterlist.length; i++){
            rs.next();
            monsterlist[i] = new Monster(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),rs.getInt(6));
        }
        return monsterlist;
    }

    public Player[] getCharacter() throws Exception{ //캐릭터 데이터 
        Statement stmt = con.createStatement();

        String count = "Select Count(CharacterId) From character_db"; // database에 저장된 캐릭터 수
        ResultSet cnt = stmt.executeQuery(count);
        cnt.next();
        int character_count = cnt.getInt(1);

        String query = "Select * FROM character_db";
        ResultSet rs = stmt.executeQuery(query);

        playerlist = new Player[character_count];
        for(int i = 0; i<playerlist.length; i++){
            rs.next();
            playerlist[i] = new Player(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7));
        }
        return playerlist;
    }

}