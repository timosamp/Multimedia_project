package Multimedia_project_front;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class MonitorLogs extends BorderPane {

//    private TextArea console;
    private PrintStream ps;
    private TextArea textArea;

    public MonitorLogs() {
        textArea = new TextArea();

        textArea.setEditable(false);
        ps = new PrintStream(new Console(textArea));
        Text title = new Text("Monitor Logs");
        VBox vb = new VBox();
        String font = "-fx-background-color: darkgrey";

        vb.getChildren().add(title);

        vb.setStyle(font);
        vb.setAlignment(Pos.CENTER);
//        vb.setMaxSize(5, 5);


        this.setTop(vb);
        this.setCenter(textArea);

        System.setOut(ps);
        System.setErr(ps);
    }

//    public void button(ActionEvent event) {
//        System.setOut(ps);
//        System.setErr(ps);
//        System.out.println("Hello World");
//    }

    public static class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea _console) {
            this.console = _console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }
}