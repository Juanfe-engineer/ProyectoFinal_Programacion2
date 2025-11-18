package co.edu.uniquindio.empresalogistica.ViewController;

import co.edu.uniquindio.empresalogistica.Controller.LoginController;
import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Model.Enums.TipoPerfil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginViewController {

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

    private EmpresaLogisticaFactory factory;
    private LoginController loginController;

    @FXML
    public void initialize() {
        System.out.println(" LoginViewController inicializado");

        // Inicializar ComboBox
        cmbTipoPerfil.getItems().addAll("Usuario", "Repartidor", "Administrador");
        cmbTipoPerfil.setValue("Usuario");

        // Inicializar controllers
        factory = EmpresaLogisticaFactory.getInstance();
        loginController = new LoginController();

        // Mensaje de ayuda
        lblMensaje.setText("programa de logistica");
        lblMensaje.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");

        System.out.println(" Inicialización completa");
    }

    /*
     * INICIAR SESIÓN
     */
    @FXML
    private void handleIniciarSesion() {
        System.out.println("Botón Iniciar Sesión presionado");

        String tipoUsuario = cmbTipoPerfil.getValue();
        String correo = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        // ===== VALIDACIONES =====
        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor, ingrese correo y contraseña");
            return;
        }

        if (!esEmailValido(correo)) {
            mostrarError("Formato de correo inválido");
            return;
        }

        System.out.println("Intentando login como: " + tipoUsuario + " - " + correo);

        try {
            // ===== USUARIO =====
            if ("Usuario".equals(tipoUsuario)) {
                // 1. Verificar que exista el usuario
                UsuarioDTO usuario = factory.obtenerUsuarioPorCorreo(correo);

                if (usuario == null) {
                    mostrarError("Usuario no encontrado");
                    return;
                }

                // 2. Validar contraseña usando el factory
                if (!factory.validarCredenciales(correo, contrasena)) {
                    mostrarError("Contraseña incorrecta");
                    return;
                }

                System.out.println("Usuario encontrado: " + usuario.getNombre());
                mostrarExito("¡Bienvenido " + usuario.getNombre() + "!");

                // 3. Abrir dashboard
                abrirUsuarioDashboard(usuario);
            }
            // ===== REPARTIDOR =====

            else if ("Repartidor".equals(tipoUsuario)) {
                // 1. Verificar que exista el repartidor
                RepartidorDTO repartidor = factory.obtenerRepartidorPorCorreo(correo);

                if (repartidor == null) {
                    mostrarError(" Repartidor no encontrado");
                    return;
                }

                // 2. Validar contraseña
                if (!factory.validarCredenciales(correo, contrasena)) {
                    mostrarError(" Contraseña incorrecta");
                    return;
                }

                System.out.println(" Repartidor encontrado: " + repartidor.getNombre());
                mostrarExito("¡Bienvenido " + repartidor.getNombre() + "!");

                // 3. Abrir dashboard
                abrirRepartidorDashboard(repartidor);
            }
            // ===== ADMINISTRADOR =====
            else if ("Administrador".equals(tipoUsuario)) {
                if (!factory.validarCredenciales(correo, contrasena)) {
                    mostrarError(" Credenciales de administrador incorrectas");
                    return;
                }

                // Solo permitir el correo específico de admin
                if (!correo.equals("admin@admin.com")) {
                    mostrarError(" Correo de administrador no válido");
                    return;
                }

                System.out.println(" Administrador autenticado");
                mostrarExito("¡Bienvenido Administrador!");

                // Abrir dashboard de administrador
                abrirAdministradorDashboard();
            }

        } catch (Exception e) {
            System.err.println(" Error en login: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error: " + e.getMessage());
        }
    }

    /*
     * REGISTRARSE
     */
    @FXML
    private void handleRegistrarse() {
        System.out.println("Abriendo ventana de registro...");

        try {
            Stage stage = (Stage) btnRegistrarse.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Registro.fxml")
            );

            if (loader.getLocation() == null) {
                System.err.println("No se encontró Registro.fxml");
                mostrarError("No se encontró el archivo de registro.\nVerifique que existe: /fxml/Registro.fxml");
                return;
            }

            System.out.println("FXML encontrado: " + loader.getLocation());

            Scene scene = new Scene(loader.load(), 450, 700);

            stage.setTitle("Logística Express - Registro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            System.out.println("Ventana de registro abierta");

        } catch (Exception e) {
            System.err.println("Error al abrir registro: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al abrir registro: " + e.getMessage());
        }
    }

    /*
     * Abrir dashboard de usuario
     */
    private void abrirUsuarioDashboard(UsuarioDTO usuario) {
        try {
            System.out.println(" Abriendo dashboard de usuario...");

            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Usuario.fxml")
            );

            if (loader.getLocation() == null) {
                System.err.println("No se encontró el archivo Usuario.fxml");
                mostrarError("Error: No se encontró el archivo Usuario.fxml");
                return;
            }

            System.out.println("FXML encontrado: " + loader.getLocation());

            Scene scene = new Scene(loader.load(), 1200, 750);

            stage.setTitle("Logística Express - Usuario: " + usuario.getNombre());
            stage.setScene(scene);
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();

            System.out.println(" Dashboard abierto exitosamente");

        } catch (Exception e) {
            System.err.println(" Error al cargar la pantalla de usuario");
            e.printStackTrace();
            mostrarError("Error al cargar la pantalla: " + e.getMessage() +
                    "\n\nVerifique que existe: /co/edu/uniquindio/empresalogistica/fxml/Usuario.fxml");
        }
    }

    /*
     * ABRIR DASHBOARD DE REPARTIDOR
     */
    private void abrirRepartidorDashboard(RepartidorDTO repartidor) {
        try {
            System.out.println("Abriendo dashboard de repartidor...");

            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Repartidor.fxml")
            );

            if (loader.getLocation() == null) {
                System.err.println("No se encontró el archivo Repartidor.fxml");
                mostrarError("Error: No se encontró el archivo Repartidor.fxml");
                return;
            }

            System.out.println("FXML encontrado: " + loader.getLocation());

            Scene scene = new Scene(loader.load(), 1200, 750);

            stage.setTitle("Logística Express - Repartidor: " + repartidor.getNombre());
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();

            System.out.println("Dashboard de repartidor abierto exitosamente");

        } catch (Exception e) {
            System.err.println("Error al cargar la pantalla de repartidor");
            e.printStackTrace();
            mostrarError("Error al cargar la pantalla: " + e.getMessage());
        }
    }


    /*
     *  Abrir dashboard de administrador
     */
    private void abrirAdministradorDashboard() {
        try {
            System.out.println("Abriendo dashboard de administrador...");

            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Administrador.fxml")
            );

            if (loader.getLocation() == null) {
                System.err.println("No se encontró el archivo Administrador.fxml");
                mostrarError("Error: No se encontró el archivo Administrador.fxml");
                return;
            }

            System.out.println("FXML encontrado: " + loader.getLocation());

            Scene scene = new Scene(loader.load(), 1400, 900);

            stage.setTitle("Logística Express - Panel de Administración");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();

            System.out.println("Dashboard de administrador abierto exitosamente");

        } catch (Exception e) {
            System.err.println("Error al cargar la pantalla de administrador");
            e.printStackTrace();
            mostrarError("Error al cargar la pantalla: " + e.getMessage());
        }
    }


    /*
     * Validar formato de email
     */
    private boolean esEmailValido(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /*
     * Obtener tipo de perfil del ComboBox
     */
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

    /*
     * Limpiar campos del formulario
     */
    private void limpiarCampos() {
        txtUsuario.clear();
        txtContrasena.clear();
        cmbTipoPerfil.setValue("Usuario");
        lblMensaje.setText("Empresa Logistica 2025");
        lblMensaje.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
    }

    /*
     * Mostrar mensaje de error
     */
    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 11px; -fx-font-weight: bold;");
        System.out.println(mensaje);
    }

    /**
     * Mostrar mensaje de éxito
     */
    private void mostrarExito(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 11px; -fx-font-weight: bold;");
        System.out.println(mensaje);
    }

    /**
     * Mostrar diálogo de información
     */
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        System.out.println(mensaje);
    }
}