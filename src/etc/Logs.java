package etc;

import java.io.*;
import java.nio.file.Files;

public class Logs { // 로그 파일 저장
    private static final String Filename_pre = "game_log";
    private static final String Filename_exten = ".log";
    private static final String File_seper = "_";
    public static int FileNum = 1;
    private static String filename = Filename_pre + File_seper + FileNum + Filename_exten;
    
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
            filename = Filename_pre + File_seper + FileNum + Filename_exten;
            file = new File(filename);
        }
        return filename;
    }
}
