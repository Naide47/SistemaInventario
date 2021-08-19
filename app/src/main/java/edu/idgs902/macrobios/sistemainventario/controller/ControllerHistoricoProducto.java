package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.DataBase;
import edu.idgs902.macrobios.sistemainventario.model.HistoricoProducto;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ControllerHistoricoProducto extends DataBase {

    private final Context context;
    private DataBase conn;
    private SQLiteDatabase sqlite;
    private ContentValues values;

    public ControllerHistoricoProducto(Context context) {
        super(context);
        this.context = context;
    }

    public boolean addHistoricos(HistoricoProducto historicoProducto) {
        long result = -1;
        try {
            conn = new DataBase(context);
            sqlite = conn.getWritableDatabase();
            if (sqlite != null) {
                values = new ContentValues();
                values.put(K_COMPRA_NOCOMPRA, historicoProducto.getNoCompra());
                values.put(K_PRODUCTO_NOPRODUCTO, historicoProducto.getNoProducto());
                values.put(K_HISTORICOPRODUCTO_COSTO, historicoProducto.getCosto());
                values.put(K_HISTORICOPRODUCTO_CANTIDAD, historicoProducto.getCantidad());

                result = sqlite.insert(T_HISTORICOPRODUCTO, null, values);
                sqlite.close();

                ControllerProducto cp = new ControllerProducto(context);
                cp.actualizarDatos(historicoProducto.getNoProducto(), historicoProducto.getCosto(), historicoProducto.getCantidad());
                cp.actualizarPromedio(historicoProducto.getNoProducto(), getHistoricosPorProducto(historicoProducto.getNoProducto()));
            }
        } catch (Exception ex) {
            Log.e("addHistoricos", ex.toString());
        }
        return result != -1;
    }

    public List<HistoricoProducto> getHistoricosPorProducto(int noProducto) {
        List<HistoricoProducto> historicos = null;
        try {
            conn = new DataBase(context);
            sqlite = conn.getWritableDatabase();
            if (sqlite != null) {
                historicos = new ArrayList<>();
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_HISTORICOPRODUCTO
                        + " WHERE " + K_PRODUCTO_NOPRODUCTO + "=?", new String[]{String.valueOf(noProducto)});
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        HistoricoProducto historicoProductoAux = new HistoricoProducto(cursor.getInt(cursor.getColumnIndex(K_HISTORICOPRODUCTO_NOHISTORICO)),
                                cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA)),
                                cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)),
                                cursor.getDouble(cursor.getColumnIndex(K_HISTORICOPRODUCTO_COSTO)),
                                cursor.getInt(cursor.getColumnIndex(K_HISTORICOPRODUCTO_CANTIDAD)));

                        historicos.add(historicoProductoAux);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getHistPorProducto", ex.toString());
        }
        return historicos;
    }

    public HistoricoProducto getHistoricoIndividual(int noCompra, int noProducto) {
        HistoricoProducto historicoProducto = null;
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_HISTORICOPRODUCTO
                                + " WHERE " + K_COMPRA_NOCOMPRA + "=? AND " +
                                K_PRODUCTO_NOPRODUCTO + "=?",
                        new String[]{String.valueOf(noCompra), String.valueOf(noProducto)});
                if (cursor.moveToFirst()) {
                    historicoProducto = new HistoricoProducto(cursor.getInt(cursor.getColumnIndex(K_HISTORICOPRODUCTO_NOHISTORICO)),
                            cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA)),
                            cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)),
                            cursor.getDouble(cursor.getColumnIndex(K_HISTORICOPRODUCTO_COSTO)),
                            cursor.getInt(cursor.getColumnIndex(K_HISTORICOPRODUCTO_CANTIDAD)));

                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getHistInvidvidual", ex.toString());
        }
        return historicoProducto;
    }

    public boolean updateHistorico(int noCompra, int noProducto, int cantidad, double costo, int noHistUltimo) {
        boolean resultado = true;
        try {
            HistoricoProducto historicoProducto = getHistoricoIndividual(noCompra, noProducto);
            Log.e("updateHistorico", "historicoProducto != null " + (historicoProducto != null));
            if (historicoProducto != null) {
                conn = new DataBase(context);
                sqlite = conn.getWritableDatabase();

                values = new ContentValues();
                values.put(K_HISTORICOPRODUCTO_COSTO, costo);
                values.put(K_HISTORICOPRODUCTO_CANTIDAD, cantidad);

                resultado = sqlite.update(T_HISTORICOPRODUCTO, values,
                        K_HISTORICOPRODUCTO_NOHISTORICO + "=?", new String[]{String.valueOf(historicoProducto.getNoHistorico())}) == 1;

                Log.e("updateHistorico", "resultado " + (resultado));

                sqlite.close();

                if (resultado) {
                    ControllerProducto cp = new ControllerProducto(context);
                    cp.actualizarPromedio(noProducto, getHistoricosPorProducto(noProducto));
                    if (historicoProducto.getNoHistorico() == noHistUltimo) {
                        Log.e("updateHistorico", "historicoProducto.getNoHistorico() == noHistUltimo" + (historicoProducto.getNoHistorico() == noHistUltimo));
                        int nuevaCantidad = -(cp.getProducto(historicoProducto.getNoProducto()).getExistencia() - historicoProducto.getCantidad()  - cantidad);
                        cp.actualizarDatos(noProducto, costo, nuevaCantidad);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

//    public boolean deleteHistoricos(int noCompra, int noProducto, SQLiteDatabase sqlite) throws Exception {
//        int result = 0;
//
//        HistoricoProducto historicoProducto = getHistoricoIndividual(noCompra, noProducto);
//        ControllerProducto cp = new ControllerProducto(context);
//        int cantidad = -historicoProducto.getCantidad();
//        double costo = -historicoProducto.getCosto();
//        cp.actualizarDatos(noProducto, costo, cantidad);
//
//        result = sqlite.delete(T_HISTORICOPRODUCTO,
//                K_HISTORICOPRODUCTO_NOHISTORICO + "=?",
//                new String[]{String.valueOf(historicoProducto.getNoHistorico())});
//
//        cp.actualizarPromedio(noProducto, getHistoricosPorProducto(noProducto));
//
//        return result != 0;
//    }
}
