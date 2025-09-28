module co.edu.uniquindio.empresalogistica {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.empresalogistica to javafx.fxml;
    exports co.edu.uniquindio.empresalogistica;
}