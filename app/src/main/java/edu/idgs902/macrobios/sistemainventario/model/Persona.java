package edu.idgs902.macrobios.sistemainventario.model;

public class Persona {

    private int persona_no; // Numero
    private String nombre;
    private String calle;
    private String colonia;
    private String telefono;
    private String email;

    public Persona(int persona_no, String nombre, String calle, String colonia, String telefono, String email) {
        this.persona_no = persona_no;
        this.nombre = nombre;
        this.calle = calle;
        this.colonia = colonia;
        this.telefono = telefono;
        this.email = email;
    }

    public Persona(String nombre, String calle, String colonia, String telefono, String email) {
        this.nombre = nombre;
        this.calle = calle;
        this.colonia = colonia;
        this.telefono = telefono;
        this.email = email;
    }

    public int getPersona_no() {
        return persona_no;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCalle() {
        return calle;
    }

    public String getColonia() {
        return colonia;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
}
