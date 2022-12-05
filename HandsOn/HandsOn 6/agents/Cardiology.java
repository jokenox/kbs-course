package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;
 
public class Cardiology extends Agent {
  private Environment clips;
   
  protected void setup() {
    clips = new Environment();

    addBehaviour(new ReceptorComportaminento());
  }

  private class ReceptorComportaminento extends SimpleBehaviour {
    private boolean fin = false;

    public void action() {
      System.out.println("Preparandose para recibir");

      //Obtiene el primer mensaje de la cola de mensajes
      ACLMessage mensaje = receive();

      if (mensaje!= null) {
        System.out.println(getLocalName() + ": Acaba de recibir un mensaje con sintomas");
        System.out.println(mensaje.toString());

        try {
          clips.eval("(clear)");

          clips.eval("(assert " + mensaje.getContent() + " )");

          clips.build("(defrule r1 (sintoma fatiga) (sintoma dificultad-respirar) (sintoma palpitaciones) => (printout t 'El diagnostico es: Insuficiencia cardíaca' crlf))");
          clips.build("(defrule r2 (sintoma muerte-subita) (sintoma desmayo) (sintoma paro-cardiaco) => (printout t 'El diagnostico es: Síndrome QT Largo' crlf))");
          clips.build("(defrule r3 (sintoma dolor-pecho-brazo) (sintoma sudoracion) (sintoma mareo) => (printout t 'El diagnostico es: Infarto agudo al miocardio' crlf))");
          clips.build("(defrule r4 (sintoma edema) (sintoma cefalea) (sintoma vision-borroza) => (printout t 'El diagnostico es: Hipertensión arterial' crlf))");
          clips.build("(defrule r5 (sintoma cianosis) (sintoma soplo) (sintoma dificultad-respirar) => (printout t 'El diagnostico es: Tetralogía de Fallot' crlf))");
          
          clips.eval("(reset)");
          clips.eval("(run)");
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