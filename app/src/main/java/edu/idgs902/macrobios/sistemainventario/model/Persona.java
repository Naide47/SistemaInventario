package edu.idgs902.macrobios.sistemainventario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Persona implements Parcelable {

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

    protected Persona(Parcel in) {
        persona_no = in.readInt();
        nombre = in.readString();
        calle = in.readString();
        colonia = in.readString();
        telefono = in.readString();
        email = in.readString();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(persona_no);
        dest.writeString(nombre);
        dest.writeString(calle);
        dest.writeString(colonia);
        dest.writeString(telefono);
        dest.writeString(email);
    }
}
