package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import net.sf.clipsrules.jni.*;
 
public class AnesthesiologistAgent extends Agent {
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
        
        System.out.println(getLocalName() + ": Anesteciando paciente");

        try {
          clips.eval("(clear)");

          clips.eval("(assert " + mensaje.getContent() + " )");

          clips.build("(defrule r1 (patient anesthetize) =>  (assert (patient is-anesthetized)))");

          clips.eval("(run)");

          //Envio de mensaje a anestesista
          AID id = new AID();
          id.setLocalName("Cirujano");

          mensaje = new ACLMessage(ACLMessage.INFORM);

          mensaje.setSender(getAID());
          mensaje.addReceiver(id);
          mensaje.setContent("(patient is-anesthetized)");

          System.out.println(getLocalName() + ": Paciente anesteciado");

          send(mensaje);
        } catch (Exception e){
          System.out.println (e.getMessage());
        }

        fin = true;
      }
    }

    public boolean done() {
      return fin;
    }
  }
}