package etc;
import java.util.logging.*;
import java.io.IOException;

public class Logs {
    
    Logger logger = Logger.getLogger("mylogger");
    private static Logs instance = new Logs();

    private FileHandler logFile = null;
    private FileHandler warningFile = null;
    private FileHandler fineFile = null;

    public Logs(){
        try {
            logFile = new FileHandler("log.txt",true);
            warningFile = new FileHandler("warning.txt", true);
            fineFile = new FileHandler("fine.txt",true);
        } catch (SecurityException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        logFile.setFormatter(new SimpleFormatter());
        warningFile.setFormatter(new SimpleFormatter());
        fineFile.setFormatter(new SimpleFormatter());

        logger.setLevel(Level.ALL);
        warningFile.setLevel(Level.ALL);
        fineFile.setLevel(Level.ALL);

        logger.addHandler(logFile);
        logger.addHandler(warningFile);
        logger.addHandler(fineFile);
    }
    
    public static Logs getLogger(){
        return instance;
    }

    public void log(String msg){
        logger.finest(msg);
        logger.finer(msg);
        logger.fine(msg);
        logger.config(msg);
        logger.info(msg);
        logger.warning(msg);
        logger.severe(msg);

    }

    public void fine(String msg){
        logger.fine(msg);
    }
    public void warning(String msg){
        logger.warning(msg);
    }
}
