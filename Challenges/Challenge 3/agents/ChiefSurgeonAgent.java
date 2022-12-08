package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
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
        //System.out.println("Mensaje recibido: " + mensaje.getContent());
        
        //System.out.println("Mensaje recibido: " + mensaje.getContent());

        try {
          clips.eval("(clear)");

          clips.eval("(assert " + mensaje.getContent() + " )");

          clips.build("(defrule r1 (patient ready) =>  (assert (patient anesthetize)))");

          clips.eval("(run)");

          //Envio de mensaje a anestesista
          AID id = new AID();
          id.setLocalName("Anestesista");

          ACLMessage mensajeEnviar = new ACLMessage(ACLMessage.INFORM);

          mensajeEnviar.setSender(getAID());
          mensajeEnviar.addReceiver(id);
          mensajeEnviar.setContent("(patient anesthetize)");

          System.out.println(getLocalName() + ": Anesteciar paciente");

          send(mensajeEnviar);
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