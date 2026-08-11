package org.example.scdeedec;

import equipos.modelo.EquipoComputo;
import equipos.service.EquipoService;

import usuarios.modelo.Usuario;
import usuarios.service.UsuarioService;

import mantenimientos.modelo.Mantenimiento;
import mantenimientos.service.MantenimientoService;

import reparaciones.modelo.Reparacion;
import reparaciones.service.ReparacionService;

import reportes.modelo.Reporte;
import reportes.service.ReporteService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite de pruebas centralizada para los componentes del sistema
 * (Equipos, Usuarios, Mantenimientos, Reparaciones, Reportes).
 *
 * Todas las pruebas son UNITARIAS: ninguna toca la base de datos real.
 * Los métodos de validación (validar, validarEquipo, validarRangoFechas...)
 * son privados en cada Service, así que se invocan por reflexión en vez de
 * cambiarles la visibilidad en el código de producción. Los métodos públicos
 * que requieren base de datos (registrar, actualizar, obtenerTodos...) NO se
 * llaman directamente en ningún módulo — eso queda para pruebas de
 * integración aparte.
 */
class SCdeEdeCApplicationTests {

    @Test
    @DisplayName("Permite crear un EquipoComputo y leer sus datos")
    void permiteCrearUnEquipoComputo() {
        EquipoComputo equipo = new EquipoComputo();

        equipo.setTipo("Laptop");
        equipo.setMarca("Dell");
        equipo.setModelo("Latitude 5420");
        equipo.setNumeroSerie("PRUEBA-001");

        assertEquals("Laptop", equipo.getTipo());
        assertEquals("Dell", equipo.getMarca());
        assertEquals("Latitude 5420", equipo.getModelo());
        assertEquals("PRUEBA-001", equipo.getNumeroSerie());
    }

    // ======================================================================
    // EQUIPOS
    // ======================================================================
    @Nested
    @DisplayName("Equipos")
    class EquiposTests {

        private final EquipoService service = new EquipoService();

        private EquipoComputo equipoValido() {
            EquipoComputo equipo = new EquipoComputo();
            equipo.setTipo("Laptop");
            equipo.setMarca("Dell");
            equipo.setModelo("Latitude 5420");
            equipo.setNumeroSerie("SN-001");
            equipo.setEstado("Activo");
            equipo.setUbicacion("Oficina 2");
            return equipo;
        }

        @Test
        @DisplayName("Un equipo con todos los campos obligatorios completos no lanza excepción")
        void equipoValidoNoLanzaExcepcion() {
            assertDoesNotThrow(() -> validar(equipoValido()));
        }

        @Test
        @DisplayName("Falla si el tipo está vacío o son solo espacios")
        void tipoVacioLanzaExcepcion() {
            EquipoComputo equipo = equipoValido();
            equipo.setTipo("   ");

            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> validar(equipo));
            assertEquals("El tipo de equipo es obligatorio.", ex.getMessage());
        }

        @Test
        @DisplayName("Falla si la marca es null")
        void marcaNulaLanzaExcepcion() {
            EquipoComputo equipo = equipoValido();
            equipo.setMarca(null);

            assertThrows(IllegalArgumentException.class, () -> validar(equipo));
        }

        @Test
        @DisplayName("Falla si el modelo está vacío")
        void modeloVacioLanzaExcepcion() {
            EquipoComputo equipo = equipoValido();
            equipo.setModelo("");

            assertThrows(IllegalArgumentException.class, () -> validar(equipo));
        }

        @Test
        @DisplayName("Falla si el número de serie está vacío")
        void numeroSerieVacioLanzaExcepcion() {
            EquipoComputo equipo = equipoValido();
            equipo.setNumeroSerie(" ");

            assertThrows(IllegalArgumentException.class, () -> validar(equipo));
        }

        @Test
        @DisplayName("Falla si el estado es null")
        void estadoNuloLanzaExcepcion() {
            EquipoComputo equipo = equipoValido();
            equipo.setEstado(null);

            assertThrows(IllegalArgumentException.class, () -> validar(equipo));
        }

        @Test
        @DisplayName("La ubicación es opcional: un equipo sin ubicación sigue siendo válido")
        void ubicacionEsOpcional() {
            EquipoComputo equipo = equipoValido();
            equipo.setUbicacion(null);

            assertDoesNotThrow(() -> validar(equipo));
        }

        @Test
        @DisplayName("limpiarDatos recorta espacios en blanco de los campos de texto")
        void limpiarDatosRecortaEspacios() throws Throwable {
            EquipoComputo equipo = equipoValido();
            equipo.setTipo("  Laptop  ");
            equipo.setMarca(" Dell ");
            equipo.setUbicacion("  Oficina 2  ");

            invocarPrivado(service, "limpiarDatos", EquipoComputo.class, equipo);

            assertEquals("Laptop", equipo.getTipo());
            assertEquals("Dell", equipo.getMarca());
            assertEquals("Oficina 2", equipo.getUbicacion());
        }

        private void validar(EquipoComputo equipo) throws Throwable {
            invocarPrivado(service, "validarEquipo", EquipoComputo.class, equipo);
        }
    }

    // ======================================================================
    // USUARIOS
    // ======================================================================
    @Nested
    @DisplayName("Usuarios")
    class UsuariosTests {

        private final UsuarioService service = new UsuarioService();

        private Usuario usuarioValido() {
            return new Usuario("Natanael", "Solís", "natanael@correo.com", "8888-8888", null);
        }

        @Test
        @DisplayName("Un usuario con todos los campos válidos no lanza excepción")
        void usuarioValidoNoLanzaExcepcion() {
            assertDoesNotThrow(() -> validar(usuarioValido()));
        }

        @Test
        @DisplayName("Falla si el nombre contiene números")
        void nombreConNumerosLanzaExcepcion() {
            Usuario usuario = usuarioValido();
            usuario.setNombre("Natanael2");

            assertThrows(IllegalArgumentException.class, () -> validar(usuario));
        }

        @Test
        @DisplayName("Falla si el nombre es demasiado corto (menos de 2 letras)")
        void nombreMuyCortoLanzaExcepcion() {
            Usuario usuario = usuarioValido();
            usuario.setNombre("A");

            assertThrows(IllegalArgumentException.class, () -> validar(usuario));
        }

        @Test
        @DisplayName("El nombre acepta acentos y ñ")
        void nombreConAcentosEsValido() {
            Usuario usuario = usuarioValido();
            usuario.setNombre("José Ñúñez");

            assertDoesNotThrow(() -> validar(usuario));
        }

        @Test
        @DisplayName("Falla si el apellido contiene caracteres no permitidos")
        void apellidoConSimbolosLanzaExcepcion() {
            Usuario usuario = usuarioValido();
            usuario.setApellido("Solís#123");

            assertThrows(IllegalArgumentException.class, () -> validar(usuario));
        }

        @Test
        @DisplayName("Falla si el email no contiene @")
        void emailSinArrobaLanzaExcepcion() {
            Usuario usuario = usuarioValido();
            usuario.setEmail("natanael.correo.com");

            assertThrows(IllegalArgumentException.class, () -> validar(usuario));
        }

        @Test
        @DisplayName("Falla si el teléfono contiene letras")
        void telefonoConLetrasLanzaExcepcion() {
            Usuario usuario = usuarioValido();
            usuario.setTelefono("ochoocho88");

            assertThrows(IllegalArgumentException.class, () -> validar(usuario));
        }

        @Test
        @DisplayName("El teléfono acepta formato internacional con +, espacios y guiones")
        void telefonoConFormatoInternacionalEsValido() {
            Usuario usuario = usuarioValido();
            usuario.setTelefono("+506 8888-8888");

            assertDoesNotThrow(() -> validar(usuario));
        }

        private void validar(Usuario usuario) throws Throwable {
            invocarPrivado(service, "validar", Usuario.class, usuario);
        }
    }

    // ======================================================================
    // MANTENIMIENTOS
    // ======================================================================
    @Nested
    @DisplayName("Mantenimientos")
    class MantenimientosTests {

        private final MantenimientoService service = new MantenimientoService();

        private Mantenimiento mantenimientoValido() {
            return new Mantenimiento(1, "Limpieza interna y cambio de pasta térmica",
                    new Date(), "preventivo", "Natanael Solís", "Sin observaciones");
        }

        @Test
        @DisplayName("Un mantenimiento con todos los campos obligatorios completos no lanza excepción")
        void mantenimientoValidoNoLanzaExcepcion() {
            assertDoesNotThrow(() -> validar(mantenimientoValido()));
        }

        @Test
        @DisplayName("Falla si el ID de equipo es 0")
        void idEquipoCeroLanzaExcepcion() {
            Mantenimiento m = mantenimientoValido();
            m.setIdEquipo(0);

            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> validar(m));
            assertEquals("El ID del equipo debe ser mayor a 0.", ex.getMessage());
        }

        @Test
        @DisplayName("Falla si la descripción está vacía")
        void descripcionVaciaLanzaExcepcion() {
            Mantenimiento m = mantenimientoValido();
            m.setDescripcion("   ");

            assertThrows(IllegalArgumentException.class, () -> validar(m));
        }

        @Test
        @DisplayName("Falla si el técnico está vacío")
        void tecnicoVacioLanzaExcepcion() {
            Mantenimiento m = mantenimientoValido();
            m.setTecnico("");

            assertThrows(IllegalArgumentException.class, () -> validar(m));
        }

        private void validar(Mantenimiento mantenimiento) throws Throwable {
            invocarPrivado(service, "validar", Mantenimiento.class, mantenimiento);
        }
    }

    // ======================================================================
    // REPARACIONES
    // ======================================================================
    @Nested
    @DisplayName("Reparaciones")
    class ReparacionesTests {

        private final ReparacionService service = new ReparacionService();

        private Date hace(int dias) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, dias);
            return cal.getTime();
        }

        private Reparacion reparacionValida() {
            return new Reparacion(1, hace(-2), "No enciende", "Fuente de poder dañada",
                    "Se reemplazó la fuente", "Natanael Solís", 45.50, "Pendiente", null, "Cliente avisado");
        }

        @Test
        @DisplayName("Una reparación con todos los campos obligatorios completos no lanza excepción")
        void reparacionValidaNoLanzaExcepcion() {
            assertDoesNotThrow(() -> validar(reparacionValida()));
        }

        @Test
        @DisplayName("Falla si el ID de equipo es 0")
        void idEquipoCeroLanzaExcepcion() {
            Reparacion r = reparacionValida();
            r.setIdEquipo(0);

            assertThrows(IllegalArgumentException.class, () -> validar(r));
        }

        @Test
        @DisplayName("Falla si la falla reportada está vacía")
        void fallaReportadaVaciaLanzaExcepcion() {
            Reparacion r = reparacionValida();
            r.setFallaReportada("   ");

            assertThrows(IllegalArgumentException.class, () -> validar(r));
        }

        @Test
        @DisplayName("Falla si el costo es negativo")
        void costoNegativoLanzaExcepcion() {
            Reparacion r = reparacionValida();
            r.setCosto(-10);

            assertThrows(IllegalArgumentException.class, () -> validar(r));
        }

        @Test
        @DisplayName("Falla si la fecha de entrega es anterior a la de ingreso")
        void fechaEntregaAnteriorAIngresoLanzaExcepcion() {
            Reparacion r = reparacionValida();
            r.setFechaIngreso(hace(-1));
            r.setFechaEntrega(hace(-5));

            assertThrows(IllegalArgumentException.class, () -> validar(r));
        }

        @Test
        @DisplayName("La fecha de entrega es opcional")
        void fechaEntregaEsOpcional() {
            Reparacion r = reparacionValida();
            r.setFechaEntrega(null);

            assertDoesNotThrow(() -> validar(r));
        }

        private void validar(Reparacion reparacion) throws Throwable {
            invocarPrivado(service, "validar", Reparacion.class, reparacion);
        }
    }

    // ======================================================================
    // REPORTES
    // ======================================================================
    @Nested
    @DisplayName("Reportes")
    class ReportesTests {

        private final ReporteService service = new ReporteService();

        @Test
        @DisplayName("Falla si la fecha 'desde' es posterior a la fecha 'hasta' (mantenimientos)")
        void fechaDesdePosteriorAHastaLanzaExcepcion() {
            LocalDate desde = LocalDate.of(2026, 6, 15);
            LocalDate hasta = LocalDate.of(2026, 1, 1);

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.obtenerMantenimientosPorEquipo(desde, hasta));
            assertEquals("La fecha 'desde' no puede ser posterior a la fecha 'hasta'", ex.getMessage());
        }

        @Test
        @DisplayName("Falla si falta la fecha 'desde' o la fecha 'hasta'")
        void fechaFaltanteLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class,
                    () -> service.obtenerReparacionesPorEquipo(null, LocalDate.now()));
        }

        @Test
        @DisplayName("Un rango de fechas válido (desde <= hasta) no lanza excepción")
        void rangoValidoNoLanzaExcepcion() throws Throwable {
            invocarPrivado(service, "validarRangoFechas",
                    new Class<?>[]{LocalDate.class, LocalDate.class},
                    LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 15));
        }

        @Test
        @DisplayName("Un reporte sin filas reporta 0 filas totales y una lista vacía")
        void reporteVacioSeRepresentaCorrectamente() {
            List<String> columnas = List.of("tipo", "cantidad");
            Reporte reporte = new Reporte(columnas, Collections.emptyList());

            assertEquals(0, reporte.getTotalFilas());
            assertTrue(reporte.getFilas().isEmpty());
        }

        @Test
        @DisplayName("Un reporte con filas cuenta correctamente el total")
        void reporteConFilasCuentaCorrectamente() {
            List<Object[]> filas = List.of(new Object[]{"Laptop", 4}, new Object[]{"Desktop", 2});
            Reporte reporte = new Reporte(List.of("tipo", "cantidad"), filas);

            assertEquals(2, reporte.getTotalFilas());
        }
    }

    // ======================================================================
    // HELPERS DE REFLEXIÓN (compartidos por todas las clases anidadas)
    // ======================================================================

    /** Invoca un método privado de un parámetro y desenvuelve la excepción real que lanzó. */
    private static void invocarPrivado(Object objetivo, String nombreMetodo, Class<?> tipoParametro, Object argumento) throws Throwable {
        invocarPrivado(objetivo, nombreMetodo, new Class<?>[]{tipoParametro}, argumento);
    }

    /** Igual que el anterior, pero para métodos con más de un parámetro. */
    private static void invocarPrivado(Object objetivo, String nombreMetodo, Class<?>[] tiposParametros, Object... argumentos) throws Throwable {
        Method metodo = objetivo.getClass().getDeclaredMethod(nombreMetodo, tiposParametros);
        metodo.setAccessible(true);
        try {
            metodo.invoke(objetivo, argumentos);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
