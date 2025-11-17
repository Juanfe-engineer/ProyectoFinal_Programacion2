package co.edu.uniquindio.empresalogistica.ViewController;

import co.edu.uniquindio.empresalogistica.Controller.AdminController;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Enums.DisponibilidadRepartidor;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoUsuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 🎯 CONTROLADOR DE VISTA DEL ADMINISTRADOR
 * El dashboard más completo del sistema
 */
public class AdminViewController {

    // ==========  COMPONENTES DEL DASHBOARD PRINCIPAL ==========
    @FXML private Label lblTotalUsuarios;
    @FXML private Label lblTotalRepartidores;
    @FXML private Label lblTotalEnvios;
    @FXML private Label lblIngresosTotales;
    @FXML private Label lblEnviosHoy;
    @FXML private Label lblEnviosMes;
    @FXML private Label lblTasaExito;
    @FXML private Label lblTiempoPromedio;
    @FXML private Label lblIncidenciasTotales;
    @FXML private Label lblEnviosPendientes;

    // ==========  GRÁFICOS ==========
    @FXML private LineChart<String, Number> chartEnviosSemana;
    @FXML private BarChart<String, Number> chartTopRepartidores;
    @FXML private PieChart chartEstadosEnvio;
    @FXML private AreaChart<String, Number> chartIngresosMes;

    // ==========  GESTIÓN DE USUARIOS ==========
    @FXML private TableView<UsuarioDTO> tblUsuarios;
    @FXML private TableColumn<UsuarioDTO, String> colUsuarioId;
    @FXML private TableColumn<UsuarioDTO, String> colUsuarioNombre;
    @FXML private TableColumn<UsuarioDTO, String> colUsuarioCorreo;
    @FXML private TableColumn<UsuarioDTO, String> colUsuarioTelefono;
    @FXML private TableColumn<UsuarioDTO, String> colUsuarioEstado;
    @FXML private TableColumn<UsuarioDTO, String> colUsuarioEnvios;
    @FXML private TextField txtBuscarUsuario;
    @FXML private Button btnCrearUsuario;
    @FXML private Button btnEditarUsuario;
    @FXML private Button btnEliminarUsuario;
    @FXML private Button btnSuspenderUsuario;

    // ==========  GESTIÓN DE REPARTIDORES ==========
    @FXML private TableView<RepartidorDTO> tblRepartidores;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorId;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorNombre;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorZona;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorDisponibilidad;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorEnvios;
    @FXML private TableColumn<RepartidorDTO, Double> colRepartidorCalificacion;
    @FXML private TextField txtBuscarRepartidor;
    @FXML private Button btnCrearRepartidor;
    @FXML private Button btnEditarRepartidor;
    @FXML private Button btnCambiarDisponibilidad;

    // ==========  GESTIÓN DE ENVÍOS ==========
    @FXML private TableView<EnvioAdmin> tblEnvios;
    @FXML private TableColumn<EnvioAdmin, String> colEnvioId;
    @FXML private TableColumn<EnvioAdmin, String> colEnvioOrigen;
    @FXML private TableColumn<EnvioAdmin, String> colEnvioDestino;
    @FXML private TableColumn<EnvioAdmin, String> colEnvioEstado;
    @FXML private TableColumn<EnvioAdmin, String> colEnvioFecha;
    @FXML private TableColumn<EnvioAdmin, Double> colEnvioCosto;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private Button btnAsignarRepartidor;
    @FXML private Button btnVerDetalles;

    // ========== 🎛 CONTROLADOR Y DATOS ==========
    private AdminController adminController;
    private ObservableList<UsuarioDTO> listaUsuarios;
    private ObservableList<RepartidorDTO> listaRepartidores;
    private ObservableList<EnvioAdmin> listaEnvios;

    /**
     * 🚀 INICIALIZACIÓN
     */
    @FXML
    public void initialize() {
        System.out.println(" Inicializando AdminViewController...");

        adminController = new AdminController();

        // Inicializar listas observables
        listaUsuarios = FXCollections.observableArrayList();
        listaRepartidores = FXCollections.observableArrayList();
        listaEnvios = FXCollections.observableArrayList();

        // Configurar componentes
        configurarTablas();
        configurarFiltros();

        // Cargar datos
        cargarDashboard();
        cargarUsuarios();
        cargarRepartidores();
        cargarEnvios();

        // Configurar gráficos
        configurarGraficos();
        cargarGraficos();

        System.out.println(" AdminViewController inicializado");
    }

    // ========== 📋 CONFIGURACIÓN DE TABLAS ==========

    /**
     * Configurar todas las tablas del sistema
     */
    private void configurarTablas() {
        configurarTablaUsuarios();
        configurarTablaRepartidores();
        configurarTablaEnvios();
    }

    /**
     * Configurar tabla de usuarios
     */
    private void configurarTablaUsuarios() {
        colUsuarioId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsuarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsuarioCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colUsuarioTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colUsuarioEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colUsuarioEnvios.setCellValueFactory(new PropertyValueFactory<>("enviosRealizados"));

        // Personalizar celda de estado con colores
        colUsuarioEstado.setCellFactory(column -> new TableCell<UsuarioDTO, String>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    switch (estado) {
                        case "ACTIVO":
                            setStyle("-fx-text-fill: #059669; -fx-font-weight: bold;");
                            break;
                        case "SUSPENDIDO":
                            setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("-fx-text-fill: #64748b;");
                    }
                }
            }
        });

        tblUsuarios.setItems(listaUsuarios);
    }

    /**
     * Configurar tabla de repartidores
     */
    private void configurarTablaRepartidores() {
        colRepartidorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRepartidorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRepartidorZona.setCellValueFactory(new PropertyValueFactory<>("zonaCobertura"));
        colRepartidorDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));
        colRepartidorEnvios.setCellValueFactory(new PropertyValueFactory<>("enviosRealizados"));
        colRepartidorCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacionPromedio"));

        // Personalizar celda de disponibilidad
        colRepartidorDisponibilidad.setCellFactory(column -> new TableCell<RepartidorDTO, String>() {
            @Override
            protected void updateItem(String disponibilidad, boolean empty) {
                super.updateItem(disponibilidad, empty);
                if (empty || disponibilidad == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(disponibilidad);
                    switch (disponibilidad) {
                        case "ACTIVO":
                            setStyle("-fx-text-fill: #059669; -fx-font-weight: bold;");
                            break;
                        case "EN_RUTA":
                            setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
                            break;
                        case "INACTIVO":
                            setStyle("-fx-text-fill: #64748b;");
                            break;
                    }
                }
            }
        });

        // Personalizar celda de calificación con estrellas
        colRepartidorCalificacion.setCellFactory(column -> new TableCell<RepartidorDTO, Double>() {
            @Override
            protected void updateItem(Double calificacion, boolean empty) {
                super.updateItem(calificacion, empty);
                if (empty || calificacion == null) {
                    setText(null);
                } else {
                    int estrellas = (int) Math.round(calificacion);
                    String textoEstrellas = "⭐".repeat(estrellas) + "☆".repeat(5 - estrellas);
                    setText(String.format("%.1f %s", calificacion, textoEstrellas));
                }
            }
        });

        tblRepartidores.setItems(listaRepartidores);
    }

    /**
     * Configurar tabla de envíos
     */
    private void configurarTablaEnvios() {
        colEnvioId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEnvioOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colEnvioDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colEnvioEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEnvioFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEnvioCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        // Personalizar celda de estado
        colEnvioEstado.setCellFactory(column -> new TableCell<EnvioAdmin, String>() {
            @Override
            protected void updateItem(String estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    // Asignar colores según estado
                    if (estado.contains("ENTREGADO")) {
                        setStyle("-fx-text-fill: #059669; -fx-font-weight: bold;");
                    } else if (estado.contains("EN_RUTA") || estado.contains("REPARTO")) {
                        setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
                    } else if (estado.contains("CANCELADO")) {
                        setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #2563eb; -fx-font-weight: bold;");
                    }
                }
            }
        });

        tblEnvios.setItems(listaEnvios);
    }

    /**
     * Configurar filtros y combos
     */
    private void configurarFiltros() {
        if (cmbFiltroEstado != null) {
            cmbFiltroEstado.getItems().addAll(
                    "TODOS",
                    "SOLICITADO",
                    "CONFIRMADO",
                    "EN_RUTA",
                    "ENTREGADO",
                    "CANCELADO"
            );
            cmbFiltroEstado.setValue("TODOS");
        }
    }

// ==========  CARGAR DASHBOARD ==========

    /**
     * Cargar todas las métricas del dashboard
     */
    private void cargarDashboard() {
        // Totales
        lblTotalUsuarios.setText(String.valueOf(adminController.obtenerTodosLosUsuarios().size()));
        lblTotalRepartidores.setText(String.valueOf(adminController.obtenerTodosLosRepartidores().size()));
        lblTotalEnvios.setText(String.valueOf(adminController.obtenerTodosLosEnvios().size()));

        // Ingresos
        double ingresos = adminController.calcularIngresosTotales();
        lblIngresosTotales.setText(String.format("$%.2f", ingresos));

        // Envíos del día y mes
        int enviosHoy = (int) adminController.obtenerTodosLosEnvios().stream()
                .filter(e -> e.getFechaCreacion().toLocalDate().equals(LocalDate.now()))
                .count();
        lblEnviosHoy.setText(String.valueOf(enviosHoy));

        int enviosMes = adminController.obtenerEnviosDelMes().size();
        lblEnviosMes.setText(String.valueOf(enviosMes));

        // Tasa de éxito
        double tasaExito = adminController.calcularTasaExito();
        lblTasaExito.setText(String.format("%.1f%%", tasaExito));

        // Tiempo promedio
        double tiempoPromedio = adminController.calcularTiempoPromedioEntrega();
        lblTiempoPromedio.setText(String.format("%.0f min", tiempoPromedio));

        // Incidencias
        int incidencias = adminController.contarIncidenciasTotales();
        lblIncidenciasTotales.setText(String.valueOf(incidencias));

        // Envíos pendientes
        int pendientes = adminController.obtenerEnviosSinAsignar().size();
        lblEnviosPendientes.setText(String.valueOf(pendientes));
    }

// ==========  CONFIGURAR Y CARGAR GRÁFICOS ==========

    /**
     * Configurar estilos de los gráficos
     */
    private void configurarGraficos() {
        // Configurar LineChart
        if (chartEnviosSemana != null) {
            chartEnviosSemana.setTitle("Envíos de la Última Semana");
            chartEnviosSemana.setLegendVisible(false);
            chartEnviosSemana.setAnimated(true);
        }

        // Configurar BarChart
        if (chartTopRepartidores != null) {
            chartTopRepartidores.setTitle("Top 5 Repartidores");
            chartTopRepartidores.setLegendVisible(false);
            chartTopRepartidores.setAnimated(true);
        }

        // Configurar PieChart
        if (chartEstadosEnvio != null) {
            chartEstadosEnvio.setTitle("Distribución por Estado");
            chartEstadosEnvio.setLegendSide(Side.RIGHT);
            chartEstadosEnvio.setAnimated(true);
        }

        // Configurar AreaChart
        if (chartIngresosMes != null) {
            chartIngresosMes.setTitle("Ingresos del Mes");
            chartIngresosMes.setLegendVisible(false);
            chartIngresosMes.setAnimated(true);
        }
    }

    /**
     * Cargar datos en todos los gráficos
     */
    private void cargarGraficos() {
        cargarGraficoEnviosSemana();
        cargarGraficoTopRepartidores();
        cargarGraficoEstadosEnvio();
        cargarGraficoIngresosMes();
    }

    /**
     *  Gráfico de Líneas: Envíos de la última semana
     */
    private void cargarGraficoEnviosSemana() {
        if (chartEnviosSemana == null) return;

        chartEnviosSemana.getData().clear();

        Map<LocalDate, Long> enviosPorDia = adminController.obtenerEnviosPorDia();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Envíos");

        // Últimos 7 días
        for (int i = 6; i >= 0; i--) {
            LocalDate fecha = LocalDate.now().minusDays(i);
            long cantidad = enviosPorDia.getOrDefault(fecha, 0L);
            String dia = fecha.format(DateTimeFormatter.ofPattern("dd/MM"));
            series.getData().add(new XYChart.Data<>(dia, cantidad));
        }

        chartEnviosSemana.getData().add(series);
    }

    /**
     *  Gráfico de Barras: Top 5 Repartidores
     */
    private void cargarGraficoTopRepartidores() {
        if (chartTopRepartidores == null) return;

        chartTopRepartidores.getData().clear();

        List<Map.Entry<String, Integer>> topRepartidores = adminController.obtenerTopRepartidores();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Entregas");

        for (Map.Entry<String, Integer> entry : topRepartidores) {
            String nombre = entry.getKey().length() > 15 ?
                    entry.getKey().substring(0, 12) + "..." : entry.getKey();
            series.getData().add(new XYChart.Data<>(nombre, entry.getValue()));
        }

        chartTopRepartidores.getData().add(series);
    }

    /**
     *  Gráfico Circular: Distribución por Estado
     */
    private void cargarGraficoEstadosEnvio() {
        if (chartEstadosEnvio == null) return;

        chartEstadosEnvio.getData().clear();

        Map<EstadoEnvio, Long> estadosCount = adminController.contarEnviosPorEstado();

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (Map.Entry<EstadoEnvio, Long> entry : estadosCount.entrySet()) {
            String label = entry.getKey().name() + " (" + entry.getValue() + ")";
            pieData.add(new PieChart.Data(label, entry.getValue()));
        }

        chartEstadosEnvio.setData(pieData);
    }

    /**
     *  Gráfico de Área: Ingresos del mes
     */
    private void cargarGraficoIngresosMes() {
        if (chartIngresosMes == null) return;

        chartIngresosMes.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos");

        // Ingresos por semana del mes actual
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate hoy = LocalDate.now();

        for (int semana = 1; semana <= 4; semana++) {
            LocalDate inicioSemana = inicioMes.plusWeeks(semana - 1);
            LocalDate finSemana = inicioSemana.plusDays(6);

            if (finSemana.isAfter(hoy)) finSemana = hoy;

            double ingresos = adminController.calcularIngresosPorPeriodo(inicioSemana, finSemana);
            series.getData().add(new XYChart.Data<>("Sem " + semana, ingresos));
        }

        chartIngresosMes.getData().add(series);
    }

// ==========  CARGAR DATOS EN TABLAS ==========

    /**
     * Cargar usuarios en la tabla
     */
    private void cargarUsuarios() {
        listaUsuarios.clear();
        List<UsuarioDTO> usuarios = adminController.obtenerTodosLosUsuarios();
        listaUsuarios.addAll(usuarios);
        System.out.println(" Cargados " + usuarios.size() + " usuarios");
    }

    /**
     * Cargar repartidores en la tabla
     */
    private void cargarRepartidores() {
        listaRepartidores.clear();
        List<RepartidorDTO> repartidores = adminController.obtenerTodosLosRepartidores();
        listaRepartidores.addAll(repartidores);
        System.out.println(" Cargados " + repartidores.size() + " repartidores");
    }

    /**
     * Cargar envíos en la tabla
     */
    private void cargarEnvios() {
        listaEnvios.clear();
        List<Envio> envios = adminController.obtenerTodosLosEnvios();

        for (Envio envio : envios) {
            listaEnvios.add(new EnvioAdmin(
                    envio.getIdEnvio().substring(0, 8),
                    envio.getOrigen().getCalle(),
                    envio.getDestino().getCalle(),
                    envio.getEstadoEnvio().getDescripcion(),
                    envio.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    envio.getCostoTotal()
            ));
        }

        System.out.println(" Cargados " + envios.size() + " envíos");
    }

// ========== 🔍 BÚSQUEDAS ==========

    /**
     * Buscar usuarios
     */
    @FXML
    private void handleBuscarUsuario() {
        String criterio = txtBuscarUsuario.getText().trim();

        if (criterio.isEmpty()) {
            cargarUsuarios();
            return;
        }

        listaUsuarios.clear();
        List<UsuarioDTO> resultados = adminController.buscarUsuarios(criterio);
        listaUsuarios.addAll(resultados);

        mostrarInfo("Se encontraron " + resultados.size() + " usuarios");
    }

    /**
     * Buscar repartidores
     */
    @FXML
    private void handleBuscarRepartidor() {
        String criterio = txtBuscarRepartidor.getText().trim();

        if (criterio.isEmpty()) {
            cargarRepartidores();
            return;
        }

        listaRepartidores.clear();
        List<RepartidorDTO> resultados = adminController.buscarRepartidores(criterio);
        listaRepartidores.addAll(resultados);

        mostrarInfo("Se encontraron " + resultados.size() + " repartidores");
    }

// ========== 🔄 ACTUALIZAR DATOS ==========

    /**
     * Refrescar todo el dashboard
     */
    @FXML
    private void handleRefrescarDashboard() {
        cargarDashboard();
        cargarUsuarios();
        cargarRepartidores();
        cargarEnvios();
        cargarGraficos();
        mostrarExito("✅ Dashboard actualizado");
    }


    // ========== 👥 OPERACIONES CON USUARIOS ==========

    /**
     * Crear nuevo usuario
     */
    @FXML
    private void handleCrearUsuario() {
        try {
            // Crear dialog personalizado
            Dialog<UsuarioDTO> dialog = new Dialog<>();
            dialog.setTitle("Crear Nuevo Usuario");
            dialog.setHeaderText("Complete los datos del nuevo usuario");

            // Botones
            ButtonType btnCrear = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnCrear, ButtonType.CANCEL);

            // Crear formulario
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            TextField txtNombre = new TextField();
            txtNombre.setPromptText("Nombre completo");
            TextField txtCorreo = new TextField();
            txtCorreo.setPromptText("correo@ejemplo.com");
            TextField txtTelefono = new TextField();
            txtTelefono.setPromptText("3001234567");
            TextField txtDireccion = new TextField();
            txtDireccion.setPromptText("Calle 123 #45-67");
            PasswordField txtPassword = new PasswordField();
            txtPassword.setPromptText("Contraseña (mín 6 caracteres)");

            grid.add(new Label("Nombre:"), 0, 0);
            grid.add(txtNombre, 1, 0);
            grid.add(new Label("Correo:"), 0, 1);
            grid.add(txtCorreo, 1, 1);
            grid.add(new Label("Teléfono:"), 0, 2);
            grid.add(txtTelefono, 1, 2);
            grid.add(new Label("Dirección:"), 0, 3);
            grid.add(txtDireccion, 1, 3);
            grid.add(new Label("Contraseña:"), 0, 4);
            grid.add(txtPassword, 1, 4);

            dialog.getDialogPane().setContent(grid);

            // Convertir resultado
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnCrear) {
                    UsuarioDTO nuevoUsuario = new UsuarioDTO();
                    nuevoUsuario.setNombre(txtNombre.getText());
                    nuevoUsuario.setCorreo(txtCorreo.getText());
                    nuevoUsuario.setTelefono(txtTelefono.getText());
                    nuevoUsuario.setDireccionFrecuente(txtDireccion.getText());
                    nuevoUsuario.setFechaRegistro(LocalDate.now());
                    nuevoUsuario.setEstado(EstadoUsuario.ACTIVO.toString());
                    nuevoUsuario.setEnviosRealizados("0");
                    return nuevoUsuario;
                }
                return null;
            });

            // Mostrar y procesar
            dialog.showAndWait().ifPresent(usuario -> {
                String password = txtPassword.getText();
                if (adminController.crearUsuario(usuario, password)) {
                    cargarUsuarios();
                    cargarDashboard();
                    mostrarExito(" Usuario creado exitosamente");
                } else {
                    mostrarError(" Error al crear usuario");
                }
            });

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Editar usuario seleccionado
     */
    @FXML
    private void handleEditarUsuario() {
        UsuarioDTO seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAdvertencia(" Seleccione un usuario para editar");
            return;
        }

        try {
            Dialog<UsuarioDTO> dialog = new Dialog<>();
            dialog.setTitle("Editar Usuario");
            dialog.setHeaderText("Modifique los datos del usuario");

            ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            TextField txtNombre = new TextField(seleccionado.getNombre());
            TextField txtTelefono = new TextField(seleccionado.getTelefono());
            TextField txtDireccion = new TextField(seleccionado.getDireccionFrecuente());

            grid.add(new Label("Nombre:"), 0, 0);
            grid.add(txtNombre, 1, 0);
            grid.add(new Label("Teléfono:"), 0, 1);
            grid.add(txtTelefono, 1, 1);
            grid.add(new Label("Dirección:"), 0, 2);
            grid.add(txtDireccion, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardar) {
                    seleccionado.setNombre(txtNombre.getText());
                    seleccionado.setTelefono(txtTelefono.getText());
                    seleccionado.setDireccionFrecuente(txtDireccion.getText());
                    return seleccionado;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(usuario -> {
                if (adminController.actualizarUsuario(usuario)) {
                    cargarUsuarios();
                    mostrarExito("Usuario actualizado");
                } else {
                    mostrarError("Error al actualizar usuario");
                }
            });

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Eliminar usuario
     */
    @FXML
    private void handleEliminarUsuario() {
        UsuarioDTO seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAdvertencia("Seleccione un usuario para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Eliminar usuario?");
        confirmacion.setContentText("¿Está seguro de eliminar a " + seleccionado.getNombre() + "?\nEsta acción no se puede deshacer.");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (adminController.eliminarUsuario(seleccionado.getId())) {
                    cargarUsuarios();
                    cargarDashboard();
                    mostrarExito("Usuario eliminado");
                } else {
                    mostrarError("Error al eliminar usuario");
                }
            }
        });
    }

    /**
     * Suspender/Activar usuario
     */
    @FXML
    private void handleSuspenderUsuario() {
        UsuarioDTO seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAdvertencia("Seleccione un usuario");
            return;
        }

        EstadoUsuario nuevoEstado = seleccionado.getEstado().equals("ACTIVO") ?
                EstadoUsuario.SUSPENDIDO : EstadoUsuario.ACTIVO;

        String accion = nuevoEstado == EstadoUsuario.ACTIVO ? "activar" : "suspender";

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cambiar Estado");
        confirmacion.setHeaderText("¿" + accion.substring(0, 1).toUpperCase() + accion.substring(1) + " usuario?");
        confirmacion.setContentText("Usuario: " + seleccionado.getNombre());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (adminController.cambiarEstadoUsuario(seleccionado.getId(), nuevoEstado)) {
                    cargarUsuarios();
                    mostrarExito("Usuario " + accion + "do");
                } else {
                    mostrarError("Error al cambiar estado");
                }
            }
        });
    }

// ==========  OPERACIONES CON REPARTIDORES ==========

    /**
     * Crear nuevo repartidor
     */
    @FXML
    private void handleCrearRepartidor() {
        try {
            Dialog<RepartidorDTO> dialog = new Dialog<>();
            dialog.setTitle("Crear Nuevo Repartidor");
            dialog.setHeaderText("Complete los datos del repartidor");

            ButtonType btnCrear = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnCrear, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            TextField txtNombre = new TextField();
            txtNombre.setPromptText("Nombre completo");
            TextField txtCorreo = new TextField();
            txtCorreo.setPromptText("correo@ejemplo.com");
            TextField txtTelefono = new TextField();
            txtTelefono.setPromptText("3001234567");
            TextField txtZona = new TextField();
            txtZona.setPromptText("Zona de cobertura");
            PasswordField txtPassword = new PasswordField();
            txtPassword.setPromptText("Contraseña");

            grid.add(new Label("Nombre:"), 0, 0);
            grid.add(txtNombre, 1, 0);
            grid.add(new Label("Correo:"), 0, 1);
            grid.add(txtCorreo, 1, 1);
            grid.add(new Label("Teléfono:"), 0, 2);
            grid.add(txtTelefono, 1, 2);
            grid.add(new Label("Zona:"), 0, 3);
            grid.add(txtZona, 1, 3);
            grid.add(new Label("Contraseña:"), 0, 4);
            grid.add(txtPassword, 1, 4);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnCrear) {
                    RepartidorDTO nuevoRepartidor = new RepartidorDTO();
                    nuevoRepartidor.setNombre(txtNombre.getText());
                    nuevoRepartidor.setCorreo(txtCorreo.getText());
                    nuevoRepartidor.setTelefono(txtTelefono.getText());
                    nuevoRepartidor.setZonaCobertura(txtZona.getText());
                    nuevoRepartidor.setFechaRegistro(LocalDate.now());
                    nuevoRepartidor.setDisponibilidad("ACTIVO");
                    nuevoRepartidor.setEnviosRealizados("0");
                    nuevoRepartidor.setCalificacionPromedio(5.0);
                    return nuevoRepartidor;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(repartidor -> {
                String password = txtPassword.getText();
                if (adminController.crearRepartidor(repartidor, password)) {
                    cargarRepartidores();
                    cargarDashboard();
                    mostrarExito("Repartidor creado exitosamente");
                } else {
                    mostrarError("Error al crear repartidor");
                }
            });

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Editar repartidor seleccionado
     */
    @FXML
    private void handleEditarRepartidor() {
        RepartidorDTO seleccionado = tblRepartidores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAdvertencia(" Seleccione un repartidor para editar");
            return;
        }

        try {
            Dialog<RepartidorDTO> dialog = new Dialog<>();
            dialog.setTitle("Editar Repartidor");
            dialog.setHeaderText("Modifique los datos del repartidor");

            ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20));

            TextField txtNombre = new TextField(seleccionado.getNombre());
            TextField txtTelefono = new TextField(seleccionado.getTelefono());
            TextField txtZona = new TextField(seleccionado.getZonaCobertura());

            grid.add(new Label("Nombre:"), 0, 0);
            grid.add(txtNombre, 1, 0);
            grid.add(new Label("Teléfono:"), 0, 1);
            grid.add(txtTelefono, 1, 1);
            grid.add(new Label("Zona:"), 0, 2);
            grid.add(txtZona, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnGuardar) {
                    seleccionado.setNombre(txtNombre.getText());
                    seleccionado.setTelefono(txtTelefono.getText());
                    seleccionado.setZonaCobertura(txtZona.getText());
                    return seleccionado;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(repartidor -> {
                if (adminController.actualizarRepartidor(repartidor)) {
                    cargarRepartidores();
                    mostrarExito("Repartidor actualizado");
                } else {
                    mostrarError("Error al actualizar repartidor");
                }
            });

        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cambiar disponibilidad de repartidor
     */
    @FXML
    private void handleCambiarDisponibilidad() {
        RepartidorDTO seleccionado = tblRepartidores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAdvertencia("Seleccione un repartidor");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("ACTIVO", "ACTIVO", "EN_RUTA", "INACTIVO");
        dialog.setTitle("Cambiar Disponibilidad");
        dialog.setHeaderText("Cambiar estado de: " + seleccionado.getNombre());
        dialog.setContentText("Nueva disponibilidad:");

        dialog.showAndWait().ifPresent(disponibilidad -> {
            DisponibilidadRepartidor nuevaDisponibilidad = DisponibilidadRepartidor.valueOf(disponibilidad);
            if (adminController.cambiarDisponibilidadRepartidor(seleccionado.getId(), nuevaDisponibilidad)) {
                cargarRepartidores();
                mostrarExito("Disponibilidad actualizada");
            } else {
                mostrarError("Error al cambiar disponibilidad");
            }
        });
    }

// ==========  OPERACIONES CON ENVÍOS ==========

    /**
     * Asignar envío a repartidor
     */
    @FXML
    private void handleAsignarRepartidor() {
        EnvioAdmin envioSeleccionado = tblEnvios.getSelectionModel().getSelectedItem();

        if (envioSeleccionado == null) {
            mostrarAdvertencia(" Seleccione un envío");
            return;
        }

        // Obtener repartidores disponibles
        List<RepartidorDTO> repartidoresDisponibles = adminController.obtenerRepartidoresDisponibles();

        if (repartidoresDisponibles.isEmpty()) {
            mostrarAdvertencia(" No hay repartidores disponibles");
            return;
        }

        // Crear dialog de selección
        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Asignar Repartidor");
        dialog.setHeaderText("Asignar envío: " + envioSeleccionado.getId());
        dialog.setContentText("Seleccione repartidor:");

        // Agregar repartidores al combo
        for (RepartidorDTO repartidor : repartidoresDisponibles) {
            String item = repartidor.getNombre() + " - " + repartidor.getZonaCobertura();
            dialog.getItems().add(item);
        }

        dialog.setSelectedItem(dialog.getItems().get(0));

        dialog.showAndWait().ifPresent(seleccion -> {
            // Obtener ID del repartidor seleccionado
            int indice = dialog.getItems().indexOf(seleccion);
            RepartidorDTO repartidorSeleccionado = repartidoresDisponibles.get(indice);

            if (adminController.asignarEnvioARepartidor(envioSeleccionado.getId(), repartidorSeleccionado.getId())) {
                cargarEnvios();
                cargarDashboard();
                mostrarExito("Envío asignado a " + repartidorSeleccionado.getNombre());
            } else {
                mostrarError("Error al asignar envío");
            }
        });
    }

    /**
     * Ver detalles completos de un envío
     */
    @FXML
    private void handleVerDetalles() {
        EnvioAdmin seleccionado = tblEnvios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAdvertencia("⚠️ Seleccione un envío");
            return;
        }

        try {
            // Buscar el envío completo
            Envio envioCompleto = adminController.obtenerTodosLosEnvios().stream()
                    .filter(e -> e.getIdEnvio().startsWith(seleccionado.getId()))
                    .findFirst()
                    .orElse(null);

            if (envioCompleto == null) {
                mostrarError("Envío no encontrado");
                return;
            }

            // Crear ventana de detalles
            Alert detalles = new Alert(Alert.AlertType.INFORMATION);
            detalles.setTitle("Detalles del Envío");
            detalles.setHeaderText("Envío: " + seleccionado.getId());

            // ⭐ VERSIÓN SIMPLIFICADA SIN getPaquete()
            String contenido = String.format(
                    "INFORMACIÓN DEL ENVÍO\n\n" +
                            "ID Completo: %s\n" +
                            "Origen: %s\n" +
                            "Destino: %s\n" +
                            "Estado: %s\n" +
                            "Fecha Creación: %s\n" +
                            "Costo: $%.2f COP\n" +
                            "Incidencias: %d",
                    envioCompleto.getIdEnvio(),
                    envioCompleto.getOrigen().getCalle(),
                    envioCompleto.getDestino().getCalle(),
                    envioCompleto.getEstadoEnvio().getDescripcion(),
                    envioCompleto.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    envioCompleto.getCostoTotal(),
                    envioCompleto.contarIncidencias()
            );

            detalles.setContentText(contenido);
            detalles.getDialogPane().setPrefWidth(500);
            detalles.showAndWait();

        } catch (Exception e) {
            mostrarError("Error al cargar detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Filtrar envíos por estado
     */
    @FXML
    private void handleFiltrarEnvios() {
        String estadoFiltro = cmbFiltroEstado.getValue();

        if ("TODOS".equals(estadoFiltro)) {
            cargarEnvios();
            return;
        }

        listaEnvios.clear();
        List<Envio> enviosFiltrados = adminController.obtenerTodosLosEnvios().stream()
                .filter(e -> e.getEstadoEnvio().name().equals(estadoFiltro))
                .collect(java.util.stream.Collectors.toList());

        for (Envio envio : enviosFiltrados) {
            listaEnvios.add(new EnvioAdmin(
                    envio.getIdEnvio().substring(0, 8),
                    envio.getOrigen().getCalle(),
                    envio.getDestino().getCalle(),
                    envio.getEstadoEnvio().getDescripcion(),
                    envio.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    envio.getCostoTotal()
            ));
        }

        mostrarInfo("Filtrados: " + enviosFiltrados.size() + " envíos");
    }


    // ==========  CERRAR SESIÓN ==========

    /**
     * Cerrar sesión y volver al login
     */
    @FXML
    private void handleCerrarSesion() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar Sesión");
        confirmacion.setHeaderText("¿Cerrar sesión?");
        confirmacion.setContentText("¿Está seguro de que desea salir?");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                adminController.cerrarSesion();
                volverAlLogin();
            }
        });
    }

    /**
     * Volver a la pantalla de login
     */
    private void volverAlLogin() {
        try {
            Stage stage = (Stage) lblTotalUsuarios.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/uniquindio/empresalogistica/fxml/Login.fxml")
            );

            Scene scene = new Scene(loader.load(), 400, 600);
            stage.setTitle("Logística Express - Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            System.out.println(" Sesión cerrada, volviendo al login");

        } catch (Exception e) {
            System.err.println(" Error al volver al login: " + e.getMessage());
            e.printStackTrace();
        }
    }

// ==========  MENSAJES Y ALERTAS ==========

    /**
     * Mostrar mensaje de éxito
     */
    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Mostrar mensaje de error
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Mostrar mensaje de advertencia
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Mostrar mensaje informativo
     */
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

// ==========  CLASE INTERNA PARA TABLA DE ENVÍOS ==========

    /**
     * Clase auxiliar para mostrar envíos en la tabla del admin
     */
    public static class EnvioAdmin {
        private String id;
        private String origen;
        private String destino;
        private String estado;
        private String fecha;
        private Double costo;

        public EnvioAdmin(String id, String origen, String destino, String estado, String fecha, Double costo) {
            this.id = id;
            this.origen = origen;
            this.destino = destino;
            this.estado = estado;
            this.fecha = fecha;
            this.costo = costo;
        }

        // Getters
        public String getId() { return id; }
        public String getOrigen() { return origen; }
        public String getDestino() { return destino; }
        public String getEstado() { return estado; }
        public String getFecha() { return fecha; }
        public Double getCosto() { return costo; }

        // Setters
        public void setId(String id) { this.id = id; }
        public void setOrigen(String origen) { this.origen = origen; }
        public void setDestino(String destino) { this.destino = destino; }
        public void setEstado(String estado) { this.estado = estado; }
        public void setFecha(String fecha) { this.fecha = fecha; }
        public void setCosto(Double costo) { this.costo = costo; }
    }

}