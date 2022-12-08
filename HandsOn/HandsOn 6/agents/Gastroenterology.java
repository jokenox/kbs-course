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

      //Obtiene el primer mensaje de la cola de mensajes
      ACLMessage mensaje = receive();

      if (mensaje!= null) {
        System.out.println(getLocalName() + ": Acaba de recibir un mensaje con sintomas");
        //System.out.println(mensaje.toString());

        try {
          clips.eval("(clear)");

          clips.eval("(assert (diagnosis no))");

          clips.eval("(assert " + mensaje.getContent() + " )");

          clips.build("(defrule r1 (sintoma acidez) (sintoma dolor-epigastrio) (sintoma perdida-apetito) (sintoma eructos) => (retract 1) (printout t 'Gastroenterólogo: El_diagnóstico_es_Gastritis' crlf))");
          clips.build("(defrule r2 (sintoma vomito) (sintoma fiebre) (sintoma ictericia) (sintoma dolor-abdominal) => (retract 1) (printout t 'Gastroenterólogo: El_diagnóstico_es_Colecistitis' crlf))");
          clips.build("(defrule r3 (sintoma dolor-abdominal) (sintoma diarrea) (sintoma flatulencias) => (retract 1) (printout t 'Gastroenterólogo: El_diagnóstico_es_Colitis' crlf))");
          clips.build("(defrule r4 (sintoma defecacion-oscura) (sintoma vomito-sangre) (sintoma palidez) (sintoma dolor-abdominal) => (retract 1) (printout t 'Gastroenterólogo: El_diagnóstico_es_Sangrado_de_tubo_digestivo_alto' crlf))");
          clips.build("(defrule r5 (sintoma perdida-peso) (sintoma dolor-abdominal) (sintoma prurito-anal) => (retract 1) (printout t 'Gastroenterólogo: El_diagnóstico_es_Paracitosis_intestinal' crlf))");

          clips.eval("(run)");

          clips.build("(defrule r6 (diagnosis no) => (printout t 'Gastroenterólogo: No_tengo_un_diagnóstico' crlf))");
          
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