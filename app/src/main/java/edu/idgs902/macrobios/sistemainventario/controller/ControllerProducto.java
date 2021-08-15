package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.DataBase;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ControllerProducto extends DataBase {

    private final Context context;
    private DataBase conn;
    private SQLiteDatabase sqlite;
    private ContentValues values;

    public ControllerProducto(Context context) {
        super(context);
        this.context = context;
    }

    public boolean addProducto(Producto producto) {
        conn = new DataBase(context);
        sqlite = conn.getWritableDatabase();
        long result = -1;
        try {
            if (sqlite != null) {
                values = new ContentValues();
                values.put(K_PRODUCTO_NUPRODUCTO, producto.getNuProducto());
                values.put(K_PRODUCTO_NOMBRE, producto.getNombre());
                values.put(K_PRODUCTO_LINEA, producto.getLinea());
                values.put(K_PRODUCTO_EXISTENCIA, producto.getExistencia());

                result = sqlite.insert(T_PRODUCTO, null, values);
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("addProducto", ex.toString());
        }
        return result != -1;
    }

//    public Producto getProducto(String nuProducto) {
//        Producto producto = null;
//        conn = new DataBase(context);
//        sqlite = conn.getReadableDatabase();
//        Cursor cursor = null;
//        try {
//            if (sqlite != null) {
//                cursor = sqlite.rawQuery("SELECT * FROM " + T_PRODUCTO +
//                                " WHERE " + K_PRODUCTO_NUPRODUCTO + "=?",
//                        new String[]{String.valueOf(nuProducto)});
//                if (cursor.moveToFirst()) {
//                    producto = new Producto(cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)),
//                            cursor.getString(cursor.getColumnIndex(K_PRODUCTO_NUPRODUCTO)),
//                            cursor.getString(cursor.getColumnIndex(K_PRODUCTO_NOMBRE)),
//                            cursor.getString(cursor.getColumnIndex(K_PRODUCTO_LINEA)),
//                            cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_EXISTENCIA)),
//                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_COSTO)),
//                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_PROMEDIO)),
//                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_VENTA_MAYOR)),
//                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_VENTA_MENOR)));
//                }
//                cursor.close();
//                sqlite.close();
//            }
//        } catch (Exception ex) {
//            Log.e("getProductoNu", ex.toString());
//        }
//        return producto;
//    }

    public Producto getProducto(int noProducto) {
        Producto producto = null;
        conn = new DataBase(context);
        sqlite = conn.getReadableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                cursor = sqlite.rawQuery("SELECT * FROM " + T_PRODUCTO +
                                " WHERE " + K_PRODUCTO_NOPRODUCTO + "=?",
                        new String[]{String.valueOf(noProducto)});
                if (cursor.moveToFirst()) {
                    producto = new Producto(cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)),
                            cursor.getString(cursor.getColumnIndex(K_PRODUCTO_NUPRODUCTO)),
                            cursor.getString(cursor.getColumnIndex(K_PRODUCTO_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(K_PRODUCTO_LINEA)),
                            cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_EXISTENCIA)),
                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_COSTO)),
                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_PROMEDIO)),
                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_VENTA_MAYOR)),
                            cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_VENTA_MENOR)));
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getProductoNo", ex.toString());
        }
        return producto;
    }



    public boolean updateProducto(Producto producto) {
        Log.i("productoUpdate",producto.toString());
        int result = 0;
        try {
            if (getProducto(producto.getNoProducto()) != null) {
                conn = new DataBase(context);
                sqlite = conn.getWritableDatabase();

                values = new ContentValues();
                values.put(K_PRODUCTO_NUPRODUCTO, producto.getNuProducto());
                values.put(K_PRODUCTO_NOMBRE, producto.getNombre());
                values.put(K_PRODUCTO_LINEA, producto.getLinea());
                values.put(K_PRODUCTO_EXISTENCIA, producto.getExistencia());
                values.put(K_PRODUCTO_P_COSTO, producto.getP_costo());
                values.put(K_PRODUCTO_P_PROMEDIO, producto.getP_promedio());
                values.put(K_PRODUCTO_P_VENTA_MAYOR, producto.getP_venta_mayor());
                values.put(K_PRODUCTO_P_VENTA_MENOR, producto.getP_venta_menor());


                result = sqlite.update(T_PRODUCTO,
                        values,
                        K_PRODUCTO_NOPRODUCTO + "=?",
                        new String[]{String.valueOf(producto.getNoProducto())});

                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("updateProducto", ex.toString());
        }
        return result != 0;
    }

    public boolean deleteProducto(int noProducto) {
        int result = 0;
        try {
            if (getProducto(noProducto) != null) {
                conn = new DataBase(context);
                sqlite = conn.getWritableDatabase();

                result = sqlite.delete(T_PRODUCTO, K_PRODUCTO_NOPRODUCTO + "=?",
                        new String[]{String.valueOf(noProducto)});
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("deleteProducto", ex.toString());
        }
        return result != 0;
    }

    public List<Producto> getProductos() {
        List<Producto> productos = null;
        conn = new DataBase(context);
        sqlite = conn.getWritableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                productos = new ArrayList<>();
                cursor = sqlite.rawQuery("SELECT * FROM " + T_PRODUCTO
                        + " ORDER BY " + K_PRODUCTO_NUPRODUCTO, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Producto productoAux = new Producto(cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_NOPRODUCTO)),
                                cursor.getString(cursor.getColumnIndex(K_PRODUCTO_NUPRODUCTO)),
                                cursor.getString(cursor.getColumnIndex(K_PRODUCTO_NOMBRE)),
                                cursor.getString(cursor.getColumnIndex(K_PRODUCTO_LINEA)),
                                cursor.getInt(cursor.getColumnIndex(K_PRODUCTO_EXISTENCIA)),
                                cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_COSTO)),
                                cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_PROMEDIO)),
                                cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_VENTA_MAYOR)),
                                cursor.getDouble(cursor.getColumnIndex(K_PRODUCTO_P_VENTA_MENOR)));

                        productos.add(productoAux);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getProductos", ex.toString());
        }
        return productos;
    }

    public int getCuenta(String letra) {
        int result = -1;
        conn = new DataBase(context);
        sqlite = conn.getReadableDatabase();
        Cursor cursor = null;
        try {
            if (sqlite != null) {
                cursor = sqlite.rawQuery("SELECT " + K_PRODUCTO_NUPRODUCTO + " FROM " + T_PRODUCTO +
                        " WHERE " + K_PRODUCTO_NUPRODUCTO + " LIKE '" + letra + "%'", null);
                if (cursor.moveToFirst()) {
                    String nuProducto = cursor.getString(cursor.getColumnIndex(K_PRODUCTO_NUPRODUCTO));
                    result = Integer.parseInt(nuProducto.substring(nuProducto.indexOf(letra) + 1)) + 1;
                } else {
                    result = 1;
                }
            }
        } catch (Exception ex) {
            Log.e("getCuenta", ex.toString());
        }
        return result;
    }
}
