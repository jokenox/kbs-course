package agents;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import net.sf.clipsrules.jni.*;
 
public class Endocrinology extends Agent {
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

          clips.build("(defrule r1 (sintoma dolor-articular) (sintoma deformidad-fisica) (sintoma voz-grave) => (retract 1) (printout t 'Endocrinólogo: El_diagnóstico_es_Acromegalia' crlf))");
          clips.build("(defrule r2 (sintoma incapacidad-lactancia) (sintoma amenorrea) (sintoma presion-baja) (sintoma perdida-bello) => (retract 1) (printout t 'Endocrinólogo: El_diagnóstico_es_Síndrome de Sheehan' crlf))");
          clips.build("(defrule r3 (sintoma mestruacion-irregular) (sintoma aumento-peso) (sintoma quistes-ovarios) => (retract 1) (printout t 'Endocrinólogo: El_diagnóstico_es_Síndrome de ovario políquistico' crlf))");
          clips.build("(defrule r4 (sintoma letargo) (sintoma caida-pelo) (sintoma aumento-peso) (sintoma agrandamiento-tiroides) => (retract 1) (printout t 'Endocrinólogo: El_diagnóstico_es_Hipotiroidismo' crlf))");
          clips.build("(defrule r5 (sintoma poliuria) (sintoma polidipsia) (sintoma polifagia) => (retract 1) (printout t 'Endocrinólogo: El_diagnóstico_es_Diabetes' crlf))");

          clips.eval("(run)");

          clips.build("(defrule r6 (diagnosis no) => (printout t 'Endocrinólogo: No_tengo_un_diagnóstico' crlf))");
          
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