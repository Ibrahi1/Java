module com.fstt.esalaf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.fstt.esalaf to javafx.fxml;
    exports com.fstt.esalaf;
}