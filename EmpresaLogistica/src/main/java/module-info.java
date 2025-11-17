module co.edu.uniquindio.empresalogistica {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires itextpdf;
    requires java.desktop;

    exports co.edu.uniquindio.empresalogistica;
    exports co.edu.uniquindio.empresalogistica.ViewController to javafx.fxml;
    exports co.edu.uniquindio.empresalogistica.Controller to javafx.fxml;
    exports co.edu.uniquindio.empresalogistica.Factory;
    exports co.edu.uniquindio.empresalogistica.Model;
    exports co.edu.uniquindio.empresalogistica.Mapping.Dto;



    opens co.edu.uniquindio.empresalogistica.ViewController to javafx.fxml, javafx.base;
    opens co.edu.uniquindio.empresalogistica to javafx.fxml;
    opens co.edu.uniquindio.empresalogistica.Mapping.Dto to javafx.base;
    opens co.edu.uniquindio.empresalogistica.Controller to javafx.fxml;



}