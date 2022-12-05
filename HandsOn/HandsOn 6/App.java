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
      AgentController controller = container.createNewAgent("paciente", "agents.Patient", null);
      controller.start();

      controller = container.createNewAgent("cardiologo", "agents.Cardiology", null);
      controller.start();

      controller = container.createNewAgent("gastroenterologo", "agents.Gastroenterology", null);
      controller.start();

      controller = container.createNewAgent("endocrinologo", "agents.Endocrinology", null);
      controller.start();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}