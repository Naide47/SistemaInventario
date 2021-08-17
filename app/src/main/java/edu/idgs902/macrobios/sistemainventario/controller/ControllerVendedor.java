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
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;
import edu.idgs902.macrobios.sistemainventario.view.vendedor.AgregarVendedor;

public class ControllerVendedor extends DataBase {

    private final Context context;
    private DataBase conection;
    private SQLiteDatabase sqlite;
    private ContentValues values;

    public ControllerVendedor(Context context) {
        super(context);
        this.context = context;
    }

    public long addVendedor(Vendedor vendedor) {
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        long result = -1;
        try {
            if (sqlite != null) {

                Persona persona = new Persona(vendedor.getNombre(), vendedor.getCalle(), vendedor.getColonia(),
                        vendedor.getTelefono(), vendedor.getEmail());

                ControllerPersona controllerPersona = new ControllerPersona(context, context);

                int noPersona = (int) controllerPersona.addPersona(persona);

                values = new ContentValues();
                values.put(K_VENDEDOR_COMISIONES, vendedor.getComisiones());
                values.put(K_PERSONA_NOPERSONA, noPersona);

                result = sqlite.insert(T_VENDEDOR, null, values);
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("addVendedor", ex.toString());
        }

        return result;
    }

    public Vendedor getVendedor(int noVendedor) {
        Vendedor vendedor = null;
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                cursor = sqlite.rawQuery("SELECT * FROM " + T_VENDEDOR +" INNER JOIN " + T_PERSONA
                                + " ON " + T_VENDEDOR + "." + K_VENDEDOR_NOVENDEDOR + " = " + T_PERSONA + "." + K_PERSONA_NOPERSONA +
                                " WHERE " + K_VENDEDOR_NOVENDEDOR + "=?",
                        new String[]{String.valueOf(noVendedor)});

                if (cursor.moveToFirst()) {

                    vendedor = new Vendedor(cursor.getInt(cursor.getColumnIndex(K_PERSONA_NOPERSONA)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_CALLE)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_COLONIA)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_TELEFONO)),
                            cursor.getString(cursor.getColumnIndex(K_PERSONA_EMAIL)),
                            cursor.getInt(cursor.getColumnIndex(K_VENDEDOR_NOVENDEDOR)),
                            cursor.getDouble(cursor.getColumnIndex(K_VENDEDOR_COMISIONES)));

                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getVendedor", ex.toString());
        }

        return vendedor;

    }

    public boolean updateVendedor(Vendedor vendedor)
    {
        int result = 0;
        try
        {
            if (getVendedor(vendedor.getNoVendedor()) != null)
            {
                conection = new DataBase(context);
                sqlite = conection.getWritableDatabase();

                values = new ContentValues();
                values.put(K_VENDEDOR_COMISIONES, vendedor.getComisiones());

                result = sqlite.update(T_VENDEDOR,
                        values, K_VENDEDOR_COMISIONES + "=?",
                        new String[]{String.valueOf(vendedor.getNoVendedor())});
                sqlite.close();
            }
        }catch (Exception ex){
            Log.e("updateVendedor", ex.toString());
        }

        return result != 0;

    }

    public boolean deleteVendedor(int noVendedor)
    {
        int result = 0;
        try
        {
            if (getVendedor(noVendedor) != null)
            {
                conection = new DataBase(context);
                sqlite = conection.getWritableDatabase();

                result = sqlite.delete(T_VENDEDOR,
                        K_VENDEDOR_NOVENDEDOR + "=?",
                        new String[]{String.valueOf(noVendedor)});
                sqlite.close();
            }
        }catch (Exception ex){
            Log.e("deleteVendedor", ex.toString());
        }

        return result != 0;

    }

    public List<Vendedor> getVendedores() {
        List<Vendedor> vendedores = null;
        Vendedor vendedor;
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                vendedores = new ArrayList<>();
                cursor = sqlite.rawQuery("SELECT * FROM " + T_VENDEDOR +" INNER JOIN " + T_PERSONA
                                + " ON " + T_VENDEDOR + "." + K_VENDEDOR_NOVENDEDOR + " = " + T_PERSONA + "." + K_PERSONA_NOPERSONA,
                            null);

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {

                        vendedor = new Vendedor(cursor.getInt(cursor.getColumnIndex(K_PERSONA_NOPERSONA)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_NOMBRE)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_CALLE)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_COLONIA)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_TELEFONO)),
                                cursor.getString(cursor.getColumnIndex(K_PERSONA_EMAIL)),
                                cursor.getInt(cursor.getColumnIndex(K_VENDEDOR_NOVENDEDOR)),
                                cursor.getDouble(cursor.getColumnIndex(K_VENDEDOR_COMISIONES)));

                        vendedores.add(vendedor);

                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getVendedor", ex.toString());
        }
        return vendedores;
    }

}
