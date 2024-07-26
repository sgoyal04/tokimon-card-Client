module Client {
    requires javafx.controls;
    requires javafx.fxml;

    opens ca.cmpt213.asn5.client to javafx.fxml;
    exports ca.cmpt213.asn5.client;
}