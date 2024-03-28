module com.thanhthuan.coffe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    opens com.thanhthuan.coffe to javafx.fxml;
    exports com.thanhthuan.coffe;
}