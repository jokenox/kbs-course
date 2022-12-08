package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import net.sf.clipsrules.jni.*;
 
public class Market extends Agent {
  private Environment clips;

  protected void setup() {
    clips = new Environment();

    addBehaviour(new EmisorComportaminento());
  }

  private class EmisorComportaminento extends SimpleBehaviour {
    boolean fin = false;
  
    public void action() {
      try{
        clips.eval("(clear)");

        clips.load("market/run.clp");
        
        clips.eval("(run)");

        clips.eval("(facts)");

        myAgent.doDelete();

        fin = true;
      } catch(Exception e){
        e.printStackTrace();
      }
    }

    public boolean done() {
      return fin;
    }

    public int onEnd() {
      return super.onEnd();
    }
  }

}