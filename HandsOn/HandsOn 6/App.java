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
      AgentController controller = container.createNewAgent("Cardiologo", "agents.Cardiology", null);
      controller.start();

      controller = container.createNewAgent("Gastroenterologo", "agents.Gastroenterology", null);
      controller.start();

      controller = container.createNewAgent("Endocrinologo", "agents.Endocrinology", null);
      controller.start();

      controller = container.createNewAgent("Paciente", "agents.Patient", null);
      controller.start();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}