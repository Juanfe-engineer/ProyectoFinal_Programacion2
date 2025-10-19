package co.edu.uniquindio.empresalogistica.ViewController;

import co.edu.uniquindio.empresalogistica.Controller.LoginController;
import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Model.Enums.TipoPerfil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class LoginViewController {
    private LoginController loginController;

    @FXML
    private ComboBox<String> cmbTipoPerfil;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Label lblMensaje;

    private EmpresaLogisticaFactory empresaLogisticaFactory;

    @FXML
    public void initialize() {
        cmbTipoPerfil.getItems().addAll("Usuario", "Repartidor", "Administrador");
        cmbTipoPerfil.setValue("Usuario");

        empresaLogisticaFactory = EmpresaLogisticaFactory.getInstance();

        lblMensaje.setText("Demo: juan@email.com");
    }


    @FXML
    private void handleIniciarSesion() {
        String tipoUsuario = cmbTipoPerfil.getValue();
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        String correo = txtUsuario.getText().trim();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor, ingrese usuario y contraseña");
            return;
        }

        try {
            if ("Usuario".equals(tipoUsuario)) {
                UsuarioDTO usuarioLogeado = empresaLogisticaFactory.obtenerUsuarioPorCorreo(correo);

                if (usuarioLogeado == null) {
                    mostrarError("Usuario no encontrado");
                    return;
                }

                lblMensaje.setText("¡Bienvenido " + usuarioLogeado.getNombre() + "!");
                abrirUsuarioDashboard(usuarioLogeado);
            } else if ("Administrador".equals(tipoUsuario)) {
                mostrarInfo("Módulo de Administrador en desarrollo");
            } else if ("Repartidor".equals(tipoUsuario)) {
                mostrarInfo("Módulo de Repartidor en desarrollo");
            }

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void handleRegistrarse() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        String tipoPerfil = cmbTipoPerfil.getValue();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor, ingrese usuario y contraseña");
            return;
        }

        if (contrasena.length() < 6) {
            mostrarError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        TipoPerfil tipo = obtenerTipoPerfil(tipoPerfil);

        if (loginController.registrarse(usuario, contrasena, tipo, usuario)) {
            mostrarExito("¡Registro exitoso! Ahora puede iniciar sesión.");
            limpiarCampos();
        } else {
            mostrarError("El usuario ya existe o hubo un error en el registro");
        }
    }


    private void abrirUsuarioDashboard(UsuarioDTO usuario) {
        try {
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Usuario.fxml"));

            Scene scene = new Scene(loader.load(), 1000, 700);
            stage.setTitle("Logística Express - Usuario");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            mostrarError("Error al cargar la pantalla: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private TipoPerfil obtenerTipoPerfil(String tipoPerfil) {
        switch (tipoPerfil) {
            case "Repartidor":
                return TipoPerfil.REPARTIDOR;
            case "Administrador":
                return TipoPerfil.ADMINISTRADOR;
            default:
                return TipoPerfil.USUARIO;
        }
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 11px;");
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 11px;");
    }

    private void limpiarCampos() {
        txtUsuario.clear();
        txtContrasena.clear();
        lblMensaje.setText("");
        cmbTipoPerfil.setValue("Usuario");
    }
}

