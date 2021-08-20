package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.DataBase;
import edu.idgs902.macrobios.sistemainventario.model.DetalleCompra;
import edu.idgs902.macrobios.sistemainventario.model.HistoricoProducto;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ControllerDetalleCompra extends DataBase {

    private final Context context;
    private DataBase conn;
    private SQLiteDatabase sqlite;
    private ContentValues values;

    public ControllerDetalleCompra(Context context) {
        super(context);
        this.context = context;
    }

    public boolean addDetallesC(int noCompra, List<DetalleCompra> detallesCompra) {
        boolean result = true;
        try {
            conn = new DataBase(context);
            sqlite = conn.getWritableDatabase();
            if (sqlite != null) {
                ControllerHistoricoProducto chp = new ControllerHistoricoProducto(context);
                for (DetalleCompra detalleCompra : detallesCompra) {
                    values = new ContentValues();
                    values.put(K_COMPRA_NOCOMPRA, noCompra);
                    values.put(K_PRODUCTO_NOPRODUCTO, detalleCompra.getProducto().getNoProducto());
                    values.put(K_DETALLECOMPRA_CANTIDAD, detalleCompra.getCantidad_producto());
                    values.put(K_DETALLECOMPRA_PRECIOCOSTO, detalleCompra.getPrecio_compra());

                    boolean insert = (sqlite.insert(T_DETALLECOMPRA, null, values) != -1);
                    boolean addHistoricos = chp.addHistoricos(new HistoricoProducto(noCompra,
                            detalleCompra.getProducto().getNoProducto(),
                            detalleCompra.getPrecio_compra(),
                            detalleCompra.getCantidad_producto()));

                    if (!insert && !addHistoricos) {
                        result = false;
                        break;
                    }
                }
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("addDetallesC", ex.toString());
        }
        return result;
    }

    public List<DetalleCompra> getDetallesCompra(int noCompra) {
        List<DetalleCompra> detallesCompra = new ArrayList<>();
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_DETALLECOMPRA
                        + " WHERE " + K_COMPRA_NOCOMPRA + "=?", new String[]{String.valueOf(noCompra)});
                if (cursor.moveToFirst()) {
                    ControllerProducto cp = new ControllerProducto(context);
                    while (!cursor.isAfterLast()) {
                        Producto productoAux = cp.getProducto(cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)));

                        DetalleCompra detalleCompraAux = new DetalleCompra(cursor.getInt(cursor.getColumnIndex(K_DETALLECOMPRA_NODETALLECOMPRA)),
                                productoAux,
                                cursor.getInt(cursor.getColumnIndex(K_DETALLECOMPRA_CANTIDAD)),
                                cursor.getDouble(cursor.getColumnIndex(K_DETALLECOMPRA_PRECIOCOSTO)));

                        detallesCompra.add(detalleCompraAux);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getDetallesCompra", ex.toString());
        }
        return detallesCompra;
    }

    public boolean updateDetalleCompra(int noCompra, List<DetalleCompra> detallesCompra) {
        boolean result = true;
        try {

            if (detallesCompra.size() != 0) {
                conn = new DataBase(context);
                sqlite = conn.getWritableDatabase();
                ControllerHistoricoProducto chp = new ControllerHistoricoProducto(context);

                for (DetalleCompra detalleCompra : detallesCompra) {
                    values = new ContentValues();
                    values.put(K_DETALLECOMPRA_PRECIOCOSTO, detalleCompra.getPrecio_compra());
                    values.put(K_DETALLECOMPRA_CANTIDAD, detalleCompra.getCantidad_producto());

                    boolean update = sqlite.update(T_DETALLECOMPRA, values,
                            K_DETALLECOMPRA_NODETALLECOMPRA + "=?",
                            new String[]{String.valueOf(detalleCompra.getNoDetalleCompra())}) != 0;

                    List<HistoricoProducto> historicoProductos = chp.getHistoricosPorProducto(detalleCompra.getProducto().getNoProducto());
                    boolean updateHistorico = chp.updateHistorico(
                            noCompra,
                            detalleCompra.getProducto().getNoProducto(),
                            detalleCompra.getCantidad_producto(),
                            detalleCompra.getPrecio_compra(),
                            historicoProductos.get(historicoProductos.size() - 1).getNoHistorico()
                    );

                    if (!update && !updateHistorico) {
                        result = false;
                        break;
                    }
                }
                sqlite.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean deleteDetalles(int noCompra) {
        int result = 0;
        try {
            if (getDetallesCompra(noCompra) != null) {
                conn = new DataBase(context);
                sqlite = conn.getReadableDatabase();

                result = sqlite.delete(T_DETALLECOMPRA,
                        K_COMPRA_NOCOMPRA + "=?",
                        new String[]{String.valueOf(noCompra)});
                sqlite.close();
            }
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
        return result != 0;
    }
}
