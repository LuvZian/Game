package etc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Monster> monsterList = new ArrayList<>();
    public List<Player> playerList = new ArrayList<>();
    public List<Skill> skillList = new ArrayList<>();
    private Connection conn;
    public Monster monsters;
    public Player[] selectedPlayer;

    public void connect(){
        try{
            String url = "jdbc:mysql://127.0.0.1:3306/game_db";
            String user = "root";
            String passwd = "1234";

            this.conn = DriverManager.getConnection(url, user, passwd);
            Logs.log("연결 성공");
        }catch(SQLException e){
            Logs.log("연결 실패");
            Logs.log("사유 : "+ e.getMessage());
        }
    }

    public void close(){
        try {
            if(conn != null){
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Monster> getMonster() throws Exception{
        String query = "Select * FROM monster_db";
        PreparedStatement infopstmt = conn.prepareStatement(query);
        ResultSet rs = infopstmt.executeQuery();
        while(rs.next()){
            Monster monster = new Monster(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),rs.getInt(6),rs.getString(7));
            monsterList.add(monster);
        }
        return monsterList;
    }

    public List<Skill> getSkill(String unitId)throws Exception{//필요한 스킬 데이터 받아오기
        skillList = new ArrayList<>();
        String skillQuery = "Select * from skillinfo where id in (select skill_id from playerskill where player_id = ?)";
        PreparedStatement pstmtSkill = conn.prepareStatement(skillQuery);
        pstmtSkill.setString(1, unitId);
        ResultSet rs = pstmtSkill.executeQuery();

        while(rs.next()){
            Skill skill = new Skill(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
            skillList.add(skill);
        } 
        return skillList;
    }

    public List<Player> getCharacter() throws Exception{ //캐릭터 데이터 
        String query = "Select * FROM character_db";
        PreparedStatement pstmtPlayer = conn.prepareStatement(query);
        ResultSet rs = pstmtPlayer.executeQuery();

        int i = 0;
        while(rs.next()){
            Player player = new Player(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getString(8));
            playerList.add(player);
            playerList.get(i).setSkills(getSkill(playerList.get(i).id));
            i++;
        }
        return playerList;
    }

    public void sleep(){
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}