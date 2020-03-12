package Multimedia_project_front;

import Multimedia_project_front.MyMenus.ApplicationMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;


public class MyMenuBarBegin extends MenuBar {


    public MyMenuBarBegin() { //Stage _primaryStage) {


        //Create your Application menu
        final Menu application = new ApplicationMenu();


        // Create and add menus to menubar
        this.getMenus().addAll(application);

    }


}

