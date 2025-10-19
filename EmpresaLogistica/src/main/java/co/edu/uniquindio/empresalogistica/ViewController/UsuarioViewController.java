package co.edu.uniquindio.empresalogistica.ViewController;

import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
        import javafx.stage.Stage;

public class UsuarioViewController {

    @FXML
    private Label lblBienvenida;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblFechaRegistro;

    @FXML
    private Label lblEstado;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtMetodoPago;

    @FXML
    private TextField txtEnvios;

    @FXML
    private Button btnGuardarCambios;

    @FXML
    private Label lblMensaje;

    private EmpresaLogisticaFactory factory;
    private UsuarioDTO usuarioActual;

    @FXML
    public void initialize() {
        factory = EmpresaLogisticaFactory.getInstance();
        usuarioActual = factory.getUsuarioActual();

        if (usuarioActual != null) {
            cargarDatosUsuario();
        } else {
            mostrarError("No hay usuario logeado");
        }
    }

    private void cargarDatosUsuario() {
        lblBienvenida.setText("¡Bienvenido, " + usuarioActual.getNombre() + "!");
        lblCorreo.setText("Correo: " + usuarioActual.getCorreo());
        lblTelefono.setText("Teléfono: " + usuarioActual.getTelefono());
        lblFechaRegistro.setText("Fecha Registro: " + usuarioActual.getFechaRegistro());
        lblEstado.setText("Estado: " + usuarioActual.getEstado());

        txtDireccion.setText(usuarioActual.getDireccionFrecuente());
        txtMetodoPago.setText(usuarioActual.getMetodosPago());
        txtEnvios.setText(usuarioActual.getEnviosRealizados());
    }

    @FXML
    public void handleGuardarCambios() {
        try {
            usuarioActual.setDireccionFrecuente(txtDireccion.getText());
            usuarioActual.setMetodosPago(txtMetodoPago.getText());

            factory.actualizarUsuario(usuarioActual);
            mostrarInfo("Cambios guardados exitosamente");
        } catch (Exception e) {
            mostrarError("Error al guardar cambios: " + e.getMessage());
        }
    }

    @FXML
    public void handleVerEnvios() {
        mostrarInfo("Módulo de envíos en desarrollo");
    }

    @FXML
    public void handleVerHistorial() {
        mostrarInfo("Historial de envíos en desarrollo");
    }


    @FXML
    public void handleCerrarSesion() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar sesión");
        confirmacion.setHeaderText("¿Deseas cerrar sesión?");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            factory.logout();

            try {
                Stage stage = (Stage) lblBienvenida.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Login.fxml"));
                Scene scene = new Scene(loader.load(), 400, 600);
                stage.setTitle("Logística Express - Login");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                mostrarError("Error al cerrar sesión: " + e.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}