package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.DataBase;
import edu.idgs902.macrobios.sistemainventario.model.DetalleVenta;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ControllerDetalleVenta extends DataBase {

    private final Context context;
    private DataBase conn;
    private SQLiteDatabase sqlite;
    private ContentValues values;

    public ControllerDetalleVenta(Context context) {
        super(context);
        this.context = context;
    }

    public boolean addDetallesV(int noVenta, List<DetalleVenta> detallesVenta) {
        boolean result = true;
        try {
            conn = new DataBase(context);
            sqlite = conn.getWritableDatabase();
            if (sqlite != null) {
                ControllerProducto chp = new ControllerProducto(context);
                Log.e("detallesVenta", detallesVenta.size() + "");
                for (DetalleVenta detalleVenta : detallesVenta) {
                    values = new ContentValues();
                    values.put(K_VENTA_NOVENTA, noVenta);
                    values.put(K_PRODUCTO_NOPRODUCTO, detalleVenta.getProducto().getNoProducto());
                    values.put(K_DETALLEVENTA_CANTIDAD, detalleVenta.getCantidad_producto());
                    values.put(K_DETALLEVENTA_PRECIOVENTA, detalleVenta.getPrecio_venta());

                    boolean restarVenta = chp.restarVenta(detalleVenta.getProducto().getNoProducto(), detalleVenta.getCantidad_producto());
                    Log.e("restarVenta", restarVenta + "");

                    if (sqlite.insert(T_DETALLEVENTA, null, values) == -1 &&
                            !restarVenta) {
                        result = false;
                        break;
                    }
                }
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("addDetallesV", ex.toString());
            ex.printStackTrace();
        }
        return result;
    }

    public List<DetalleVenta> getDetallesVenta(int noVenta) {
        List<DetalleVenta> detalleVentas = new ArrayList<>();
        try {
            conn = new DataBase(context);
            sqlite = conn.getWritableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_DETALLEVENTA + " WHERE "
                        + K_VENTA_NOVENTA + "=?", new String[]{String.valueOf(noVenta)});
                if (cursor.moveToFirst()) {
                    ControllerProducto cp = new ControllerProducto(context);
                    while (!cursor.isAfterLast()) {
                        Producto productoAux = cp.getProducto(noVenta);
                        DetalleVenta detalleVentaAux = new DetalleVenta(cursor.getInt(cursor.getColumnIndex(K_DETALLEVENTA_NODETALLEVENTA)),
                                productoAux,
                                cursor.getInt(cursor.getColumnIndex(K_DETALLEVENTA_CANTIDAD)),
                                cursor.getDouble(cursor.getColumnIndex(K_DETALLEVENTA_PRECIOVENTA)));
                        detalleVentas.add(detalleVentaAux);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getDetallesVenta", ex.toString());

        }
        return detalleVentas;
    }
}
