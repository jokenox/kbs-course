package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
 
public class NurseAgent extends Agent {

  protected void setup() {
    addBehaviour(new EmisorComportaminento());
  }

  private class EmisorComportaminento extends SimpleBehaviour {
    boolean fin = false;
  
    public void action() {
      System.out.println(getLocalName() + ": Preparando paciente");

      AID id = new AID();
      id.setLocalName("Jefe-Cirugia");

      // Creación del objeto ACLMessage
      ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);

      //Rellenar los campos necesarios del mensaje
      mensaje.setSender(getAID());
      mensaje.addReceiver(id);
      mensaje.setContent("(patient ready)");

      //Envia el mensaje a los destinatarios
      send(mensaje);

      System.out.println(getLocalName() + ": Paciente preparado");

      fin = true;
    }

    public boolean done() {
      return fin;
    }
  }

}