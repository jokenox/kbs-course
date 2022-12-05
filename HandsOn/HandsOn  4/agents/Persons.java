package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import net.sf.clipsrules.jni.*;
 
public class Persons extends Agent {
  private Environment clips;

  protected void setup() {
    clips = new Environment();

    addBehaviour(new EmisorComportaminento());
  }

  private class EmisorComportaminento extends SimpleBehaviour {
    boolean fin = false;
  
    public void action() {
      try{
        clips.load("persons/run-persons.clp");
        clips.reset();
        clips.run();

        fin = true;
      } catch(Exception e){
        e.printStackTrace();
      }
    }

    public boolean done() {
      return fin;
    }
  }

}