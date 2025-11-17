package co.edu.uniquindio.empresalogistica.ViewController;

import co.edu.uniquindio.empresalogistica.Controller.LoginController;
import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoUsuario;
import co.edu.uniquindio.empresalogistica.Model.Enums.TipoPerfil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class RegistroViewController {

    @FXML private ComboBox<String> cmbTipoRegistro;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private ComboBox<String> cmbMetodoPago;
    @FXML private TextField txtZonaCobertura;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Button btnRegistrar;
    @FXML private Button btnCancelar;
    @FXML private Label lblMensaje;

    // VBoxes para mostrar/ocultar según tipo
    @FXML private VBox vboxDireccion;
    @FXML private VBox vboxMetodoPago;
    @FXML private VBox vboxZonaCobertura;

    private LoginController loginController;
    private EmpresaLogisticaFactory factory;

    @FXML
    public void initialize() {
        System.out.println(" RegistroViewController inicializado");

        loginController = new LoginController();
        factory = EmpresaLogisticaFactory.getInstance();

        // Configurar ComboBox tipo de registro
        cmbTipoRegistro.getItems().addAll("Usuario", "Repartidor");
        cmbTipoRegistro.setValue("Usuario");

        // Configurar ComboBox método de pago
        cmbMetodoPago.getItems().addAll(
                "Tarjeta de Crédito",
                "Tarjeta de Débito",
                "Efectivo",
                "Billetera Digital",
                "Transferencia Bancaria"
        );
        cmbMetodoPago.setValue("Tarjeta de Debito");

        // Listener para cambiar campos según tipo
        cmbTipoRegistro.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarCamposPorTipo(newVal);
        });

        System.out.println(" RegistroViewController inicializado");
    }

    /**
     * Mostrar/ocultar campos según tipo de registro
     */
    private void actualizarCamposPorTipo(String tipo) {
        if ("Usuario".equals(tipo)) {
            vboxDireccion.setVisible(true);
            vboxDireccion.setManaged(true);
            vboxMetodoPago.setVisible(true);
            vboxMetodoPago.setManaged(true);
            vboxZonaCobertura.setVisible(false);
            vboxZonaCobertura.setManaged(false);
        } else { // Repartidor
            vboxDireccion.setVisible(false);
            vboxDireccion.setManaged(false);
            vboxMetodoPago.setVisible(false);
            vboxMetodoPago.setManaged(false);
            vboxZonaCobertura.setVisible(true);
            vboxZonaCobertura.setManaged(true);
        }
    }

    /**
     * ⭐ REGISTRAR
     */
    @FXML
    private void handleRegistrar() {
        System.out.println(" Botón Registrar presionado");

        String tipoRegistro = cmbTipoRegistro.getValue();
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        // Validaciones comunes
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            mostrarError("Complete todos los campos obligatorios");
            return;
        }

        if (!esEmailValido(correo)) {
            mostrarError("Ingrese un correo electrónico válido");
            return;
        }

        if (!esTelefonoValido(telefono)) {
            mostrarError("El teléfono debe tener 10 dígitos");
            return;
        }

        if (password.length() < 6) {
            mostrarError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!password.equals(confirmPassword)) {
            mostrarError("Las contraseñas no coinciden");
            return;
        }

        System.out.println(" Validaciones pasadas");

        try {
            if ("Usuario".equals(tipoRegistro)) {
                registrarUsuario(nombre, correo, telefono, password);
            } else {
                registrarRepartidor(nombre, correo, telefono, password);
            }
        } catch (Exception e) {
            System.err.println(" Error en registro: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al registrar: " + e.getMessage());
        }
    }

    /**
     * Registrar como Usuario
     */
    private void registrarUsuario(String nombre, String correo, String telefono, String password) throws Exception {
        String direccion = txtDireccion.getText().trim();
        String metodoPago = cmbMetodoPago.getValue();

        if (direccion.isEmpty()) {
            mostrarError("Ingrese su dirección");
            return;
        }

        System.out.println(" Iniciando registro de usuario: " + correo);

        // PASO 1: Crear UsuarioDTO
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setFechaRegistro(LocalDate.now());
        nuevoUsuario.setEstado(EstadoUsuario.ACTIVO.toString());
        nuevoUsuario.setDireccionFrecuente(direccion);
        nuevoUsuario.setMetodosPago(metodoPago);
        nuevoUsuario.setEnviosRealizados("0");

        // PASO 2: Crear usuario en Factory (esto valida que no exista)
        UsuarioDTO usuarioCreado = factory.crearUsuario(nuevoUsuario);
        System.out.println(" Usuario creado en Factory: " + usuarioCreado.getNombre());

        // PASO 3: CREAR CREDENCIAL DE AUTENTICACIÓN
        factory.crearCredencialUsuario(correo, password);
        System.out.println(" Credencial creada para: " + correo);

        // PASO 4: TAMBIÉN REGISTRAR EN SERVICIO DE AUTENTICACIÓN
        boolean credencialRegistrada = loginController.registrarse(
                correo,
                password,
                TipoPerfil.USUARIO,
                nombre
        );

        if (credencialRegistrada) {
            System.out.println(" Registro completo exitoso");
            mostrarExito("¡Registro exitoso! Redirigiendo al login...");
            esperarYVolverAlLogin();
        } else {
            // Si falla el registro en ServicioAutenticacion, eliminar el usuario creado
            factory.eliminarUsuario(usuarioCreado.getId());
            mostrarError("Error al crear credenciales de acceso");
        }
    }

    /**
     * Registrar como Repartidor
     */
    private void registrarRepartidor(String nombre, String correo, String telefono, String password) throws Exception {
        String zonaCobertura = txtZonaCobertura.getText().trim();

        if (zonaCobertura.isEmpty()) {
            mostrarError("Ingrese su zona de cobertura");
            return;
        }

        System.out.println(" Iniciando registro de repartidor: " + correo);

        // PASO 1: Crear RepartidorDTO
        RepartidorDTO nuevoRepartidor = new RepartidorDTO();
        nuevoRepartidor.setNombre(nombre);
        nuevoRepartidor.setCorreo(correo);
        nuevoRepartidor.setTelefono(telefono);
        nuevoRepartidor.setFechaRegistro(LocalDate.now());
        nuevoRepartidor.setZonaCobertura(zonaCobertura);
        nuevoRepartidor.setDisponibilidad("ACTIVO");
        nuevoRepartidor.setEstado("DISPONIBLE");
        nuevoRepartidor.setEnviosRealizados("0");
        nuevoRepartidor.setIncidenciasReportadas(0);

        // PASO 2: Crear repartidor en Factory
        RepartidorDTO repartidorCreado = factory.crearRepartidor(nuevoRepartidor);
        System.out.println(" Repartidor creado en Factory: " + repartidorCreado.getNombre());

        // PASO 3: CREAR CREDENCIAL DE AUTENTICACIÓN
        factory.crearCredencialUsuario(correo, password);
        System.out.println(" Credencial creada para: " + correo);

        // PASO 4: TAMBIÉN REGISTRAR EN SERVICIO DE AUTENTICACIÓN
        boolean credencialRegistrada = loginController.registrarse(
                correo,
                password,
                TipoPerfil.REPARTIDOR,
                nombre
        );

        if (credencialRegistrada) {
            System.out.println(" Registro completo exitoso");
            mostrarExito("¡Registro exitoso! Redirigiendo al login...");
            esperarYVolverAlLogin();
        } else {
            mostrarError("Error al crear credenciales de acceso");
        }
    }

    /**
     * Esperar 2 segundos y volver al login
     */
    private void esperarYVolverAlLogin() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                javafx.application.Platform.runLater(this::volverAlLogin);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     *  CANCELAR Y VOLVER AL LOGIN
     */
    @FXML
    private void handleCancelar() {
        System.out.println("🔵 Cancelando registro");
        volverAlLogin();
    }

    /**
     * Volver a la pantalla de Login
     */
    private void volverAlLogin() {
        try {
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Login.fxml")
            );

            Scene scene = new Scene(loader.load(), 400, 600);
            stage.setTitle("Logística Express - Login");
            stage.setScene(scene);
            stage.show();

            System.out.println(" Volviendo al login");

        } catch (Exception e) {
            System.err.println(" Error al volver al login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========== VALIDACIONES ==========

    private boolean esEmailValido(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean esTelefonoValido(String telefono) {
        String telefonoLimpio = telefono.replaceAll("[\\s-]", "");
        return telefonoLimpio.matches("^\\d{10}$");
    }

    // ========== MENSAJES ==========

    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 11px; -fx-font-weight: bold;");
        System.out.println("⚠️ " + mensaje);
    }

    private void mostrarExito(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #059669; -fx-font-size: 11px; -fx-font-weight: bold;");
        System.out.println("✅ " + mensaje);
    }
}