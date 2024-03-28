module org.examplejdbc.term3java {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.examplejdbc.term3java to javafx.fxml;
    exports org.examplejdbc.term3java;
}