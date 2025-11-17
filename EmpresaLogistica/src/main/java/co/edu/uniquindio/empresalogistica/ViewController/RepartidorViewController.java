package co.edu.uniquindio.empresalogistica.ViewController;

import co.edu.uniquindio.empresalogistica.Controller.RepartidorController;
import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.ReportarIncidenciaDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class RepartidorViewController {

    // ========== COMPONENTES MI PERFIL ==========
    @FXML private Label lblBienvenida;
    @FXML private Label lblNombreRepartidor;
    @FXML private Label lblCorreo;
    @FXML private Label lblTelefono;
    @FXML private Label lblDocumento;
    @FXML private Label lblFechaRegistro;
    @FXML private Label lblEstado;
    @FXML private TextField txtZonaCobertura;
    @FXML private ComboBox<String> cmbDisponibilidad;
    @FXML private Button btnActualizarPerfil;

    // ========== COMPONENTES MIS ENVÍOS ==========
    @FXML private TableView<EnvioRepartidorTabla> tblEnviosAsignados;
    @FXML private TableColumn<EnvioRepartidorTabla, String> colIdEnvio;
    @FXML private TableColumn<EnvioRepartidorTabla, String> colOrigen;
    @FXML private TableColumn<EnvioRepartidorTabla, String> colDestino;
    @FXML private TableColumn<EnvioRepartidorTabla, String> colEstado;
    @FXML private TableColumn<EnvioRepartidorTabla, String> colFechaCreacion;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private Button btnActualizarLista;
    @FXML private Label lblTotalEnvios;

    // ========== COMPONENTES ACTUALIZAR ESTADO ==========
    @FXML private TextField txtIdEnvioCambio;
    @FXML private ComboBox<String> cmbNuevoEstado;
    @FXML private TextArea txtNotasCambio;
    @FXML private Button btnCambiarEstado;
    @FXML private Button btnReportarIncidencia;
    @FXML private Label lblInfoEnvioSeleccionado;

    // ========== COMPONENTES MI DESEMPEÑO ==========
    @FXML private Label lblEnviosHoy;
    @FXML private Label lblEnviosMes;
    @FXML private Label lblEnviosTotal;
    @FXML private Label lblCalificacion;
    @FXML private Label lblTiempoPromedio;
    @FXML private Label lblIncidencias;
    @FXML private ProgressBar progressBarMes;
    @FXML private Label lblMetaMes;

    // ========== COMPONENTES MI ZONA ==========
    @FXML private TextArea txtMapaZona;
    @FXML private Label lblEstadoDisponibilidad;
    @FXML private Button btnCambiarDisponibilidad;
    @FXML private ListView<String> listDireccionesFrecuentes;

    private RepartidorController repartidorController;
    private EmpresaLogisticaFactory factory;
    private RepartidorDTO repartidorActual;
    private ObservableList<EnvioRepartidorTabla> listaEnvios;

    @FXML
    public void initialize() {
        repartidorController = new RepartidorController();
        factory = EmpresaLogisticaFactory.getInstance();
        repartidorActual = factory.getRepartidorActual();

        if (repartidorActual != null) {
            inicializarComponentes();
            cargarDatosRepartidor();
            cargarEnviosAsignados();
            cargarEstadisticas();
        } else {
            mostrarError("No hay repartidor logueado");
        }
    }

    private void inicializarComponentes() {
        // Combo disponibilidad
        if (cmbDisponibilidad != null) {
            cmbDisponibilidad.getItems().addAll("ACTIVO", "INACTIVO", "EN_RUTA");
            cmbDisponibilidad.setValue(repartidorActual.getDisponibilidad());
        }

        // Combo filtro estado
        if (cmbFiltroEstado != null) {
            cmbFiltroEstado.getItems().addAll("TODOS", "CONFIRMADO", "RECOGIDO",
                    "EN_RUTA", "EN_REPARTO", "ENTREGADO");
            cmbFiltroEstado.setValue("TODOS");
        }

        // Combo nuevo estado
        if (cmbNuevoEstado != null) {
            cmbNuevoEstado.getItems().addAll("CONFIRMADO", "RECOGIDO", "EN_RUTA",
                    "EN_REPARTO", "ENTREGADO", "CANCELADO");
        }

        // Configurar tabla
        if (tblEnviosAsignados != null) {
            configurarTablaEnvios();
        }

        // Lista de direcciones frecuentes
        if (listDireccionesFrecuentes != null) {
            cargarDireccionesFrecuentes();
        }
    }

    // ========== FUNCIONES CONFIGURACIÓN ==========

    @FXML
    public void handleActualizarPerfil() {
        try {
            repartidorActual.setZonaCobertura(txtZonaCobertura.getText());

            String nuevaDisponibilidad = cmbDisponibilidad.getValue();
            repartidorActual.setDisponibilidad(nuevaDisponibilidad);

            if (repartidorController.actualizarRepartidor(repartidorActual)) {
                mostrarExito("Perfil actualizado exitosamente");
                actualizarEstadoDisponibilidad();
            } else {
                mostrarError("Error al actualizar perfil");
            }
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleCambiarPassword() {
        String passwordActual = txtPasswordActual.getText().trim();
        String passwordNueva = txtPasswordNueva.getText().trim();
        String passwordConfirmar = txtPasswordConfirmar.getText().trim();

        // Validaciones
        if (passwordActual.isEmpty() || passwordNueva.isEmpty() || passwordConfirmar.isEmpty()) {
            mostrarError("Complete todos los campos de contraseña");
            return;
        }

        if (passwordNueva.length() < 6) {
            mostrarError("La nueva contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!passwordNueva.equals(passwordConfirmar)) {
            mostrarError("Las contraseñas nuevas no coinciden");
            return;
        }

        if (passwordActual.equals(passwordNueva)) {
            mostrarError("La nueva contraseña debe ser diferente a la actual");
            return;
        }

        try {
            boolean exito = factory.cambiarContrasenaRepartidor(
                    repartidorActual.getCorreo(),
                    passwordActual,
                    passwordNueva
            );

            if (exito) {
                mostrarExito("Contraseña actualizada exitosamente");
                txtPasswordActual.clear();
                txtPasswordNueva.clear();
                txtPasswordConfirmar.clear();
            } else {
                mostrarError("La contraseña actual es incorrecta");
            }
        } catch (Exception e) {
            mostrarError("Error al cambiar contraseña: " + e.getMessage());
        }
    }

    // Declarar estos componentes al inicio si no están:
    @FXML private PasswordField txtPasswordActual;
    @FXML private PasswordField txtPasswordNueva;
    @FXML private PasswordField txtPasswordConfirmar;
    @FXML private Button btnCambiarPassword;

    // ========== FUNCIONES MI PERFIL ========== (resto del código existente)

    private void cargarDatosRepartidor() {
        lblBienvenida.setText("Bienvenido, " + repartidorActual.getNombre() + "!");

        if (lblNombreRepartidor != null) {
            lblNombreRepartidor.setText(repartidorActual.getNombre());
        }

        lblCorreo.setText("Correo: " + repartidorActual.getCorreo());
        lblTelefono.setText("Telefono: " + repartidorActual.getTelefono());

        if (lblDocumento != null) {
            lblDocumento.setText("Documento: " + repartidorActual.getDocumento());
        }

        lblFechaRegistro.setText("Fecha Registro: " + repartidorActual.getFechaRegistro());
        lblEstado.setText("Estado: " + repartidorActual.getEstado());

        if (txtZonaCobertura != null) {
            txtZonaCobertura.setText(repartidorActual.getZonaCobertura());
        }

        if (lblEstadoDisponibilidad != null) {
            actualizarEstadoDisponibilidad();
        }
    }


    // ========== FUNCIONES MIS ENVÍOS ==========

    private void configurarTablaEnvios() {
        colIdEnvio.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        listaEnvios = FXCollections.observableArrayList();
        tblEnviosAsignados.setItems(listaEnvios);

        // Evento de selección
        tblEnviosAsignados.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        mostrarDetalleEnvio(newSelection);
                    }
                });
    }

    private void cargarEnviosAsignados() {
        listaEnvios.clear();
        List<Envio> envios = repartidorController.obtenerEnviosAsignados(repartidorActual.getId());

        for (Envio envio : envios) {
            listaEnvios.add(new EnvioRepartidorTabla(
                    envio.getIdEnvio().substring(0, 8),
                    envio.getOrigen().getCalle(),
                    envio.getDestino().getCalle(),
                    envio.getEstadoEnvio().getDescripcion(),
                    envio.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            ));
        }

        if (lblTotalEnvios != null) {
            lblTotalEnvios.setText("Total: " + envios.size() + " envios");
        }
    }

    @FXML
    public void handleActualizarLista() {
        String filtro = cmbFiltroEstado.getValue();

        if ("TODOS".equals(filtro)) {
            cargarEnviosAsignados();
        } else {
            filtrarEnviosPorEstado(filtro);
        }
    }

    private void filtrarEnviosPorEstado(String estadoStr) {
        listaEnvios.clear();
        List<Envio> envios = repartidorController.obtenerEnviosAsignados(repartidorActual.getId());

        EstadoEnvio estadoFiltro = EstadoEnvio.valueOf(estadoStr);

        for (Envio envio : envios) {
            if (envio.getEstadoEnvio() == estadoFiltro) {
                listaEnvios.add(new EnvioRepartidorTabla(
                        envio.getIdEnvio().substring(0, 8),
                        envio.getOrigen().getCalle(),
                        envio.getDestino().getCalle(),
                        envio.getEstadoEnvio().getDescripcion(),
                        envio.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                ));
            }
        }
    }

    // ========== FUNCIONES ACTUALIZAR ESTADO ==========

    private void mostrarDetalleEnvio(EnvioRepartidorTabla envioTabla) {
        if (txtIdEnvioCambio != null) {
            txtIdEnvioCambio.setText(envioTabla.getId());
        }

        if (lblInfoEnvioSeleccionado != null) {
            String info = String.format("Origen: %s\nDestino: %s\nEstado: %s",
                    envioTabla.getOrigen(), envioTabla.getDestino(), envioTabla.getEstado());
            lblInfoEnvioSeleccionado.setText(info);
        }
    }

    @FXML
    public void handleCambiarEstado() {
        String idEnvio = txtIdEnvioCambio.getText().trim();
        String nuevoEstadoStr = cmbNuevoEstado.getValue();

        if (idEnvio.isEmpty() || nuevoEstadoStr == null) {
            mostrarError("Complete todos los campos");
            return;
        }

        try {
            // Buscar ID completo
            List<Envio> envios = repartidorController.obtenerEnviosAsignados(repartidorActual.getId());
            String idCompleto = envios.stream()
                    .filter(e -> e.getIdEnvio().startsWith(idEnvio))
                    .map(Envio::getIdEnvio)
                    .findFirst()
                    .orElse(null);

            if (idCompleto == null) {
                mostrarError("Envio no encontrado");
                return;
            }

            EstadoEnvio nuevoEstado = EstadoEnvio.valueOf(nuevoEstadoStr);

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Cambio");
            confirmacion.setHeaderText("Cambiar estado del envio?");
            confirmacion.setContentText("Nuevo estado: " + nuevoEstado.getDescripcion());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (repartidorController.actualizarEstadoEnvio(idCompleto, nuevoEstado)) {
                    mostrarExito("Estado actualizado exitosamente");
                    cargarEnviosAsignados();
                    cargarEstadisticas();
                    limpiarCambioEstado();
                } else {
                    mostrarError("Error al actualizar estado");
                }
            }

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleReportarIncidencia() {
        String idEnvioSeleccionado = txtIdEnvioCambio.getText().trim();

        ReportarIncidenciaDialog dialog = new ReportarIncidenciaDialog(idEnvioSeleccionado);

        Optional<Boolean> resultado = dialog.showAndWait();

        if (resultado.isPresent() && resultado.get()) {
            try {
                repartidorActual = factory.obtenerRepartidorPorId(repartidorActual.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            cargarEnviosAsignados();
            cargarEstadisticas(); // ← Esto ahora mostrará las incidencias actualizadas
            limpiarCambioEstado();

            mostrarExito("✅ Incidencia reportada exitosamente");
        }
    }

    private void limpiarCambioEstado() {
        txtIdEnvioCambio.clear();
        cmbNuevoEstado.setValue(null);
        txtNotasCambio.clear();
        if (lblInfoEnvioSeleccionado != null) {
            lblInfoEnvioSeleccionado.setText("Seleccione un envio de la tabla");
        }
    }

    // ========== FUNCIONES MI DESEMPEÑO ==========

    private void cargarEstadisticas() {
        String idRepartidor = repartidorActual.getId();

        // Envíos hoy
        int enviosHoy = repartidorController.contarEnviosCompletadosHoy(idRepartidor);
        if (lblEnviosHoy != null) {
            lblEnviosHoy.setText(String.valueOf(enviosHoy));
        }

        // Envíos del mes
        int enviosMes = repartidorController.contarEnviosCompletadosMes(idRepartidor);
        if (lblEnviosMes != null) {
            lblEnviosMes.setText(String.valueOf(enviosMes));
        }

        // Total envíos
        if (lblEnviosTotal != null) {
            lblEnviosTotal.setText(repartidorActual.getEnviosRealizados());
        }

        // Calificación
        if (lblCalificacion != null) {
            double calificacion = repartidorActual.getCalificacionPromedio();
            lblCalificacion.setText(String.format("%.1f / 5.0", calificacion));
        }

        // Tiempo promedio
        if (lblTiempoPromedio != null) {
            double tiempoPromedio = repartidorController.calcularPromedioTiempoEntrega(idRepartidor);
            lblTiempoPromedio.setText(String.format("%.0f min", tiempoPromedio));
        }

        // Incidencias
        if (lblIncidencias != null) {
            int incidencias = repartidorController.contarIncidenciasRepartidor(idRepartidor);
            lblIncidencias.setText(String.valueOf(incidencias));
        }

        // Progress bar meta del mes (meta: 100 envíos)
        if (progressBarMes != null) {
            double progreso = Math.min(enviosMes / 100.0, 1.0);
            progressBarMes.setProgress(progreso);

            if (lblMetaMes != null) {
                lblMetaMes.setText(enviosMes + " / 100 envios");
            }
        }
    }

    // ========== FUNCIONES MI ZONA ==========

    private void actualizarEstadoDisponibilidad() {
        String disponibilidad = repartidorActual.getDisponibilidad();
        String texto = "";
        String estilo = "";

        switch (disponibilidad) {
            case "ACTIVO":
                texto = "DISPONIBLE PARA ENVIOS";
                estilo = "-fx-text-fill: #059669; -fx-font-weight: bold; -fx-font-size: 16px;";
                break;
            case "EN_RUTA":
                texto = "EN RUTA - OCUPADO";
                estilo = "-fx-text-fill: #f59e0b; -fx-font-weight: bold; -fx-font-size: 16px;";
                break;
            case "INACTIVO":
                texto = "NO DISPONIBLE";
                estilo = "-fx-text-fill: #dc2626; -fx-font-weight: bold; -fx-font-size: 16px;";
                break;
        }

        if (lblEstadoDisponibilidad != null) {
            lblEstadoDisponibilidad.setText(texto);
            lblEstadoDisponibilidad.setStyle(estilo);
        }
    }

    @FXML
    public void handleCambiarDisponibilidad() {
        String disponibilidadActual = repartidorActual.getDisponibilidad();
        String nuevaDisponibilidad = "";

        // Ciclo: INACTIVO -> ACTIVO -> EN_RUTA -> INACTIVO
        switch (disponibilidadActual) {
            case "INACTIVO":
                nuevaDisponibilidad = "ACTIVO";
                break;
            case "ACTIVO":
                nuevaDisponibilidad = "EN_RUTA";
                break;
            case "EN_RUTA":
                nuevaDisponibilidad = "INACTIVO";
                break;
        }

        repartidorActual.setDisponibilidad(nuevaDisponibilidad);

        if (repartidorController.actualizarRepartidor(repartidorActual)) {
            actualizarEstadoDisponibilidad();
            if (cmbDisponibilidad != null) {
                cmbDisponibilidad.setValue(nuevaDisponibilidad);
            }
            mostrarExito("Disponibilidad actualizada a: " + nuevaDisponibilidad);
        }
    }

    private void cargarDireccionesFrecuentes() {
        // Simulación de direcciones frecuentes en la zona
        ObservableList<String> direcciones = FXCollections.observableArrayList(
                "Calle 10 #20-30, Centro",
                "Carrera 5 #100-10, Norte",
                "Avenida 15 #50-25, Sur",
                "Diagonal 20 #5-50, Occidente",
                "Transversal 8 #15-20, Oriente"
        );
        listDireccionesFrecuentes.setItems(direcciones);
    }

    @FXML
    public void handleVerMapaZona() {
        if (txtMapaZona != null) {
            String mapa = generarMapaSimulado();
            txtMapaZona.setText(mapa);
        }
    }

    private String generarMapaSimulado() {
        String zona = repartidorActual.getZonaCobertura();
        return String.format(
                "╔════════════════════════════════════════╗\n" +
                        "║       MAPA DE ZONA DE COBERTURA       ║\n" +
                        "╠════════════════════════════════════════╣\n" +
                        "║                                        ║\n" +
                        "║    Zona: %-29s ║\n" +
                        "║                                        ║\n" +
                        "║    [Norte]                             ║\n" +
                        "║       |                                ║\n" +
                        "║  [Oeste] - [USTED] - [Este]           ║\n" +
                        "║       |                                ║\n" +
                        "║    [Sur]                               ║\n" +
                        "║                                        ║\n" +
                        "║  Radio de cobertura: ~10 km            ║\n" +
                        "║  Puntos clave: 5 direcciones           ║\n" +
                        "║                                        ║\n" +
                        "╚════════════════════════════════════════╝\n",
                zona
        );
    }

    // ========== CERRAR SESIÓN ==========

    @FXML
    public void handleCerrarSesion() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar sesion");
        confirmacion.setHeaderText("Deseas cerrar sesion?");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            repartidorController.cerrarSesion();

            try {
                Stage stage = (Stage) lblBienvenida.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Login.fxml"));
                Scene scene = new Scene(loader.load(), 400, 600);
                stage.setTitle("Logistica Express - Login");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (Exception e) {
                mostrarError("Error al cerrar sesion: " + e.getMessage());
            }
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ========== CLASE INTERNA PARA TABLA ==========

    public static class EnvioRepartidorTabla {
        private String id;
        private String origen;
        private String destino;
        private String estado;
        private String fecha;

        public EnvioRepartidorTabla(String id, String origen, String destino, String estado, String fecha) {
            this.id = id;
            this.origen = origen;
            this.destino = destino;
            this.estado = estado;
            this.fecha = fecha;
        }

        public String getId() { return id; }
        public String getOrigen() { return origen; }
        public String getDestino() { return destino; }
        public String getEstado() { return estado; }
        public String getFecha() { return fecha; }
    }
}