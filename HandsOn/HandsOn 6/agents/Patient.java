package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
 
public class Patient extends Agent {

  protected void setup() {
    addBehaviour(new EmisorComportaminento());
  }

  private class EmisorComportaminento extends SimpleBehaviour {
    boolean fin = false;
  
    public void action() {
      System.out.println(getLocalName() + ": Preparandose para enviar un mensaje a doctores");

      AID id = new AID();

      // Creación del objeto ACLMessage
      ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);

      //Rellenar los campos necesarios del mensaje
      mensaje.setSender(getAID());
      mensaje.setLanguage("Clips");
      mensaje.setContent("(sintoma vomito) (sintoma fiebre) (sintoma ictericia) (sintoma dolor-abdominal)");

      //Envia el mensaje a los destinatarios
      id.setLocalName("Endocrinologo");
      mensaje.addReceiver(id);
      send(mensaje);

      id.setLocalName("Cardiologo");
      mensaje.addReceiver(id);
      send(mensaje);

      id.setLocalName("Gastroenterologo");
      mensaje.addReceiver(id);
      send(mensaje);
      
      System.out.println(getLocalName() + ": Mensaje enviado a doctores");

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