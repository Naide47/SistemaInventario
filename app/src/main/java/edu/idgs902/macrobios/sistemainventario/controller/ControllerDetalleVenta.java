package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_DETALLEVENTA + " WHERE "
                        + K_VENTA_NOVENTA + "=?", new String[]{String.valueOf(noVenta)});
                if (cursor.moveToFirst()) {
                    ControllerProducto cp = new ControllerProducto(context);
                    while (!cursor.isAfterLast()) {
                        Producto productoAux = cp.getProducto(cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)));
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

    public DetalleVenta getDetalleIndividual(int noDetalle) {
        DetalleVenta detalleVenta = null;
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_DETALLEVENTA +
                    " WHERE " + K_DETALLEVENTA_NODETALLEVENTA + "=?", new String[]{String.valueOf(noDetalle)});
            if (cursor.moveToFirst()) {
                ControllerProducto cp = new ControllerProducto(context);
                Producto productoAux = cp.getProducto(cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)));
                detalleVenta = new DetalleVenta(cursor.getInt(cursor.getColumnIndex(K_DETALLEVENTA_NODETALLEVENTA)),
                        productoAux,
                        cursor.getInt(cursor.getColumnIndex(K_DETALLEVENTA_CANTIDAD)),
                        cursor.getDouble(cursor.getColumnIndex(K_DETALLEVENTA_PRECIOVENTA)));
            }
            cursor.close();
            sqlite.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return detalleVenta;
    }

    public boolean updateDetallesVenta(List<DetalleVenta> detallesVenta) {
        boolean result = true;
        try {
            if (detallesVenta.size() != 0) {
                DataBase conn = new DataBase(context);
                SQLiteDatabase sqlite = conn.getWritableDatabase();

                ControllerProducto cp = new ControllerProducto(context);

                for (DetalleVenta detalleVenta : detallesVenta) {
                    DetalleVenta detalleVentaAnterior = getDetalleIndividual(detalleVenta.getNoDetalleVenta());

                    values = new ContentValues();

                    values.put(K_DETALLEVENTA_CANTIDAD, detalleVenta.getCantidad_producto());
                    values.put(K_DETALLEVENTA_PRECIOVENTA, detalleVenta.getPrecio_venta());

                    result = (sqlite.update(T_DETALLEVENTA, values, K_DETALLEVENTA_NODETALLEVENTA + "=?",
                            new String[]{String.valueOf(detalleVenta.getNoDetalleVenta())}) == 1);

                    if (result) {
                        DetalleVenta anterior = getDetalleIndividual(detalleVenta.getNoDetalleVenta());
                        cp.restarVenta(anterior.getProducto().getNoProducto(), -anterior.getCantidad_producto());
                        cp.restarVenta(anterior.getProducto().getNoProducto(), detalleVenta.getCantidad_producto());
                    } else {
                        break;
                    }

                    int nuevaCantidad = -(detalleVentaAnterior.getCantidad_producto() - detalleVenta.getCantidad_producto());
                    cp.restarVenta(detalleVenta.getProducto().getNoProducto(), nuevaCantidad);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e("updateDet","" + result);
        return result;
    }

    //    public boolean deleteDetallesVenta(int noVenta) {
//        int result = 0;
//        try {
//            List<DetalleVenta> detallesVenta = getDetallesVenta(noVenta);
//            if (detallesVenta.size() != 0) {
//                conn = new DataBase(context);
//                sqlite = conn.getWritableDatabase();
//
//                ControllerProducto cp = new ControllerProducto(context);
//                for (DetalleVenta detalleVenta : detallesVenta) {
//                    result = sqlite.delete(T_DETALLEVENTA,
//                            K_DETALLEVENTA_NODETALLEVENTA + "=?",
//                            new String[]{String.valueOf(detalleVenta.getNoDetalleVenta())});
//
//                    if (result != 0) {
//                        cp.restarVenta(detalleVenta.getProducto().getNoProducto(), -(detalleVenta.getCantidad_producto()));
//                    } else {
//                        break;
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return result != 0;
//    }
    public boolean deleteDetallesVenta(int noVenta) {
        int result = 0;
        try {
            if (getDetallesVenta(noVenta) != null) {
                conn = new DataBase(context);
                sqlite = conn.getWritableDatabase();

                result = sqlite.delete(T_DETALLEVENTA,
                        K_VENTA_NOVENTA + "=?",
                        new String[]{String.valueOf(noVenta)});
            }
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
        return result != 0;
    }
}
