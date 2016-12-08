package nz.co.thescene.console.menu.webadmin;


import nz.co.thescene.client.errors.SceneClientException;
import nz.co.thescene.console.menu.Menu;
import nz.co.thescene.dto.json.hal.MemberResource;

public class MessageManageMenu extends Menu{
    @Override
    protected void printMenu() {

        System.out.println("1)");

    }

    @Override
    protected void processUserInput(MemberResource member, String userInput) {

    }

    @Override
    public void onError(SceneClientException e) {

    }
}
