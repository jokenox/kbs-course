package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import net.sf.clipsrules.jni.*;
 
public class ChiefSurgeonAgent extends Agent {
  private Environment clips;
   
  protected void setup() {
    clips = new Environment();

    addBehaviour(new ReceptorComportaminento());
  }

  private class ReceptorComportaminento extends SimpleBehaviour {
    private boolean fin = false;

    public void action() {

      //Obtiene el primer mensaje de la cola de mensajes
      ACLMessage mensaje = receive();

      if (mensaje!= null) {
        
        if (mensaje.toString() == "(patient ready)") {
          System.out.println("Patient ready");

          try {
            clips.eval("(clear)");

            clips.eval("(assert " + mensaje.getContent() + " )");

            clips.eval("(defrule r1 (patient ready) =>  (assert (patient anesthetize)))");

            clips.eval("(run)");

            //Envio de mensaje a anestesista
            AID id = new AID();
            id.setLocalName("anestesista");

            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);

            mensaje.setSender(getAID());
            mensaje.addReceiver(id);
            mensaje.setContent("(patient anesthetize)");

            System.out.println(getLocalName() + ": Anesteciar paciente");

            send(mensaje);
          } catch (Exception e){
            System.out.println (e.getMessage());
          }
        } else if (mensaje.toString() == "(surgery can-start)") {
          System.out.println(getLocalName() + ": Que comience la cirugia");
        }

        fin = true;
      }
    }

    public boolean done() {
      return fin;
    }
  }
}