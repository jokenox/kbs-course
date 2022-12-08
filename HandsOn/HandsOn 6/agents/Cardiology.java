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

      //Obtiene el primer mensaje de la cola de mensajes
      ACLMessage mensaje = receive();

      if (mensaje!= null) {
        System.out.println(getLocalName() + ": Acaba de recibir un mensaje con sintomas");
        //System.out.println(mensaje.toString());

        try {
          clips.eval("(clear)");

          clips.eval("(assert (diagnosis no))");

          clips.eval("(assert " + mensaje.getContent() + " )");

          clips.build("(defrule r1 (sintoma fatiga) (sintoma dificultad-respirar) (sintoma palpitaciones) => (retract 1) (printout t 'Cardiólogo: El_diagnóstico_es_Insuficiencia_cardíaca' crlf))");
          clips.build("(defrule r2 (sintoma muerte-subita) (sintoma desmayo) (sintoma paro-cardiaco) => (retract 1) (printout t 'Cardiólogo: El_diagnóstico_es_Síndrome_QT_Largo' crlf))");
          clips.build("(defrule r3 (sintoma dolor-pecho-brazo) (sintoma sudoracion) (sintoma mareo) => (retract 1) (printout t 'Cardiólogo: El_diagnóstico_es_Infarto_agudo_al_miocardio' crlf))");
          clips.build("(defrule r4 (sintoma edema) (sintoma cefalea) (sintoma vision-borroza) => (retract 1) (printout t 'Cardiólogo: El_diagnóstico_es_Hipertensión_arterial' crlf))");
          clips.build("(defrule r5 (sintoma cianosis) (sintoma soplo) (sintoma dificultad-respirar) => (retract 1) (printout t 'Cardiólogo: El_diagnóstico_es_Tetralogía_de_Fallot' crlf))");

          clips.eval("(run)");

          clips.build("(defrule r6 (diagnosis no) => (printout t 'Cardiólogo: No_tengo_un_diagnóstico' crlf))");

          clips.eval("(run)");
        } catch (Exception e){
          System.out.println (e.getMessage());
        }

        myAgent.doDelete();

        fin = true;
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