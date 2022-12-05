import net.sf.clipsrules.jni.*;

public class Prodcust {
  
  public void main(String[] args){
    Environment clips = new Environment();

    try{
      clips.load("clips/prodcust/run-prodcust.clp");
      clips.reset();
      clips.run();
    } catch(Exception e){
      e.printStackTrace();
    }
  }

}