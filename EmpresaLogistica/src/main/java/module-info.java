module co.edu.uniquindio.empresalogistica {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports co.edu.uniquindio.empresalogistica;
    exports co.edu.uniquindio.empresalogistica.ViewController to javafx.fxml;
    exports co.edu.uniquindio.empresalogistica.Controller to javafx.fxml;
    exports co.edu.uniquindio.empresalogistica.Factory;
    exports co.edu.uniquindio.empresalogistica.Model;


    opens co.edu.uniquindio.empresalogistica.ViewController to javafx.fxml;
    opens co.edu.uniquindio.empresalogistica to javafx.fxml;
}