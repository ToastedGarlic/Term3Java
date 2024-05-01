module org.examplejdbc.term3java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.jdbc.term3java to javafx.fxml;
    exports org.example.jdbc.term3java;
}