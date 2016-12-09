package nz.co.thescene.console.menu.webadmin;

//import nz.co.thescene.DataGenerator;
import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.console.menu.Menu;
import nz.co.thescene.dto.json.hal.MemberResource;
import org.springframework.stereotype.Component;

@Component
public class DataGenerationMenu extends Menu {


    @Override
    protected void printMenu() {
        printHeader("Welcome to data generator!");
        System.out.println("1) Generate events");
        System.out.println("b) Back to previous menu");
    }

    @Override
    protected void processUserInput(MemberResource member, String userInput) {
        if (userInput.equals("1")) {
            String quantity = getUserInput("Please enter max quantity of events. Events generated with step by 20. ");
            int limit = Integer.parseInt(quantity);
            //DataGenerator dataGenerator = new DataGenerator();
            //dataGenerator.generateEvents(member, limit);
        } else {
            handleInvalidInput();
        }
    }

    @Override
    public void onError(SceneClientException e) {

    }
}
