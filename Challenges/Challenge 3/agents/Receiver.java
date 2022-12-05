package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import net.sf.clipsrules.jni.*;
 
public class Receiver extends Agent {
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

          clips.build("(defrule r1 (sintoma dolor-garganta) (sintoma inflamacion-amigdalas) (sintoma fiebre) => (printout t 'El diagnostico es: infeccion de garganta' crlf))");
          clips.build("(defrule r2 (sintoma congestion-nasal) (sintoma tos) (sintoma dolor-garganta) => (printout t 'El diagnostico es: gripe' crlf))");
          clips.build("(defrule r3 (sintoma diarrea) (sintoma dolor-abdomen) (sintoma nauseas) => (printout t 'El diagnostico es: gastroenteritis' crlf))");
          clips.build("(defrule r4 (sintoma congestion-nasal) (sintoma picazon-nasal) (sintoma enrojecimiento-ojos) => (printout t 'El diagnostico es: rinitis alergica' crlf))");
          clips.build("(defrule r5 (sintoma tos) (sintoma dificultad-respirar) (sintoma perdida-olfato) (sintoma perdida-gusto) => (printout t 'El diagnostico es: covid' crlf))");

          clips.eval("(restart)");
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