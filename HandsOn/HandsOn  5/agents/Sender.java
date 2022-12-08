package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
 
public class Sender extends Agent {

  protected void setup() {
    addBehaviour(new EmisorComportaminento());
  }

  private class EmisorComportaminento extends SimpleBehaviour {
    boolean fin = false;
  
    public void action() {
      System.out.println(getLocalName() + ": Preparandose para enviar un mensaje a receptor");

      AID id = new AID();
      id.setLocalName("Receptor");

      // Creación del objeto ACLMessage
      ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);

      //Rellenar los campos necesarios del mensaje
      mensaje.setSender(getAID());
      mensaje.setLanguage("Clips");
      mensaje.addReceiver(id);
      mensaje.setContent("(sintoma tos) (sintoma dificultad-respirar) (sintoma perdida-olfato) (sintoma perdida-gusto)");

      //Envia el mensaje a los destinatarios
      send(mensaje);

      System.out.println(getLocalName() + ": Mensaje enviado a receptor");

      myAgent.doDelete();

      fin = true;
    }

    public boolean done() {
      return fin;
    }

    public int onEnd() {
      return super.onEnd();
    }
  }

}