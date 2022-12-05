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
      System.out.println("Preparandose para recibir");

      //Obtiene el primer mensaje de la cola de mensajes
      ACLMessage mensaje = receive();

      if (mensaje!= null) {
        System.out.println(getLocalName() + ": Acaba de recibir un mensaje con sintomas");
        System.out.println(mensaje.toString());

        try {
          clips.eval("(clear)");

          clips.eval("(assert " + mensaje.getContent() + " )");

          clips.build("(defrule r1 (sintoma dolor-articular) (sintoma deformidad-fisica) (sintoma voz-grave) => (printout t 'El diagnostico es: Acromegalia' crlf))");
          clips.build("(defrule r2 (sintoma incapacidad-lactancia) (sintoma amenorrea) (sintoma presion-baja) (sintoma perdida-bello) => (printout t 'El diagnostico es: Síndrome de Sheehan' crlf))");
          clips.build("(defrule r3 (sintoma mestruacion-irregular) (sintoma aumento-peso) (sintoma quistes-ovarios) => (printout t 'El diagnostico es: Síndrome de ovario políquistico' crlf))");
          clips.build("(defrule r4 (sintoma letargo) (sintoma caida-pelo) (sintoma aumento-peso) (sintoma agrandamiento-tiroides) => (printout t 'El diagnostico es: Hipotiroidismo' crlf))");
          clips.build("(defrule r5 (sintoma poliuria) (sintoma polidipsia) (sintoma polifagia) => (printout t 'El diagnostico es: Diabetes' crlf))");
          
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