package co.edu.uniquindio.empresalogistica.ViewController;

import co.edu.uniquindio.empresalogistica.Factory.EmpresaLogisticaFactory;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Incidencia;
import co.edu.uniquindio.empresalogistica.Model.Strategy.InfoEnvio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioViewController {

    // ========== COMPONENTES MI PERFIL ==========
    @FXML private Label lblBienvenida;
    @FXML private Label lblNombrePerfil;
    @FXML private Label lblCorreo;
    @FXML private Label lblTelefono;
    @FXML private Label lblFechaRegistro;
    @FXML private Label lblEstado;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtMetodoPago;
    @FXML private TextField txtEnvios;
    @FXML private Button btnGuardarCambios;

    // ========== COMPONENTES NUEVO ENVÍO ==========
    @FXML private TextField txtOrigen;
    @FXML private TextField txtDestino;
    @FXML private TextField txtPeso;
    @FXML private TextField txtVolumen;
    @FXML private TextField txtDistancia;
    @FXML private TextArea txtDescripcion;
    @FXML private ComboBox<String> cmbPrioridad;
    @FXML private CheckBox chkZonaRural;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkFragil;
    @FXML private CheckBox chkFirmaRequerida;
    @FXML private CheckBox chkRastreoPremium;
    @FXML private Label lblCostoEstimado;
    @FXML private Button btnCotizar;
    @FXML private Button btnCrearEnvio;

    // ========== COMPONENTES MIS ENVÍOS ==========
    @FXML private TableView<EnvioTabla> tblEnvios;
    @FXML private TableColumn<EnvioTabla, String> colId;
    @FXML private TableColumn<EnvioTabla, String> colOrigen;
    @FXML private TableColumn<EnvioTabla, String> colDestino;
    @FXML private TableColumn<EnvioTabla, String> colEstado;
    @FXML private TableColumn<EnvioTabla, String> colFecha;
    @FXML private TableColumn<EnvioTabla, Double> colCosto;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private DatePicker dtpFechaInicio;
    @FXML private DatePicker dtpFechaFin;
    @FXML private Button btnFiltrar;
    @FXML private Button btnLimpiarFiltros;
    @FXML private Button btnDescargarCSV;
    @FXML private Button btnDescargarPDF;
    @FXML private TextField txtRastreoId;
    @FXML private Button btnRastrear;
    @FXML private TextArea txtInfoRastreo;

    // ========== COMPONENTES MI CARTERA ==========
    @FXML private Label lblSaldoDisponible;
    @FXML private TextField txtMontoRecarga;
    @FXML private Button btnRecargar;
    @FXML private TextArea txtHistorialMovimientos;

    // ========== COMPONENTES CONFIGURACIÓN ==========
    @FXML private TextField txtNombrePerfil;
    @FXML private TextField txtTelefonoPerfil;
    @FXML private PasswordField txtPasswordActual;
    @FXML private PasswordField txtPasswordNueva;
    @FXML private PasswordField txtPasswordConfirmar;
    @FXML private Button btnActualizarDatos;
    @FXML private Button btnCambiarPassword;
    @FXML private TableColumn<EnvioTabla, String> colIncidencias;

    private EmpresaLogisticaFactory factory;
    private UsuarioDTO usuarioActual;
    private double costoEnvioActual = 0;
    private ObservableList<EnvioTabla> listaEnvios;

    @FXML
    public void initialize() {
        factory = EmpresaLogisticaFactory.getInstance();
        usuarioActual = factory.getUsuarioActual();

        if (usuarioActual != null) {
            cargarDatosUsuario();
            inicializarComponentes();
        } else {
            mostrarError("No hay usuario logueado");
        }
    }

    private void inicializarComponentes() {
        // Inicializar combo de prioridad
        if (cmbPrioridad != null) {
            cmbPrioridad.getItems().addAll("ESTANDAR", "EXPRESS", "PRIORITARIO");
            cmbPrioridad.setValue("ESTANDAR");
        }

        // Inicializar combo de filtro estado
        if (cmbFiltroEstado != null) {
            cmbFiltroEstado.getItems().addAll("TODOS", "SOLICITADO", "CONFIRMADO",
                    "EN_RUTA", "ENTREGADO", "CANCELADO");
            cmbFiltroEstado.setValue("TODOS");
        }

        // Inicializar tabla de envíos
        if (tblEnvios != null) {
            configurarTablaEnvios();
            cargarEnvios();
        }

        // Cargar saldo de cartera
        if (lblSaldoDisponible != null) {
            actualizarSaldoCartera();
        }

        // Cargar datos en configuración
        if (txtNombrePerfil != null) {
            txtNombrePerfil.setText(usuarioActual.getNombre());
            txtTelefonoPerfil.setText(usuarioActual.getTelefono());
        }
    }

    // ========== FUNCIONES MI PERFIL ==========

    private void cargarDatosUsuario() {
        lblBienvenida.setText("¡Bienvenido, " + usuarioActual.getNombre() + "!");

        if (lblNombrePerfil != null) {
            lblNombrePerfil.setText(usuarioActual.getNombre());
        }

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
            mostrarExito("Cambios guardados exitosamente");
        } catch (Exception e) {
            mostrarError("Error al guardar cambios: " + e.getMessage());
        }
    }

    // ========== FUNCIONES NUEVO ENVÍO ==========

    @FXML
    public void handleCotizar() {
        try {
            // Validar campos
            if (txtPeso.getText().isEmpty() || txtDistancia.getText().isEmpty()) {
                mostrarError("Por favor ingrese peso y distancia");
                return;
            }

            double peso = Double.parseDouble(txtPeso.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            String prioridad = cmbPrioridad.getValue();
            boolean zonaRural = chkZonaRural.isSelected();

            // Validaciones de límites
            if (peso <= 0 || peso > 100) {
                mostrarError("El peso debe estar entre 0.1 y 100 kg");
                return;
            }

            if (distancia <= 0 || distancia > 1000) {
                mostrarError("La distancia debe estar entre 1 y 1000 km");
                return;
            }

            // Cotizar usando Strategy Pattern
            costoEnvioActual = factory.cotizarEnvio(peso, distancia, prioridad, zonaRural, "COMBINADA");

            // Agregar servicios adicionales (Decorator Pattern)
            if (chkSeguro.isSelected()) {
                costoEnvioActual += 5000;
            }
            if (chkFragil.isSelected()) {
                costoEnvioActual += 3000;
            }
            if (chkFirmaRequerida.isSelected()) {
                costoEnvioActual += 2000;
            }
            if (chkRastreoPremium.isSelected()) {
                costoEnvioActual += 5000;
            }

            lblCostoEstimado.setText(String.format("Costo Estimado: $%.2f COP", costoEnvioActual));
            lblCostoEstimado.setStyle("-fx-text-fill: #059669; -fx-font-size: 18px; -fx-font-weight: bold;");

        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese valores numéricos válidos");
        }
    }

    @FXML
    public void handleCrearEnvio() {
        try {
            // Validar cotización
            if (costoEnvioActual <= 0) {
                mostrarError("Primero debe cotizar el envío");
                return;
            }

            // Validar campos
            if (txtOrigen.getText().isEmpty() || txtDestino.getText().isEmpty()) {
                mostrarError("Por favor complete origen y destino");
                return;
            }

            // Verificar saldo
            double saldo = factory.obtenerSaldoCartera(usuarioActual.getCorreo());
            if (saldo < costoEnvioActual) {
                mostrarError(String.format("Saldo insuficiente. Necesita $%.2f pero tiene $%.2f",
                        costoEnvioActual, saldo));
                return;
            }

            // Confirmar con el usuario
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Envío");
            confirmacion.setHeaderText("¿Desea crear este envío?");
            confirmacion.setContentText(String.format("Costo total: $%.2f\nSaldo después del pago: $%.2f",
                    costoEnvioActual, saldo - costoEnvioActual));

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                // Crear envío
                double peso = Double.parseDouble(txtPeso.getText());
                double volumen = txtVolumen.getText().isEmpty() ? 0 : Double.parseDouble(txtVolumen.getText());

                Envio nuevoEnvio = factory.crearEnvio(
                        txtOrigen.getText(),
                        txtDestino.getText(),
                        peso,
                        volumen,
                        txtDescripcion.getText(),
                        usuarioActual.getCorreo()
                );

                nuevoEnvio.setCostoTotal(costoEnvioActual);

                // Descontar de cartera
                factory.descontarDeCartera(usuarioActual.getCorreo(), costoEnvioActual);

                // Actualizar saldo
                actualizarSaldoCartera();

                // Generar comprobante
                mostrarComprobante(nuevoEnvio);

                // Limpiar formulario
                limpiarFormularioEnvio();

                // Recargar tabla
                cargarEnvios();

                mostrarExito("¡Envío creado exitosamente!");
            }

        } catch (Exception e) {
            mostrarError("Error al crear envío: " + e.getMessage());
        }
    }

    private void limpiarFormularioEnvio() {
        txtOrigen.clear();
        txtDestino.clear();
        txtPeso.clear();
        txtVolumen.clear();
        txtDistancia.clear();
        txtDescripcion.clear();
        cmbPrioridad.setValue("ESTANDAR");
        chkZonaRural.setSelected(false);
        chkSeguro.setSelected(false);
        chkFragil.setSelected(false);
        chkFirmaRequerida.setSelected(false);
        chkRastreoPremium.setSelected(false);
        lblCostoEstimado.setText("Costo Estimado: $0.00");
        costoEnvioActual = 0;
    }

    private void mostrarComprobante(Envio envio) {
        Alert comprobante = new Alert(Alert.AlertType.INFORMATION);
        comprobante.setTitle("Comprobante de Envío");
        comprobante.setHeaderText("Envío Creado Exitosamente");

        String detalles = String.format(
                "═══════════════════════════════════════\n" +
                        "        LOGÍSTICA EXPRESS\n" +
                        "═══════════════════════════════════════\n\n" +
                        "ID ENVÍO: %s\n" +
                        "FECHA: %s\n\n" +
                        "ORIGEN: %s\n" +
                        "DESTINO: %s\n\n" +
                        "PESO: %.2f kg\n" +
                        "DESCRIPCIÓN: %s\n\n" +
                        "COSTO TOTAL: $%.2f COP\n" +
                        "ESTADO: %s\n" +
                        "FECHA ESTIMADA ENTREGA: %s\n\n" +
                        "═══════════════════════════════════════\n" +
                        "Gracias por confiar en nosotros",
                envio.getIdEnvio().substring(0, 8),
                envio.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                envio.getOrigen().getCalle(),
                envio.getDestino().getCalle(),
                envio.getPesoGramos(),
                envio.getDescripcion(),
                envio.getCostoTotal(),
                envio.getEstadoEnvio().getDescripcion(),
                envio.getFechaEstimadaEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

        comprobante.setContentText(detalles);
        comprobante.showAndWait();
    }

    // ========== FUNCIONES MIS ENVÍOS ==========

    private void configurarTablaEnvios() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colIncidencias.setCellValueFactory(new PropertyValueFactory<>("incidencia"));

        colIncidencias.setCellFactory(column -> new TableCell<EnvioTabla, String>() {
            @Override
            protected void updateItem(String incidencias, boolean empty) {
                super.updateItem(incidencias, empty);

                if (empty || incidencias == null || incidencias.equals("0")) {
                    setText("");
                    setStyle("");
                } else {
                    setText("⚠️ " + incidencias);
                    setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                    setTooltip(new Tooltip("Este envío tiene " + incidencias + " incidencia(s)"));
                }
            }
        });

        listaEnvios = FXCollections.observableArrayList();
        tblEnvios.setItems(listaEnvios);

        tblEnvios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                EnvioTabla envioSeleccionado = tblEnvios.getSelectionModel().getSelectedItem();
                if (envioSeleccionado != null) {
                    mostrarDetallesEnvio(envioSeleccionado);
                }
            }
        });

    }

    /**
     * ⭐ MOSTRAR DETALLES DEL ENVÍO CON INCIDENCIAS
     */
    private void mostrarDetallesEnvio(EnvioTabla envioTabla) {
        try {
            // Buscar el envío completo
            List<Envio> envios = factory.obtenerEnviosPorUsuario(usuarioActual.getCorreo());
            Envio envio = envios.stream()
                    .filter(e -> e.getIdEnvio().startsWith(envioTabla.getId()))
                    .findFirst()
                    .orElse(null);

            if (envio == null) {
                mostrarError("Envío no encontrado");
                return;
            }

            // Crear el contenido del diálogo
            VBox contenido = new VBox(15);
            contenido.setPadding(new Insets(20));
            contenido.setStyle("-fx-background-color: white;");

            // Título
            Label titulo = new Label(" Detalles del Envío");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");

            // Información básica
            GridPane grid = new GridPane();
            grid.setHgap(15);
            grid.setVgap(10);

            grid.add(crearLabel("ID:", true), 0, 0);
            grid.add(crearLabel(envio.getIdEnvio().substring(0, 8), false), 1, 0);

            grid.add(crearLabel("Origen:", true), 0, 1);
            grid.add(crearLabel(envio.getOrigen().getCalle(), false), 1, 1);

            grid.add(crearLabel("Destino:", true), 0, 2);
            grid.add(crearLabel(envio.getDestino().getCalle(), false), 1, 2);

            grid.add(crearLabel("Estado:", true), 0, 3);
            grid.add(crearLabel(envio.getEstadoEnvio().getDescripcion(), false), 1, 3);

            grid.add(crearLabel("Costo:", true), 0, 4);
            grid.add(crearLabel(String.format("$%.2f COP", envio.getCostoTotal()), false), 1, 4);

            contenido.getChildren().addAll(titulo, new Separator(), grid);

            // ⭐ MOSTRAR INCIDENCIAS SI EXISTEN
            if (envio.tieneIncidencias()) {
                Label tituloIncidencias = new Label(" Incidencias Reportadas");
                tituloIncidencias.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #dc2626;");

                VBox cajaIncidencias = new VBox(10);
                cajaIncidencias.setStyle(
                        "-fx-background-color: #fef2f2; " +
                                "-fx-padding: 15; " +
                                "-fx-border-color: #fca5a5; " +
                                "-fx-border-width: 2; " +
                                "-fx-border-radius: 8; " +
                                "-fx-background-radius: 8;"
                );

                for (Incidencia incidencia : envio.getIncidencias()) {
                    VBox itemIncidencia = new VBox(5);
                    itemIncidencia.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-background-radius: 6;");

                    Label lblIdIncidencia = new Label(" Incidencia ID: " + incidencia.getIdIncidencia());
                    lblIdIncidencia.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

                    Label lblEstado = new Label("Estado: " + incidencia.getEstado().name());
                    lblEstado.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b;");

                    Label lblFecha = new Label("Fecha: " + incidencia.getFechaReporte().format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    ));
                    lblFecha.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b;");

                    TextArea txtDescripcionInc = new TextArea(incidencia.getDescripcion());
                    txtDescripcionInc.setEditable(false);
                    txtDescripcionInc.setWrapText(true);
                    txtDescripcionInc.setPrefRowCount(3);
                    txtDescripcionInc.setStyle("-fx-font-size: 11px;");

                    itemIncidencia.getChildren().addAll(lblIdIncidencia, lblEstado, lblFecha, txtDescripcionInc);
                    cajaIncidencias.getChildren().add(itemIncidencia);
                }

                contenido.getChildren().addAll(new Separator(), tituloIncidencias, cajaIncidencias);
            }

            // Crear y mostrar el diálogo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detalles del Envío");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(contenido);
            alert.getDialogPane().setPrefSize(600, 500);
            alert.showAndWait();

        } catch (Exception e) {
            mostrarError("Error al cargar detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Crear label helper
     */
    private Label crearLabel(String texto, boolean negrita) {
        Label label = new Label(texto);
        if (negrita) {
            label.setStyle("-fx-font-weight: bold;");
        }
        return label;
    }



    private void cargarEnvios() {
        listaEnvios.clear();
        List<Envio> envios = factory.obtenerEnviosPorUsuario(usuarioActual.getCorreo());

        for (Envio envio : envios) {
            listaEnvios.add(new EnvioTabla(
                    envio.getIdEnvio().substring(0, 8),
                    envio.getOrigen().getCalle(),
                    envio.getDestino().getCalle(),
                    envio.getEstadoEnvio().getDescripcion(),
                    envio.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    envio.getCostoTotal(),
                    String.valueOf(envio.contarIncidencias())
            ));
        }
    }

    @FXML
    public void handleFiltrar() {
        String estadoFiltro = cmbFiltroEstado.getValue();
        LocalDate fechaInicio = dtpFechaInicio.getValue();
        LocalDate fechaFin = dtpFechaFin.getValue();

        List<Envio> enviosFiltrados = factory.obtenerEnviosPorUsuario(usuarioActual.getCorreo());

        // Filtrar por estado
        if (!"TODOS".equals(estadoFiltro)) {
            EstadoEnvio estado = EstadoEnvio.valueOf(estadoFiltro);
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(e -> e.getEstadoEnvio() == estado)
                    .collect(Collectors.toList());
        }

        // Filtrar por fecha
        if (fechaInicio != null && fechaFin != null) {
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(e -> {
                        LocalDate fechaEnvio = e.getFechaCreacion().toLocalDate();
                        return !fechaEnvio.isBefore(fechaInicio) && !fechaEnvio.isAfter(fechaFin);
                    })
                    .collect(Collectors.toList());
        }

        // Actualizar tabla
        listaEnvios.clear();
        for (Envio envio : enviosFiltrados) {
            listaEnvios.add(new EnvioTabla(
                    envio.getIdEnvio().substring(0, 8),
                    envio.getOrigen().getCalle(),
                    envio.getDestino().getCalle(),
                    envio.getEstadoEnvio().getDescripcion(),
                    envio.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    envio.getCostoTotal(),
                    envio.getIncidencias().toString()
            ));
        }
    }

    @FXML
    public void handleLimpiarFiltros() {
        cmbFiltroEstado.setValue("TODOS");
        dtpFechaInicio.setValue(null);
        dtpFechaFin.setValue(null);
        cargarEnvios();
    }

    @FXML
    public void handleRastrear() {
        String idBusqueda = txtRastreoId.getText().trim();

        if (idBusqueda.isEmpty()) {
            mostrarError("Ingrese un ID de envío");
            return;
        }

        try {
            List<Envio> envios = factory.obtenerEnviosPorUsuario(usuarioActual.getCorreo());
            Envio envioEncontrado = envios.stream()
                    .filter(e -> e.getIdEnvio().startsWith(idBusqueda))
                    .findFirst()
                    .orElse(null);

            if (envioEncontrado == null) {
                txtInfoRastreo.setText("❌ Envío no encontrado");
                return;
            }

            String info = generarInfoRastreo(envioEncontrado);
            txtInfoRastreo.setText(info);

        } catch (Exception e) {
            mostrarError("Error al rastrear: " + e.getMessage());
        }
    }

    private String generarInfoRastreo(Envio envio) {
        StringBuilder sb = new StringBuilder();
        sb.append("🔍 INFORMACIÓN DEL ENVÍO\n");
        sb.append("═══════════════════════════════════\n\n");
        sb.append("📦 ID: ").append(envio.getIdEnvio().substring(0, 8)).append("\n");
        sb.append("📍 Origen: ").append(envio.getOrigen().getCalle()).append("\n");
        sb.append("📍 Destino: ").append(envio.getDestino().getCalle()).append("\n\n");

        sb.append("📊 ESTADO ACTUAL\n");
        sb.append("─────────────────────────────────\n");
        sb.append("Estado: ").append(getEmojiEstado(envio.getEstadoEnvio()));
        sb.append(" ").append(envio.getEstadoEnvio().getDescripcion()).append("\n\n");

        sb.append("📅 FECHAS\n");
        sb.append("─────────────────────────────────\n");
        sb.append("Creación: ").append(envio.getFechaCreacion().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        sb.append("Entrega estimada: ").append(envio.getFechaEstimadaEntrega().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");

        if (envio.getFechaRealEntrega() != null) {
            sb.append("Entrega real: ").append(envio.getFechaRealEntrega().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        }

        sb.append("\n💰 Costo: $").append(String.format("%.2f", envio.getCostoTotal())).append(" COP\n");

        return sb.toString();
    }

    private String getEmojiEstado(EstadoEnvio estado) {
        switch (estado) {
            case SOLICITADO: return "📝";
            case CONFIRMADO: return "✅";
            case EN_RUTA: return "🚚";
            case ENTREGADO: return "🎉";
            case CANCELADO: return "❌";
            default: return "📦";
        }
    }

    @FXML
    public void handleDescargarCSV() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte CSV");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setInitialFileName("envios_" + LocalDate.now() + ".csv");

            File file = fileChooser.showSaveDialog(tblEnvios.getScene().getWindow());

            if (file != null) {
                generarCSV(file);
                mostrarExito("Reporte CSV descargado exitosamente");
            }
        } catch (Exception e) {
            mostrarError("Error al descargar CSV: " + e.getMessage());
        }
    }

    private void generarCSV(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            // Encabezados
            writer.append("ID,Origen,Destino,Estado,Fecha,Costo\n");

            // Datos
            for (EnvioTabla envio : listaEnvios) {
                writer.append(envio.getId()).append(",");
                writer.append(envio.getOrigen()).append(",");
                writer.append(envio.getDestino()).append(",");
                writer.append(envio.getEstado()).append(",");
                writer.append(envio.getFecha()).append(",");
                writer.append(String.valueOf(envio.getCosto())).append("\n");
            }
        }
    }

    @FXML
    public void handleDescargarPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte PDF");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("envios_" + LocalDate.now() + ".pdf");

            File file = fileChooser.showSaveDialog(tblEnvios.getScene().getWindow());

            if (file != null) {
                generarPDF(file);
                mostrarExito("Reporte PDF descargado exitosamente en:\n" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al descargar PDF: " + e.getMessage());
        }
    }

    /**
     * ⭐ GENERAR PDF CON ITEXT
     */
    private void generarPDF(File file) throws Exception {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, new FileOutputStream(file));

        documento.open();

        // ===== TÍTULO =====
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);
        Paragraph titulo = new Paragraph("HISTORIAL DE ENVÍOS", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(10);
        documento.add(titulo);

        // ===== INFORMACIÓN DEL USUARIO =====
        Font infoFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
        Paragraph info = new Paragraph();
        info.add(new Chunk("Usuario: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
        info.add(new Chunk(usuarioActual.getNombre() + "\n", infoFont));
        info.add(new Chunk("Correo: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
        info.add(new Chunk(usuarioActual.getCorreo() + "\n", infoFont));
        info.add(new Chunk("Fecha de generación: ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
        info.add(new Chunk(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n", infoFont));
        info.setSpacingAfter(20);
        documento.add(info);

        // ===== TABLA DE ENVÍOS =====
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{1.5f, 2f, 2f, 1.5f, 1.5f, 1.5f});

        // Encabezados
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        String[] headers = {"ID", "Origen", "Destino", "Estado", "Fecha", "Costo"};

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(new BaseColor(37, 99, 235)); // Azul
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            tabla.addCell(cell);
        }

        // Contenido de la tabla
        Font cellFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

        for (EnvioTabla envio : listaEnvios) {
            // ID
            PdfPCell cellId = new PdfPCell(new Phrase(envio.getId(), cellFont));
            cellId.setPadding(6);
            tabla.addCell(cellId);

            // Origen
            PdfPCell cellOrigen = new PdfPCell(new Phrase(envio.getOrigen(), cellFont));
            cellOrigen.setPadding(6);
            tabla.addCell(cellOrigen);

            // Destino
            PdfPCell cellDestino = new PdfPCell(new Phrase(envio.getDestino(), cellFont));
            cellDestino.setPadding(6);
            tabla.addCell(cellDestino);

            // Estado
            PdfPCell cellEstado = new PdfPCell(new Phrase(envio.getEstado(), cellFont));
            cellEstado.setPadding(6);
            tabla.addCell(cellEstado);

            // Fecha
            PdfPCell cellFecha = new PdfPCell(new Phrase(envio.getFecha(), cellFont));
            cellFecha.setPadding(6);
            tabla.addCell(cellFecha);

            // Costo
            PdfPCell cellCosto = new PdfPCell(new Phrase(String.format("$%.2f", envio.getCosto()), cellFont));
            cellCosto.setPadding(6);
            cellCosto.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla.addCell(cellCosto);
        }

        documento.add(tabla);

        // ===== RESUMEN =====
        documento.add(new Paragraph("\n"));

        Font resumenFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        Paragraph resumen = new Paragraph();
        resumen.add(new Chunk("RESUMEN\n", resumenFont));
        resumen.add(new Chunk("Total de envíos: " + listaEnvios.size() + "\n", infoFont));

        double totalCosto = listaEnvios.stream()
                .mapToDouble(EnvioTabla::getCosto)
                .sum();
        resumen.add(new Chunk("Costo total: $" + String.format("%.2f", totalCosto) + " COP\n", infoFont));
        resumen.setSpacingBefore(15);
        documento.add(resumen);

        // ===== PIE DE PÁGINA =====
        Paragraph footer = new Paragraph(
                "Documento generado por Logística Express © " + LocalDate.now().getYear(),
                new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY)
        );
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(30);
        documento.add(footer);

        documento.close();

        System.out.println("✅ PDF generado correctamente: " + file.getAbsolutePath());
    }

    // ========== FUNCIONES MI CARTERA ==========

    private void actualizarSaldoCartera() {
        double saldo = factory.obtenerSaldoCartera(usuarioActual.getCorreo());
        lblSaldoDisponible.setText(String.format("$%.2f COP", saldo));
    }

    @FXML
    public void handleRecargar() {
        try {
            String montoStr = txtMontoRecarga.getText().trim();

            if (montoStr.isEmpty()) {
                mostrarError("Ingrese un monto a recargar");
                return;
            }

            double monto = Double.parseDouble(montoStr);

            if (monto <= 0) {
                mostrarError("El monto debe ser mayor a 0");
                return;
            }

            if (monto > 1000000) {
                mostrarError("El monto máximo de recarga es $1,000,000");
                return;
            }

            factory.recargarCartera(usuarioActual.getCorreo(), monto);
            actualizarSaldoCartera();

            // Agregar a historial
            String movimiento = String.format("[%s] Recarga: +$%.2f COP\n",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), monto);
            txtHistorialMovimientos.appendText(movimiento);

            txtMontoRecarga.clear();
            mostrarExito(String.format("¡Recarga exitosa de $%.2f!", monto));

        } catch (NumberFormatException e) {
            mostrarError("Ingrese un monto válido");
        } catch (Exception e) {
            mostrarError("Error al recargar: " + e.getMessage());
        }
    }

    // ========== FUNCIONES CONFIGURACIÓN ==========

    @FXML
    public void handleActualizarDatos() {
        try {
            usuarioActual.setNombre(txtNombrePerfil.getText());
            usuarioActual.setTelefono(txtTelefonoPerfil.getText());

            factory.actualizarUsuario(usuarioActual);
            cargarDatosUsuario();

            mostrarExito("Datos actualizados correctamente");
        } catch (Exception e) {
            mostrarError("Error al actualizar: " + e.getMessage());
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
            boolean exito = factory.cambiarContrasenaUsuario(
                    usuarioActual.getCorreo(),
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
                stage.setResizable(false);
                stage.setMaximized(false);
                stage.show();
            } catch (Exception e) {
                mostrarError("Error al cerrar sesión: " + e.getMessage());
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

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ========== CLASE INTERNA PARA TABLA ==========

    public static class EnvioTabla {
        private String id;
        private String origen;
        private String destino;
        private String estado;
        private String fecha;
        private Double costo;
        private String incidencias;

        public EnvioTabla(String id, String origen, String destino, String estado, String fecha, Double costo, String incidencias) {
            this.id = id;
            this.origen = origen;
            this.destino = destino;
            this.estado = estado;
            this.fecha = fecha;
            this.costo = costo;
            this.incidencias = incidencias;
        }

        public String getId() { return id; }
        public String getOrigen() { return origen; }
        public String getDestino() { return destino; }
        public String getEstado() { return estado; }
        public String getFecha() { return fecha; }
        public Double getCosto() { return costo; }
        public String getIncidencias() { return incidencias; }
    }
}