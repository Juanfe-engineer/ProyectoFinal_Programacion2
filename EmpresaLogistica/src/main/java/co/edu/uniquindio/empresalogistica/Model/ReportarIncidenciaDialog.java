package co.edu.uniquindio.empresalogistica.Model;

import co.edu.uniquindio.empresalogistica.Controller.RepartidorController;
import co.edu.uniquindio.empresalogistica.Model.Envio;
import co.edu.uniquindio.empresalogistica.Model.Enums.EstadoIncidencia;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/**
 *  DIALOG PARA REPORTAR INCIDENCIAS
 */
public class ReportarIncidenciaDialog extends Dialog<Boolean> {

    private TextField txtIdEnvio;
    private ComboBox<String> cmbTipoIncidencia;
    private TextArea txtDescripcion;
    private CheckBox chkEnvioDetenido;
    private RepartidorController repartidorController;

    public ReportarIncidenciaDialog(String idEnvioPreseleccionado) {
        this.repartidorController = new RepartidorController();

        configurarDialog();
        crearContenido(idEnvioPreseleccionado);
        configurarBotones();
    }

    /**
     * Configurar propiedades del dialog
     */
    private void configurarDialog() {
        setTitle(" Reportar Incidencia");
        setHeaderText("Complete la información de la incidencia");

        // Hacer el dialog modal
        initModality(Modality.APPLICATION_MODAL);

        // Establecer tamaño
        getDialogPane().setPrefSize(600, 500);

        // Estilos
        getDialogPane().setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #ef4444; " +
                        "-fx-border-width: 3; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10;"
        );
    }

    /**
     * Crear el contenido del dialog
     */
    private void crearContenido(String idEnvioPreseleccionado) {
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(20));
        contenido.setStyle("-fx-background-color: white;");

        // ===== TÍTULO CON ICONO =====
        Label lblTitulo = new Label("⚠️ Reportar Incidencia de Envío");
        lblTitulo.setStyle(
                "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #dc2626;"
        );

        // ===== INFORMACIÓN DEL ENVÍO =====
        VBox seccionEnvio = new VBox(10);
        seccionEnvio.setStyle(
                "-fx-background-color: #fef2f2; " +
                        "-fx-padding: 15; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #fca5a5; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 8;"
        );

        Label lblEnvioTitulo = new Label("Información del Envío");
        lblEnvioTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        HBox cajaIdEnvio = new HBox(10);
        cajaIdEnvio.setAlignment(Pos.CENTER_LEFT);

        Label lblIdEnvio = new Label("ID del Envío:");
        lblIdEnvio.setMinWidth(120);
        lblIdEnvio.setStyle("-fx-font-weight: bold;");

        txtIdEnvio = new TextField(idEnvioPreseleccionado != null ? idEnvioPreseleccionado : "");
        txtIdEnvio.setPromptText("Ingrese el ID del envío");
        txtIdEnvio.setPrefWidth(300);
        txtIdEnvio.setStyle(
                "-fx-padding: 8; " +
                        "-fx-border-color: #cbd5e1; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6;"
        );

        Button btnBuscar = new Button("🔍 Buscar");
        btnBuscar.setStyle(
                "-fx-background-color: #3b82f6; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 8 15; " +
                        "-fx-background-radius: 6; " +
                        "-fx-cursor: hand;"
        );
        btnBuscar.setOnAction(e -> buscarEnvio());

        cajaIdEnvio.getChildren().addAll(lblIdEnvio, txtIdEnvio, btnBuscar);
        seccionEnvio.getChildren().addAll(lblEnvioTitulo, cajaIdEnvio);

        // ===== TIPO DE INCIDENCIA =====
        VBox seccionTipo = new VBox(8);
        Label lblTipo = new Label("Tipo de Incidencia:");
        lblTipo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        cmbTipoIncidencia = new ComboBox<>();
        cmbTipoIncidencia.getItems().addAll(
                " Paquete dañado",
                " Dirección incorrecta",
                " Cliente no encontrado",
                " Cliente rechazó el paquete",
                " Retraso en la entrega",
                " Problema con el vehículo",
                "️ Condiciones climáticas adversas",
                " Problemas de comunicación",
                " Acceso restringido",
                "️ Otro (especificar en descripción)"
        );
        cmbTipoIncidencia.setPromptText("Seleccione el tipo de incidencia");
        cmbTipoIncidencia.setPrefWidth(550);
        cmbTipoIncidencia.setStyle(
                "-fx-padding: 8; " +
                        "-fx-border-color: #cbd5e1; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6;"
        );

        seccionTipo.getChildren().addAll(lblTipo, cmbTipoIncidencia);

        // ===== DESCRIPCIÓN =====
        VBox seccionDescripcion = new VBox(8);
        Label lblDescripcion = new Label("Descripción Detallada:");
        lblDescripcion.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        txtDescripcion = new TextArea();
        txtDescripcion.setPromptText(
                "Describa detalladamente la incidencia:\n" +
                        "- ¿Qué ocurrió?\n" +
                        "- ¿Cuándo sucedió?\n" +
                        "- ¿Dónde estaba ubicado?\n" +
                        "- ¿Qué acciones tomó?"
        );
        txtDescripcion.setPrefRowCount(6);
        txtDescripcion.setWrapText(true);
        txtDescripcion.setStyle(
                "-fx-padding: 10; " +
                        "-fx-border-color: #cbd5e1; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-family: 'Segoe UI'; " +
                        "-fx-font-size: 12px;"
        );

        seccionDescripcion.getChildren().addAll(lblDescripcion, txtDescripcion);

        // ===== CHECKBOX ENVÍO DETENIDO =====
        chkEnvioDetenido = new CheckBox("️ El envío está detenido y requiere intervención urgente");
        chkEnvioDetenido.setStyle(
                "-fx-font-size: 12px; " +
                        "-fx-text-fill: #dc2626; " +
                        "-fx-font-weight: bold;"
        );

        // ===== NOTA INFORMATIVA =====
        Label lblNota = new Label(
                "ℹ️ Nota: Esta incidencia será registrada en el sistema y " +
                        "notificada al equipo de soporte y al cliente."
        );
        lblNota.setWrapText(true);
        lblNota.setStyle(
                "-fx-font-size: 11px; " +
                        "-fx-text-fill: #64748b; " +
                        "-fx-padding: 10; " +
                        "-fx-background-color: #f1f5f9; " +
                        "-fx-background-radius: 6;"
        );

        // ===== AGREGAR TODO AL CONTENIDO =====
        contenido.getChildren().addAll(
                lblTitulo,
                new Separator(),
                seccionEnvio,
                seccionTipo,
                seccionDescripcion,
                chkEnvioDetenido,
                lblNota
        );

        getDialogPane().setContent(contenido);
    }

    /**
     * Configurar botones del dialog
     */
    private void configurarBotones() {
        ButtonType btnReportar = new ButtonType(" Reportar Incidencia", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(btnReportar, btnCancelar);

        // Estilo de los botones
        Button reportarBtn = (Button) getDialogPane().lookupButton(btnReportar);
        reportarBtn.setStyle(
                "-fx-background-color: #dc2626; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10 25; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand;"
        );

        Button cancelarBtn = (Button) getDialogPane().lookupButton(btnCancelar);
        cancelarBtn.setStyle(
                "-fx-background-color: #64748b; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10 25; " +
                        "-fx-background-radius: 6; " +
                        "-fx-cursor: hand;"
        );

        // Deshabilitar botón si campos vacíos
        reportarBtn.disableProperty().bind(
                txtIdEnvio.textProperty().isEmpty()
                        .or(cmbTipoIncidencia.valueProperty().isNull())
                        .or(txtDescripcion.textProperty().isEmpty())
        );

        // Configurar resultado
        setResultConverter(buttonType -> {
            if (buttonType == btnReportar) {
                return reportarIncidencia();
            }
            return false;
        });
    }

    /**
     *  BUSCAR ENVÍO
     */
    private void buscarEnvio() {
        String idEnvio = txtIdEnvio.getText().trim();

        if (idEnvio.isEmpty()) {
            mostrarAlerta("⚠ Ingrese un ID de envío", Alert.AlertType.WARNING);
            return;
        }

        try {
            Envio envio = repartidorController.obtenerDetalleEnvio(idEnvio);

            if (envio != null) {
                mostrarAlerta(
                        " Envío encontrado:\n\n" +
                                "Origen: " + envio.getOrigen().getCalle() + "\n" +
                                "Destino: " + envio.getDestino().getCalle() + "\n" +
                                "Estado: " + envio.getEstadoEnvio().getDescripcion(),
                        Alert.AlertType.INFORMATION
                );
            } else {
                mostrarAlerta(" Envío no encontrado", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta(" Error al buscar envío: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     *  REPORTAR INCIDENCIA
     */
    private boolean reportarIncidencia() {
        String idEnvio = txtIdEnvio.getText().trim();
        String tipoIncidencia = cmbTipoIncidencia.getValue();
        String descripcion = txtDescripcion.getText().trim();
        boolean esUrgente = chkEnvioDetenido.isSelected();

        // Construir descripción completa
        String descripcionCompleta = " TIPO: " + tipoIncidencia + "\n\n" +
                " DESCRIPCIÓN:\n" + descripcion + "\n\n" +
                "️ URGENTE: " + (esUrgente ? "SÍ - Requiere intervención inmediata" : "No");

        try {
            boolean exitoso = repartidorController.reportarIncidencia(idEnvio, descripcionCompleta);

            if (exitoso) {
                mostrarAlerta(
                        " Incidencia reportada exitosamente\n\n" +
                                "ID Envío: " + idEnvio + "\n" +
                                "Tipo: " + tipoIncidencia + "\n\n" +
                                "El equipo de soporte ha sido notificado.",
                        Alert.AlertType.INFORMATION
                );
                return true;
            } else {
                mostrarAlerta(" Error al reportar la incidencia", Alert.AlertType.ERROR);
                return false;
            }
        } catch (Exception e) {
            mostrarAlerta(" Error: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    /**
     * Mostrar alerta
     */
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}