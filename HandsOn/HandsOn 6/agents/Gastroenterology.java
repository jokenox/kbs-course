package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;
 
public class Gastroenterology extends Agent {
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

          clips.build("(defrule r1 (sintoma acidez) (sintoma dolor-epigastrio) (sintoma perdida-apetito) (sintoma eructos) => (printout t 'El diagnostico es: Gastritis' crlf))");
          clips.build("(defrule r2 (sintoma vomito) (sintoma fiebre) (sintoma ictericia) (sintoma dolor-abdominal) => (printout t 'El diagnostico es: Colecistitis' crlf))");
          clips.build("(defrule r3 (sintoma dolor-abdominal) (sintoma diarrea) (sintoma flatulencias) => (printout t 'El diagnostico es: Colitis' crlf))");
          clips.build("(defrule r4 (sintoma defecacion-oscura) (sintoma vomito-sangre) (sintoma palidez) (sintoma dolor-abdominal) => (printout t 'El diagnostico es: Sangrado de tubo digestivo alto' crlf))");
          clips.build("(defrule r5 (sintoma perdida-peso) (sintoma dolor-abdominal) (sintoma prurito-anal) => (printout t 'El diagnostico es: Paracitosis intestinal' crlf))");
          
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