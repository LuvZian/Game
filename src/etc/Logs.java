package etc;

import java.io.*;
import java.nio.file.Files;

public class Logs { // 로그 파일 저장
    private static final String FILENAME_PRE = "game_log";
    private static final String FILENAME_EXTEN = ".log";
    private static final String FILE_SEPER = "_";
    public static int FileNum = 1;
    private static String filename = FILENAME_PRE + FILE_SEPER + FileNum + FILENAME_EXTEN;
    
    public static void log(String msg) { // 로그파일 생성 및 작성
        try{
            Files.write(new File(filename).toPath(), (msg + "\n").getBytes(), java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
            System.out.println(msg); // 콘솔 출력용
        }catch(IOException e){
            log(e.getMessage());
        }
    }
    public static String check(){ // 파일명 중복 확인
        File file = new File(filename);
        while(file.exists()){
            FileNum++;
            filename = FILENAME_PRE + FILE_SEPER + FileNum + FILENAME_EXTEN;
            file = new File(filename);
        }
        return filename;
    }
}
