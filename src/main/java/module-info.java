module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.fxmisc.richtext;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}
