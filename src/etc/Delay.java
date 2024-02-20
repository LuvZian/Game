package etc;
public class Delay { // 스크립트 속도 조절
    public void Sleep(){
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
