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
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;
import edu.idgs902.macrobios.sistemainventario.model.Venta;

public class ControllerVenta extends DataBase {

    private final Context context;
    private DataBase conn;
    private SQLiteDatabase sqlite;
    private ContentValues values;

    public ControllerVenta(Context context) {
        super(context);
        this.context = context;
    }

    public boolean addVenta(Venta venta) {
        long result = -1;
        try {
            conn = new DataBase(context);
            sqlite = conn.getWritableDatabase();
            if (sqlite != null) {
                values = new ContentValues();
                values.put(K_VENTA_NOEXTERNOCLIENTE, venta.getExterno_cliente().getNoExterno());
                values.put(K_VENTA_NOVENDEDOR, venta.getVendedor().getNoVendedor());
                values.put(K_VENTA_FECHA, venta.getFecha());
                values.put(K_VENTA_COMISION, venta.getComision());
                values.put(K_VENTA_F_R, venta.getF_r());
                values.put(K_VENTA_F_R_NO, venta.getF_r_no());
                values.put(K_VENTA_TOTAL_PARES, venta.getTotal_pares());
                values.put(K_VENTA_SUMA, venta.getSuma());
                values.put(K_VENTA_IVA, venta.getIva());
                values.put(K_VENTA_TOTAL, venta.getTotal_venta());

                result = sqlite.insert(T_VENTA, null, values);
                sqlite.close();

                if (result != -1) {
                    ControllerDetalleVenta cdv = new ControllerDetalleVenta(context);
                    ControllerExternos ce = new ControllerExternos(context, context);

                    double comision = venta.getTotal_venta() * (venta.getComision() * 0.01);

                    boolean addDetalles = cdv.addDetallesV((int) result, venta.getDetallesVenta());
                    boolean updateSaldo = ce.updateSaldo(venta.getExterno_cliente().getNoExterno(), comision);

                    if (!addDetalles ||
                            !updateSaldo) {
                        result = -1;
                    }
                }

                // TODO Añadir el la comision al vendedor
            }
        } catch (Exception ex) {
            Log.e("addVenta", ex.toString());
        }
        return result != -1;
    }

    public List<Venta> getVentas() {
        List<Venta> ventas = new ArrayList<>();
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_VENTA, null);
                if (cursor.moveToFirst()) {
                    ControllerExternos ce = new ControllerExternos(context, context);
//                    ControllerVendedor cv = new ControllerVendedor(context);
                    ControllerDetalleVenta cdv = new ControllerDetalleVenta(context);
                    while (!cursor.isAfterLast()) {
                        Externo clienteAux = ce.getExterno(cursor.getInt(cursor.getColumnIndex(K_VENTA_NOEXTERNOCLIENTE)));
//                        Vendedor vendedorAux = cv.getVendedor(cursor.getInt(cursor.getColumnIndex(K_VENTA_NOVENDEDOR)));
                        Vendedor vendedorAux = new Vendedor(1, "Alejandro",
                                "Calle Vendedor", "Colonia Vendedor", "477 1234567",
                                "vendedor@vendedor.com", 1, 0);
                        List<DetalleVenta> detalleVentaAux = cdv.getDetallesVenta(cursor.getInt(cursor.getColumnIndex(K_VENTA_NOVENTA)));
                        Venta ventaAux = new Venta(cursor.getInt(cursor.getColumnIndex(K_VENTA_NOVENTA)),
                                clienteAux,
                                vendedorAux,
                                cursor.getString(cursor.getColumnIndex(K_VENTA_FECHA)),
                                cursor.getInt(cursor.getColumnIndex(K_VENTA_COMISION)),
                                cursor.getString(cursor.getColumnIndex(K_VENTA_F_R)),
                                cursor.getInt(cursor.getColumnIndex(K_VENTA_F_R_NO)),
                                cursor.getInt(cursor.getColumnIndex(K_VENTA_TOTAL_PARES)),
                                cursor.getDouble(cursor.getColumnIndex(K_VENTA_SUMA)),
                                cursor.getDouble(cursor.getColumnIndex(K_VENTA_IVA)),
                                cursor.getDouble(cursor.getColumnIndex(K_VENTA_TOTAL)),
                                detalleVentaAux
                        );

                        ventas.add(ventaAux);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getVentas", ex.toString());
            ex.printStackTrace();
        }
        return ventas;
    }


    public int getNoFactura() {
        long result = 17001;
        conn = new DataBase(context);
        sqlite = conn.getReadableDatabase();
        try {
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT " + K_VENTA_NOVENTA + " FROM " + T_VENTA +
                        " ORDER BY " + K_VENTA_NOVENTA, null);
                if (cursor.moveToLast()) {
                    result = result + cursor.getInt(cursor.getColumnIndex(K_VENTA_NOVENTA));
                }
                cursor.close();
            }
        } catch (Exception ex) {
            Log.e("getNoFactura", ex.toString());
        }
        return (int) result;
    }

    public int getNoVenta() {
        long result = 1;
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT " + K_VENTA_NOVENTA + " FROM " + T_VENTA +
                        " ORDER BY " + K_VENTA_NOVENTA, null);
                if (cursor.moveToLast()) {
                    result = cursor.getInt(cursor.getColumnIndex(K_VENTA_NOVENTA)) + 1;
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getNoVenta", ex.toString());
        }
        return (int) result;
    }
}
