package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.DataBase;
import edu.idgs902.macrobios.sistemainventario.model.Persona;

public class ControllerPersona extends DataBase {

    private final Context context;
    private DataBase conection;
    private SQLiteDatabase sqlite;
    private ContentValues values;


    public ControllerPersona(Context context, Context context1) {
        super(context);
        this.context = context1;
    }

    public long addPersona(Persona persona) {
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        long result = -1;
        try {
            if (sqlite != null) {
                values = new ContentValues();
                values.put(K_PERSONA_NOMBRE, persona.getNombre());
                values.put(K_PERSONA_CALLE, persona.getCalle());
                values.put(K_PERSONA_COLONIA, persona.getColonia());
                values.put(K_PERSONA_TELEFONO, persona.getTelefono());
                values.put(K_PERSONA_EMAIL, persona.getEmail());

                result = sqlite.insert(T_PERSONA, null, values);
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("addPersona", ex.toString());
        }
        return result;
    }

    public Persona getPersona(int noPersona) {
        Persona persona = null;
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                cursor = sqlite.rawQuery("SELECT * FROM " + T_PERSONA + " WHERE " + K_PERSONA_NOPERSONA + "=?",
                        new String[]{String.valueOf(noPersona)});
                if (cursor.moveToFirst()) {
                    persona = new Persona(cursor.getInt(cursor.getColumnIndex(K_PERSONA_NOPERSONA)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_CALLE)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_COLONIA)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_TELEFONO)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_EMAIL)));

                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getPersona", ex.toString());
        }
        return persona;
    }

    public boolean updatePersona(Persona persona) {
        int result = 0;
        try {
            if (getPersona(persona.getPersona_no()) != null) {
                conection = new DataBase(context);
                sqlite = conection.getWritableDatabase();

                values = new ContentValues();
                values.put(K_PERSONA_NOMBRE, persona.getNombre());
                values.put(K_PERSONA_CALLE, persona.getCalle());
                values.put(K_PERSONA_COLONIA, persona.getColonia());
                values.put(K_PERSONA_TELEFONO, persona.getTelefono());
                values.put(K_PERSONA_EMAIL, persona.getEmail());

                result = sqlite.update(T_PERSONA,
                        values, K_PERSONA_NOPERSONA + "=?",
                        new String[]{String.valueOf(persona.getPersona_no())});
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("updatePersona", ex.toString());
        }
        return result != 0;
    }

    public boolean deletePersona(int noPersona) {
        int result = 0;
        try {
            if (getPersona(noPersona) != null) {
                conection = new DataBase(context);
                sqlite = conection.getWritableDatabase();

                result = sqlite.delete(T_PERSONA,
                        K_PERSONA_NOPERSONA + "=?",
                        new String[]{String.valueOf(noPersona)});
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("deletePersona", ex.toString());
        }
        return result != 0;
    }

    public List<Persona> getPersonas() {
        List<Persona> personas = null;
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                personas = new ArrayList<>();
                cursor = sqlite.rawQuery("SELECT * FROM " + T_PERSONA, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Persona persona = new Persona(cursor.getInt(cursor.getColumnIndex(K_PERSONA_NOPERSONA)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_NOMBRE)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_CALLE)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_COLONIA)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_TELEFONO)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_EMAIL)));
                        personas.add(persona);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getPersonas", ex.toString());
        }
        return personas;
    }
}
