package Multimedia_project_back;

import javafx.beans.property.SimpleStringProperty;

public class Log {

    private final SimpleStringProperty label;
    private final SimpleStringProperty info;

    public Log(String fName, String lName) {
        this.label = new SimpleStringProperty(fName);
        this.info = new SimpleStringProperty(lName);
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String _label) {
        label.set(_label);
    }

    public String getInfo() {
        return info.get();
    }

    public void setInfo(String _info) {
        info.set(_info);
    }

}

