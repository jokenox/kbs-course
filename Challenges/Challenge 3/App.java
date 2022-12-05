import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class App {
  ContainerController container;
  
  public static void main(String[] args){
    App app = new App();
    app.run();
  }
  
  public void run(){
    jade.core.Runtime jadeRuntime = jade.core.Runtime.instance();
    Profile jadeProfile = new ProfileImpl();

    jadeProfile.setParameter(Profile.MAIN_HOST, "localhost");
    jadeProfile.setParameter(Profile.GUI, "true");

    container = jadeRuntime.createMainContainer(jadeProfile);
    
    try{
      AgentController controller = container.createNewAgent("anestesista", "agents.AnesthesiologistAgent", null);
      controller.start();

      controller = container.createNewAgent("cirujano", "agents.SurgeonAgent", null);
      controller.start();

      controller = container.createNewAgent("enfermera", "agents.NurseAgent", null);
      controller.start();

      controller = container.createNewAgent("jefe-cirugia", "agents.ChiefSurgeonAgent", null);
      controller.start();

      controller = container.createNewAgent("paciente", "agents.PatientAgent", null);
      controller.start();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}