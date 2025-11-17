package co.edu.uniquindio.empresalogistica.Controller;

import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtMetodoPago;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnRegistrar;

    private final EmpresaLogisticaFactory factory = EmpresaLogisticaFactory.getInstance();

    @FXML
    void onRegistrar(ActionEvent event) {
        try {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();
            String direccion = txtDireccion.getText();
            String metodoPago = txtMetodoPago.getText();
            String password = txtPassword.getText();

            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || direccion.isEmpty() ||
                    metodoPago.isEmpty() || password.isEmpty()) {
                mostrarAlerta("Campos incompletos", "Por favor completa todos los campos", Alert.AlertType.WARNING);
                return;
            }

            UsuarioDTO nuevoUsuario = new UsuarioDTO();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setDireccionFrecuente(direccion);
            nuevoUsuario.setMetodosPago(metodoPago);
            nuevoUsuario.setPassword(password);

            factory.crearUsuario(nuevoUsuario);

            mostrarAlerta("Registro exitoso", "Tu cuenta fue creada correctamente. Inicia sesión ahora.", Alert.AlertType.INFORMATION);

            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.close();


        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
