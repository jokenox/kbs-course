import net.sf.clipsrules.jni.*;

public class Persons {
  
  public void main(String[] args){
    Environment clips = new Environment();

    try{
      clips.load("clips/persons/run-persons.clp");
      clips.reset();
      clips.run();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
  
}