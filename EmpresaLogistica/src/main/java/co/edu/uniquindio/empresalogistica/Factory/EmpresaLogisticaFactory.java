package co.edu.uniquindio.empresalogistica.Factory;

import co.edu.uniquindio.empresalogistica.Mapping.Dto.EnvioDTO;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.UsuarioDTO;
import co.edu.uniquindio.empresalogistica.Mapping.Dto.RepartidorDTO;
import co.edu.uniquindio.empresalogistica.Mapping.mappers.UsuarioMapping;
import co.edu.uniquindio.empresalogistica.Mapping.mappers.RepartidorMapping;
import co.edu.uniquindio.empresalogistica.Model.*;
import co.edu.uniquindio.empresalogistica.Model.Enums.*;
import co.edu.uniquindio.empresalogistica.Model.Strategy.InfoEnvio;
import co.edu.uniquindio.empresalogistica.Model.Strategy.*;

//PDF
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class EmpresaLogisticaFactory {

    private static EmpresaLogisticaFactory instance;
    private List<Usuario> usuariosRegistrados;
    private List<Envio> enviosRegistrados;
    private List<Repartidor> repartidoresRegistrados;
    private Map<String, String> credencialesUsuarios;
    private Map<String, Double> carterasUsuarios;
    private Usuario usuarioActual;
    private Repartidor repartidorActual;
    private ServicioAutenticacion servicioAutenticacion;

    // Singleton
    public static EmpresaLogisticaFactory getInstance() {
        if (instance == null) {
            instance = new EmpresaLogisticaFactory();
        }
        return instance;
    }

    // Constructor privado
    private EmpresaLogisticaFactory() {
        this.usuariosRegistrados = new ArrayList<>();
        this.enviosRegistrados = new ArrayList<>();
        this.repartidoresRegistrados = new ArrayList<>();
        this.credencialesUsuarios = new HashMap<>();
        this.carterasUsuarios = new HashMap<>();
        this.servicioAutenticacion = new ServicioAutenticacion();
        inicializarDatosDemo();
        inicializarRepartidoresDemo();
        inicializarAdministrador();
    }

    public ServicioAutenticacion getServicioAutenticacion() {
        return servicioAutenticacion;
    }

    private void inicializarDatosDemo(){
        usuariosRegistrados.add(new Usuario(
                "1",
                "Juan Carlos",
                "juan@email.com",
                "3105556543",
                LocalDate.of(2023,1,15),
                EstadoUsuario.ACTIVO,
                "Calle 10 #50-25, Apartado",
                "Tarjeta de credito",
                "5"
        ));

        usuariosRegistrados.add(new Usuario(
                "2",
                "Johan García",
                "maria@email.com",
                "3104444444",
                LocalDate.of(2023, 5, 20),
                EstadoUsuario.ACTIVO,
                "Carrera 5 #100-10, Medellín",
                "Billetera virtual",
                "3"
        ));

        // Inicializar carteras demo
        carterasUsuarios.put("juan@email.com", 50000.0);
        carterasUsuarios.put("maria@email.com", 100000.0);
    }

    private void inicializarRepartidoresDemo() {
        repartidoresRegistrados.add(new Repartidor(
                "R1",
                "Carlos Repartidor",
                "carlos@repartidor.com",
                "3201234567",
                DisponibilidadRepartidor.ACTIVO,
                "Zona Norte",
                LocalDate.of(2023, 3, 10),
                EstadoRepartidor.DISPONIBLE,
                "15"
        ));

        repartidoresRegistrados.add(new Repartidor(
                "R2",
                "Laura Mensajera",
                "laura@repartidor.com",
                "3209876543",
                DisponibilidadRepartidor.ACTIVO,
                "Zona Sur",
                LocalDate.of(2023, 6, 15),
                EstadoRepartidor.DISPONIBLE,
                "23"
        ));
    }

    // ============= GESTIÓN DE CREDENCIALES =============

    public void crearCredencialUsuario(String correo, String password) {
        credencialesUsuarios.put(correo, password);
        carterasUsuarios.put(correo, 0.0);

        System.out.println(" Credencial guardada en Factory: " + correo);
    }


    public boolean validarCredenciales(String correo, String password) {
        String passwordGuardada = credencialesUsuarios.get(correo);

        if (passwordGuardada == null) {
            System.out.println(" Usuario no encontrado en credenciales: " + correo);
            return false;
        }

        boolean esValida = passwordGuardada.equals(password);

        if (esValida) {
            System.out.println(" Credenciales válidas para: " + correo);
        } else {
            System.out.println(" Contraseña incorrecta para: " + correo);
        }

        return esValida;
    }

    public boolean cambiarContrasenaUsuario(String correo, String passwordActual, String passwordNueva) {
        if (usuarioActual == null || !usuarioActual.getCorreo().equals(correo)) {
            System.err.println(" Usuario no autorizado para cambiar contraseña");
            return false;
        }

        String passwordGuardada = credencialesUsuarios.get(correo);

        if (passwordGuardada == null) {
            System.err.println(" Usuario no encontrado en credenciales");
            return false;
        }

        if (!passwordGuardada.equals(passwordActual)) {
            System.err.println(" Contraseña actual incorrecta");
            return false;
        }

        if (passwordNueva == null || passwordNueva.trim().isEmpty() || passwordNueva.length() < 6) {
            System.err.println(" La nueva contraseña debe tener al menos 6 caracteres");
            return false;
        }

        credencialesUsuarios.put(correo, passwordNueva);

        if (servicioAutenticacion.estaLogueado() &&
                servicioAutenticacion.obtenerUsuarioActual() != null &&
                servicioAutenticacion.obtenerUsuarioActual().getUsuario().equals(correo)) {
            servicioAutenticacion.cambiarContrasena(passwordActual, passwordNueva);
        }

        System.out.println(" Contraseña actualizada exitosamente para: " + correo);
        return true;
    }

    public boolean cambiarContrasenaRepartidor(String correo, String passwordActual, String passwordNueva) {
        if (repartidorActual == null || !repartidorActual.getCorreo().equals(correo)) {
            System.err.println("❌ Repartidor no autorizado para cambiar contraseña");
            return false;
        }

        return cambiarContrasenaUsuario(correo, passwordActual, passwordNueva);
    }

    // ============= CRUD USUARIOS =============

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) throws Exception{
        for(Usuario usuario : usuariosRegistrados) {
            if(usuario.getCorreo().equalsIgnoreCase(usuarioDTO.getCorreo())) {
                throw new Exception("El correo ya está registrado");
            }
        }

        Usuario nuevoUsuario = new Usuario(
                UUID.randomUUID().toString(),
                usuarioDTO.getNombre(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getTelefono(),
                LocalDate.now(),
                EstadoUsuario.ACTIVO,
                usuarioDTO.getDireccionFrecuente(),
                usuarioDTO.getMetodosPago(),
                "0"
        );

        usuariosRegistrados.add(nuevoUsuario);
        return UsuarioMapping.usuarioToDTO(nuevoUsuario);
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuariosRegistrados.stream()
                .map(UsuarioMapping::usuarioToDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerUsuarioPorId(String id) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        return UsuarioMapping.usuarioToDTO(usuario);
    }

    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        this.usuarioActual = usuario;
        return UsuarioMapping.usuarioToDTO(usuario);
    }

    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getId().equals(usuarioDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado para actualizar"));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccionFrecuente(usuarioDTO.getDireccionFrecuente());
        usuario.setMetodosPago(usuarioDTO.getMetodosPago());
        usuario.setEstado(EstadoUsuario.valueOf(usuarioDTO.getEstado()));

        return UsuarioMapping.usuarioToDTO(usuario);
    }

    public void eliminarUsuario(String id) throws Exception {
        Usuario usuario = usuariosRegistrados.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuario no encontrado para eliminar"));

        usuariosRegistrados.remove(usuario);
    }

    // ============= CRUD REPARTIDORES =============

    public RepartidorDTO crearRepartidor(RepartidorDTO repartidorDTO) throws Exception {
        for(Repartidor repartidor : repartidoresRegistrados) {
            if(repartidor.getCorreo().equalsIgnoreCase(repartidorDTO.getCorreo())) {
                throw new Exception("El correo ya está registrado");
            }
        }

        Repartidor nuevoRepartidor = new Repartidor(
                UUID.randomUUID().toString(),
                repartidorDTO.getNombre(),
                repartidorDTO.getCorreo(),
                repartidorDTO.getTelefono(),
                DisponibilidadRepartidor.INACTIVO,
                repartidorDTO.getZonaCobertura(),
                LocalDate.now(),
                EstadoRepartidor.DISPONIBLE,
                "0"
        );

        repartidoresRegistrados.add(nuevoRepartidor);
        return RepartidorMapping.repartidorToDTO(nuevoRepartidor);
    }

    public List<RepartidorDTO> obtenerTodosLosRepartidores() {
        return repartidoresRegistrados.stream()
                .map(RepartidorMapping::repartidorToDTO)
                .collect(Collectors.toList());
    }

    public RepartidorDTO obtenerRepartidorPorId(String id) throws Exception {
        Repartidor repartidor = repartidoresRegistrados.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Repartidor no encontrado"));

        return RepartidorMapping.repartidorToDTO(repartidor);
    }

    public RepartidorDTO obtenerRepartidorPorCorreo(String correo) throws Exception {
        Repartidor repartidor = repartidoresRegistrados.stream()
                .filter(r -> r.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElseThrow(() -> new Exception("Repartidor no encontrado"));

        this.repartidorActual = repartidor;
        return RepartidorMapping.repartidorToDTO(repartidor);
    }

    public RepartidorDTO actualizarRepartidor(RepartidorDTO repartidorDTO) throws Exception {
        Repartidor repartidor = repartidoresRegistrados.stream()
                .filter(r -> r.getId().equals(repartidorDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Repartidor no encontrado"));

        repartidor.setNombre(repartidorDTO.getNombre());
        repartidor.setTelefono(repartidorDTO.getTelefono());
        repartidor.setZonaCobertura(repartidorDTO.getZonaCobertura());
        repartidor.setDisponibilidadRepartidor(
                DisponibilidadRepartidor.valueOf(repartidorDTO.getDisponibilidad())
        );

        return RepartidorMapping.repartidorToDTO(repartidor);
    }

    // ============= GESTIÓN DE DISPONIBILIDAD REPARTIDOR =============

    public void cambiarDisponibilidadRepartidor(String idRepartidor, DisponibilidadRepartidor nuevaDisponibilidad) throws Exception {
        Repartidor repartidor = repartidoresRegistrados.stream()
                .filter(r -> r.getId().equals(idRepartidor))
                .findFirst()
                .orElseThrow(() -> new Exception("Repartidor no encontrado"));

        repartidor.setDisponibilidadRepartidor(nuevaDisponibilidad);
    }

    public DisponibilidadRepartidor obtenerDisponibilidadRepartidor(String idRepartidor) throws Exception {
        Repartidor repartidor = repartidoresRegistrados.stream()
                .filter(r -> r.getId().equals(idRepartidor))
                .findFirst()
                .orElseThrow(() -> new Exception("Repartidor no encontrado"));

        return repartidor.getDisponibilidadRepartidor();
    }

    public void actualizarZonaCoberturaRepartidor(String idRepartidor, String nuevaZona) throws Exception {
        Repartidor repartidor = repartidoresRegistrados.stream()
                .filter(r -> r.getId().equals(idRepartidor))
                .findFirst()
                .orElseThrow(() -> new Exception("Repartidor no encontrado"));

        repartidor.setZonaCobertura(nuevaZona);
        System.out.println("Zona de cobertura actualizada para: " + repartidor.getNombre());
    }

    // ============= GESTIÓN DE CARTERA =============

    public double obtenerSaldoCartera(String correo) {
        return carterasUsuarios.getOrDefault(correo, 0.0);
    }

    public void recargarCartera(String correo, double monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto debe ser mayor a 0");
        }

        double saldoActual = carterasUsuarios.getOrDefault(correo, 0.0);
        carterasUsuarios.put(correo, saldoActual + monto);
    }

    public void descontarDeCartera(String correo, double monto) throws Exception {
        double saldoActual = carterasUsuarios.getOrDefault(correo, 0.0);

        if (saldoActual < monto) {
            throw new Exception("Saldo insuficiente");
        }

        carterasUsuarios.put(correo, saldoActual - monto);
    }

    // ============= COTIZACIÓN DE ENVÍOS =============

    public double cotizarEnvio(double peso, double distancia, String prioridad, boolean zonaRural, String tipoTarifa) {
        InfoEnvio infoEnvio = new InfoEnvio(peso, distancia, prioridad, zonaRural);
        Tarificador tarificador;

        switch (tipoTarifa.toUpperCase()) {
            case "PESO":
                tarificador = new Tarificador(new TarifaPorPeso());
                break;
            case "DISTANCIA":
                tarificador = new Tarificador(new TarifaPorDistancia());
                break;
            case "PRIORIDAD":
                tarificador = new Tarificador(new TarifaPorPrioridad());
                break;
            default:
                tarificador = new Tarificador(new TarifaCombinada());
        }

        return tarificador.calcularCosto(infoEnvio);
    }

    // ============= GESTIÓN DE ENVÍOS =============

    public Envio crearEnvio(String origen, String destino, double peso, double volumen,
                            String descripcion, String correoUsuario) {

        Direccion dirOrigen = new Direccion(UUID.randomUUID().toString(), "Origen",
                origen, "000000", 0.0, 0.0, "");
        Direccion dirDestino = new Direccion(UUID.randomUUID().toString(), "Destino",
                destino, "000000", 0.0, 0.0, "");

        Envio nuevoEnvio = new Envio(
                UUID.randomUUID().toString(),
                dirOrigen,
                dirDestino,
                peso,
                volumen,
                descripcion
        );

        nuevoEnvio.setEstadoEnvio(EstadoEnvio.SOLICITADO);
        nuevoEnvio.setFechaCreacion(LocalDateTime.now());
        nuevoEnvio.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(3));

        // Asignar repartidor automáticamente
        asignarRepartidorAutomatico(nuevoEnvio);

        enviosRegistrados.add(nuevoEnvio);

        // Incrementar contador de envíos del usuario
        try {
            Usuario usuario = usuariosRegistrados.stream()
                    .filter(u -> u.getCorreo().equals(correoUsuario))
                    .findFirst()
                    .orElse(null);

            if (usuario != null) {
                int envios = Integer.parseInt(usuario.getEnviosRealizados());
                usuario.setEnviosRealizados(String.valueOf(envios + 1));
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar contador: " + e.getMessage());
        }

        return nuevoEnvio;
    }

    private void asignarRepartidorAutomatico(Envio envio) {
        Repartidor repartidorDisponible = repartidoresRegistrados.stream()
                .filter(r -> r.getDisponibilidadRepartidor() == DisponibilidadRepartidor.ACTIVO)
                .findFirst()
                .orElse(null);

        if (repartidorDisponible != null) {
            envio.setEstadoEnvio(EstadoEnvio.CONFIRMADO);
            System.out.println("✓ Envío " + envio.getIdEnvio().substring(0, 8) +
                    " asignado a: " + repartidorDisponible.getNombre());
        } else {
            System.out.println("⚠ No hay repartidores disponibles. Envío en espera.");
        }
    }

    public List<Envio> obtenerEnviosPorUsuario(String correoUsuario) {
        return new ArrayList<>(enviosRegistrados);
    }

    public List<Envio> obtenerEnviosAsignadosARepartidor(String idRepartidor) {
        return enviosRegistrados.stream()
                .filter(e -> e.getEstadoEnvio() != EstadoEnvio.SOLICITADO)
                .collect(Collectors.toList());
    }

    public List<Envio> obtenerEnviosPendientesRepartidor(String idRepartidor) {
        return enviosRegistrados.stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.CONFIRMADO ||
                        e.getEstadoEnvio() == EstadoEnvio.RECOGIDO ||
                        e.getEstadoEnvio() == EstadoEnvio.EN_RUTA ||
                        e.getEstadoEnvio() == EstadoEnvio.EN_REPARTO)
                .collect(Collectors.toList());
    }

    public List<Envio> obtenerEnviosCompletadosRepartidor(String idRepartidor) {
        return enviosRegistrados.stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .collect(Collectors.toList());
    }

    public Envio obtenerEnvioPorId(String idEnvio) throws Exception {
        return enviosRegistrados.stream()
                .filter(e -> e.getIdEnvio().equals(idEnvio) || e.getIdEnvio().startsWith(idEnvio))
                .findFirst()
                .orElseThrow(() -> new Exception("Envío no encontrado"));
    }

    public void actualizarEstadoEnvio(String idEnvio, EstadoEnvio nuevoEstado) throws Exception {
        Envio envio = obtenerEnvioPorId(idEnvio);
        envio.setEstadoEnvio(nuevoEstado);

        if (nuevoEstado == EstadoEnvio.ENTREGADO) {
            envio.setFechaRealEntrega(LocalDateTime.now());
        }
    }

    public void cancelarEnvio(String idEnvio) throws Exception {
        Envio envio = obtenerEnvioPorId(idEnvio);

        if (envio.getEstadoEnvio() == EstadoEnvio.EN_RUTA ||
                envio.getEstadoEnvio() == EstadoEnvio.ENTREGADO) {
            throw new Exception("No se puede cancelar un envío en ruta o entregado");
        }

        envio.setEstadoEnvio(EstadoEnvio.CANCELADO);
    }

    public List<Envio> filtrarEnviosPorEstado(EstadoEnvio estado) {
        return enviosRegistrados.stream()
                .filter(e -> e.getEstadoEnvio() == estado)
                .collect(Collectors.toList());
    }

    public List<Envio> filtrarEnviosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return enviosRegistrados.stream()
                .filter(e -> {
                    LocalDate fechaEnvio = e.getFechaCreacion().toLocalDate();
                    return !fechaEnvio.isBefore(fechaInicio) && !fechaEnvio.isAfter(fechaFin);
                })
                .collect(Collectors.toList());
    }

    public void crearIncidenciaEnvio(String idEnvio, String descripcion) throws Exception {
        Envio envio = enviosRegistrados.stream()
                .filter(e -> e.getIdEnvio().startsWith(idEnvio) || e.getIdEnvio().equals(idEnvio))
                .findFirst()
                .orElseThrow(() -> new Exception("Envío no encontrado"));

        System.out.println("=== INCIDENCIA REPORTADA ===");
        System.out.println("Envío ID: " + envio.getIdEnvio());
        System.out.println("Descripción: " + descripcion);
        System.out.println("Estado anterior: " + envio.getEstadoEnvio());

        // No cambiar a CANCELADO automáticamente, solo registrar
        System.out.println("Incidencia registrada en el sistema");
    }

    // ============= ESTADÍSTICAS REPARTIDOR =============

    public int contarEnviosRepartidorPorFecha(String idRepartidor, LocalDate fecha) {
        return (int) enviosRegistrados.stream()
                .filter(e -> e.getFechaCreacion().toLocalDate().equals(fecha))
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .count();
    }

    public int contarEnviosRepartidorPorPeriodo(String idRepartidor, LocalDate inicio, LocalDate fin) {
        return (int) enviosRegistrados.stream()
                .filter(e -> {
                    LocalDate fechaEnvio = e.getFechaCreacion().toLocalDate();
                    return !fechaEnvio.isBefore(inicio) && !fechaEnvio.isAfter(fin);
                })
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .count();
    }

    public double calcularPromedioTiempoEntregaRepartidor(String idRepartidor) {
        List<Envio> enviosEntregados = enviosRegistrados.stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .filter(e -> e.getFechaRealEntrega() != null)
                .collect(Collectors.toList());

        if (enviosEntregados.isEmpty()) {
            return 45.0; // Valor por defecto
        }

        long totalMinutos = 0;
        for (Envio envio : enviosEntregados) {
            long minutos = java.time.Duration.between(
                    envio.getFechaCreacion(),
                    envio.getFechaRealEntrega()
            ).toMinutes();
            totalMinutos += minutos;
        }

        return (double) totalMinutos / enviosEntregados.size();
    }

    public double obtenerCalificacionPromedioRepartidor(String idRepartidor) {
        int enviosEntregados = (int) enviosRegistrados.stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.ENTREGADO)
                .count();

        int incidencias = contarIncidenciasRepartidor(idRepartidor);

        double calificacion = 5.0 - (incidencias * 0.2);
        return Math.max(calificacion, 3.0);
    }

    public int contarIncidenciasRepartidor(String idRepartidor) {
        return (int) enviosRegistrados.stream()
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.CANCELADO)
                .count();
    }


    // ============= GESTIÓN DE SESIÓN =============

    public UsuarioDTO getUsuarioActual() {
        if (usuarioActual == null) {
            return null;
        }
        return UsuarioMapping.usuarioToDTO(usuarioActual);
    }

    public RepartidorDTO getRepartidorActual() {
        if (repartidorActual == null) {
            return null;
        }
        return RepartidorMapping.repartidorToDTO(repartidorActual);
    }

    public void logout() {
        usuarioActual = null;
    }

    public void logoutRepartidor() {
        repartidorActual = null;
    }

    // ========== GESTION DE PDF ================

    public void exportarHistorialEnviosPDF(String correoUsuario) throws Exception {
        List<Envio> envios = obtenerEnviosPorUsuario(correoUsuario);

        if (envios.isEmpty()) {
            throw new Exception("No hay envíos registrados para este usuario");
        }

        Document documento = new Document(PageSize.A4);
        String rutaArchivo = "Historial_Envios_" + correoUsuario.replace("@", "_") + ".pdf";
        PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));

        documento.open();

        // Título principal
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Historial de Envíos - " + correoUsuario, tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        documento.add(titulo);

        // Tabla
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{2f, 2f, 2f, 2f, 2f});

        // Encabezados
        String[] headers = {"ID Envío", "Origen", "Destino", "Estado", "Fecha"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(cell);
        }

        // Contenido
        for (Envio envio : envios) {
            tabla.addCell(envio.getIdEnvio());
            tabla.addCell(envio.getOrigen().getCalle());
            tabla.addCell(envio.getDestino().getCalle());
            tabla.addCell(envio.getEstadoEnvio().name());
            tabla.addCell(envio.getFechaCreacion().toLocalDate().toString());
        }

        documento.add(tabla);

        // Pie de página
        Paragraph footer = new Paragraph("Generado por EmpresaLogística © " + LocalDate.now(),
                new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setSpacingBefore(20);
        documento.add(footer);

        documento.close();

        System.out.println("✅ PDF generado correctamente: " + rutaArchivo);
    }


    /**
     * Incrementar contador de incidencias de un repartidor
     */
    public void incrementarIncidenciasRepartidor(String idRepartidor) throws Exception {
        Repartidor repartidor = repartidoresRegistrados.stream()
                .filter(r -> r.getId().equals(idRepartidor))
                .findFirst()
                .orElseThrow(() -> new Exception("Repartidor no encontrado"));

        repartidor.incrementarIncidencias();

        if (repartidorActual != null && repartidorActual.getId().equals(idRepartidor)) {
            repartidorActual.setIncidenciasReportadas(repartidor.getIncidenciasReportadas());
        }

        System.out.println("Incidencias del repartidor " + repartidor.getNombre() +
                ": " + repartidor.getIncidenciasReportadas());
    }

    public void agregarIncidenciaAEnvio(String idEnvio, Incidencia incidencia) throws Exception {
        Envio envio = enviosRegistrados.stream()
                .filter(e -> e.getIdEnvio().startsWith(idEnvio) || e.getIdEnvio().equals(idEnvio))
                .findFirst()
                .orElseThrow(() -> new Exception("Envío no encontrado"));

        envio.agregarIncidencia(incidencia);


        System.out.println("✅ Incidencia agregada al envío: " + idEnvio);
    }

   /**
     * Obtener envíos de un repartidor
     */
    public List<Envio> obtenerEnviosPorRepartidor(String idRepartidor) {
        return enviosRegistrados.stream()
                .filter(e -> e.getRepartidorAsociado() != null &&
                        e.getRepartidorAsociado().getId().equals(idRepartidor))
                .collect(Collectors.toList());
    }


    public boolean actualizarDisponibilidadRepartidor(String idRepartidor, String disponibilidad) {
        try {
            Repartidor repartidor = repartidoresRegistrados.stream()
                    .filter(r -> r.getId().equals(idRepartidor))
                    .findFirst()
                    .orElse(null);

            if (repartidor == null) return false;

            repartidor.setDisponibilidadRepartidor(
                    DisponibilidadRepartidor.valueOf(disponibilidad)
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cambiarEstadoEnvio(String idEnvio, String nuevoEstado) {
        try {
            Envio envio = obtenerEnvioPorId(idEnvio);
            if (envio == null) return false;

            envio.setEstadoEnvio(EstadoEnvio.valueOf(nuevoEstado));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Envio> obtenerTodosLosEnvios() {
        return new ArrayList<>(enviosRegistrados);
    }


    private void inicializarAdministrador() {
        credencialesUsuarios.put("admin@admin.com", "admin123");
        System.out.println("✅ Administrador inicializado: admin@admin.com / admin123");
    }





}