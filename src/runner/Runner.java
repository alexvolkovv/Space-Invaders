package runner;

import controllers.Controller;
import model.GameModel;
import views.View;

public class Runner {
    public static void main(String[] args) {
        GameModel mainModel = new GameModel();
        Controller controller = new Controller(mainModel);
        View view = new View(controller);
    }
}
