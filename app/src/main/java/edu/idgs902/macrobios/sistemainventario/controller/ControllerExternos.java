package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.DataBase;
import edu.idgs902.macrobios.sistemainventario.model.Externo;

public class ControllerExternos extends DataBase {

    private final Context context;
    private DataBase conection;
    private SQLiteDatabase sqlite;
    private ContentValues values;


    public ControllerExternos(Context context, Context context1) {
        super(context);
        this.context = context1;
    }

    public long addExterno(Externo externo) {
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        long result = -1;
        try {
            if (sqlite != null) {
                values = new ContentValues();
                values.put(K_PERSONA_NOPERSONA, externo.getNoPersona());
                values.put(K_EXTERNO_TIPO, externo.getTipo());
                values.put(K_EXTERNO_RFC, externo.getRfc());
                values.put(K_EXTERNO_CIUDAD, externo.getCiudad());
                values.put(K_EXTERNO_SALDO, externo.getSaldo());

                result = sqlite.insert(T_EXTERNO, null, values);
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("addExterno", ex.toString());
        }
        return result;
    }

    public Externo getExterno(int noExterno) {
        Externo externo = null;
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                cursor = sqlite.rawQuery("SELECT * FROM " + T_EXTERNO + " WHERE " + K_EXTERNO_NOEXTERNO + "=?",
                        new String[]{String.valueOf(noExterno)});
                if (cursor.moveToFirst()) {
                    externo = new Externo(cursor.getInt(cursor.getColumnIndex(K_EXTERNO_NOEXTERNO)),
                            cursor.getInt(cursor.getColumnIndex(K_EXTERNO_TIPO)),
                            cursor.getString(cursor.getColumnIndex(K_EXTERNO_RFC)),
                            cursor.getString(cursor.getColumnIndex(K_EXTERNO_CIUDAD)),
                            cursor.getDouble(cursor.getColumnIndex(K_EXTERNO_SALDO)));

                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getExterno", ex.toString());
        }
        return externo;
    }


    public boolean updateExterno(Externo externo) {
        int result = 0;
        try {
            if (getExterno(externo.getNoExterno()) != null) {
                conection = new DataBase(context);
                sqlite = conection.getWritableDatabase();

                values = new ContentValues();
                values.put(K_EXTERNO_RFC, externo.getRfc());
                values.put(K_EXTERNO_CIUDAD, externo.getCiudad());
                values.put(K_EXTERNO_SALDO, externo.getSaldo());

                result = sqlite.update(T_EXTERNO,
                        values, K_EXTERNO_NOEXTERNO + "=?",
                        new String[]{String.valueOf(externo.getNoExterno())});
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("updateExterno", ex.toString());
        }
        return result != 0;
    }

    public boolean deleteExterno(int noExterno) {
        int result = 0;
        try {
            if (getExterno(noExterno) != null) {
                conection = new DataBase(context);
                sqlite = conection.getWritableDatabase();

                result = sqlite.delete(T_EXTERNO,
                        K_EXTERNO_NOEXTERNO + "=?",
                        new String[]{String.valueOf(noExterno)});
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("deleteExterno", ex.toString());
        }
        return result != 0;
    }

    public List<Externo> getExternos() {
        List<Externo> externos = null;
        conection = new DataBase(context);
        sqlite = conection.getWritableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                externos = new ArrayList<>();
                cursor = sqlite.rawQuery("SELECT * FROM " + T_EXTERNO, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Externo externo = new Externo(cursor.getInt(cursor.getColumnIndex(K_EXTERNO_NOEXTERNO)),
                                cursor.getInt(cursor.getColumnIndex(K_EXTERNO_TIPO)),
                                cursor.getString(cursor.getColumnIndex(K_EXTERNO_RFC)),
                                cursor.getString(cursor.getColumnIndex(K_EXTERNO_CIUDAD)),
                                cursor.getDouble(cursor.getColumnIndex(K_EXTERNO_SALDO)),
                                cursor.getInt(cursor.getColumnIndex(K_PERSONA_NOPERSONA)));
                        externos.add(externo);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getExtenos", ex.toString());
        }
        return externos;
    }

}
