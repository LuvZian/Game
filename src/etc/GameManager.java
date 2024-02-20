package etc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Unit.Monster;
import Unit.Player;
import Unit.Skill;

public class GameManager {
    private GameManager(){}

    private static class GameManagerHolder{
        private static final GameManager INSTANCE = new GameManager();
    }
    public static GameManager getInstance(){
        return GameManagerHolder.INSTANCE;
    }

    private Connection con;
    public int monster_count;
    public Monster monsterlist[];
    public Monster monsters;
    public Player playerlist[];
    public Player[] selectedplayer;
    public Skill skilllist[];
    public int skill_count;

    public void connect(){
        try{
            String url = "jdbc:mysql://127.0.0.1:3306/game_db";
            String user = "root";
            String passwd = "1234";

            this.con = DriverManager.getConnection(url, user, passwd);
            Logs.log("연결 성공");
        }catch(SQLException e){
            Logs.log("연결 실패");
            Logs.log("사유 : "+ e.getMessage());
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
        String count = "Select Count(MonsterId) From monster_db"; // db에 저장된 몬스터 종류
        PreparedStatement pstmt = con.prepareStatement(count);
        
        ResultSet cnt = pstmt.executeQuery();
        cnt.next();
        monster_count = cnt.getInt(1);

        String query = "Select * FROM monster_db";
        PreparedStatement infopstmt = con.prepareStatement(query);
        ResultSet rs = infopstmt.executeQuery();

        monsterlist = new Monster[monster_count];
        for(int i = 0; i<monsterlist.length; i++){
            rs.next();
            monsterlist[i] = new Monster(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),rs.getInt(6),rs.getString(7));
        }
        return monsterlist;
    }

    

        public Skill[] getskill(String unit_id)throws Exception{//필요한 스킬 데이터 받아오기
            String countQuery = "select count(player_id) from playerskill where player_id =?";
            PreparedStatement pstmt = con.prepareStatement(countQuery);
            pstmt.setString(1, unit_id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            skill_count = rs.getInt(1);

            String skillQuery = "Select * from skillinfo where id in (select skill_id from playerskill where player_id = ?)";
            PreparedStatement pstmtskill = con.prepareStatement(skillQuery);
            pstmtskill.setString(1, unit_id);
            ResultSet rsskill = pstmtskill.executeQuery();

            skilllist = new Skill[skill_count];
            for(int i = 0;i<skill_count;i++){
                rsskill.next();
                skilllist[i] = new Skill(rsskill.getString(1), rsskill.getString(2), rsskill.getInt(3), rsskill.getInt(4), rsskill.getString(5));
            }
            
            return skilllist;
        }

        public Player[] getCharacter() throws Exception{ //캐릭터 데이터 
            String count = "Select Count(CharacterId) From character_db"; // database에 저장된 캐릭터 수
            PreparedStatement pstmt = con.prepareStatement(count);    
            ResultSet cnt = pstmt.executeQuery();
            cnt.next();
            int character_count = cnt.getInt(1);
    
            String query = "Select * FROM character_db";
            PreparedStatement pstmtplayer = con.prepareStatement(query);
            ResultSet rs = pstmtplayer.executeQuery();
    
            playerlist = new Player[character_count];
            for(int i = 0; i<playerlist.length; i++){
                rs.next();
                playerlist[i] = new Player(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7));
                playerlist[i].setskills(getskill(playerlist[i].id));
            }
            return playerlist;
        }

}