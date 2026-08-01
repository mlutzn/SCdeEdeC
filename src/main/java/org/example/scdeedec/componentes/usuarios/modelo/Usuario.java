package org.example.scdeedec.componentes.usuarios.modelo;

/**
 * Modelo que representa un usuario del sistema.
 * Corresponde 1 a 1 con las columnas de la tabla SCdeEdeC_Usuario en MySQL.
 */


public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Integer idEquipo;

    //Constructor vacío, en blanco

    public Usuario() {
    }

    // El this se refiere "a este objeto en particular, Sin el this, Java no sabría a cuál te refieres.

    public Usuario(String nombre, String apellido, String email, String telefono, Integer idEquipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.idEquipo = idEquipo;
    }

    /**
     *Estos son métodos que te dejan leer (get) y modificar (set)
     * por cada atributo hay siempre un par — un get (que no recibe nada y devuelve el valor)
     * y un set (que recibe un valor nuevo y no devuelve nada, void).
     */

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }
}

/**
 * 6 atributos privados (uno por columna de tabla).
 * Constructor vacío (para cuando el DAO lea desde la BD).
 * Constructor con datos (para cuando el usuario registre uno nuevo desde el formulario).
 * Getters y setters de cada atributo (para respetar el encapsulamiento).
 */