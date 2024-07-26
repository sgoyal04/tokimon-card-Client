module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;

    opens ca.cmpt213.asn5.client to javafx.fxml;
    exports ca.cmpt213.asn5.client;
}