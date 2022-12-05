import net.sf.clipsrules.jni.*;

public class Market {
  
  public void main(String[] args){
    Environment clips = new Environment();

    try{
      clips.load("clips/market/run.clp");
      clips.reset();
      clips.run();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
  
}