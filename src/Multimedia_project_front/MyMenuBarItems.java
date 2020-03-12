package Multimedia_project_front;

import Multimedia_project_front.MyMenus.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;


public class MyMenuBarItems extends MenuBar {


    public MyMenuBarItems() { //Stage _primaryStage) {


        //Create your Application menu
        final Menu application = new ApplicationMenu();


        //Create details menu
        final Menu details = new DetailsMenu();


        // Create and add menus to menubar
        this.getMenus().addAll(application, details);

    }


}

