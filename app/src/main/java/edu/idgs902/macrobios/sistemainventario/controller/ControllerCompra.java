package edu.idgs902.macrobios.sistemainventario.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.Compra;
import edu.idgs902.macrobios.sistemainventario.model.DataBase;
import edu.idgs902.macrobios.sistemainventario.model.DetalleCompra;
import edu.idgs902.macrobios.sistemainventario.model.Externo;

public class ControllerCompra extends DataBase {

    private final Context context;
    private DataBase conn;
    private SQLiteDatabase sqlite;
    private ContentValues values;

    public ControllerCompra(Context context) {
        super(context);
        this.context = context;
    }

    public boolean addCompra(Compra compra) {
        conn = new DataBase(context);
        sqlite = conn.getWritableDatabase();
        long result = -1;
        try {
            if (sqlite != null) {
                values = new ContentValues();
                values.put(K_COMPRA_NOEXTERNOPROVEEDOR, compra.getExterno_proveedor().getNoExterno());
                values.put(K_COMPRA_FECHA, compra.getFecha());
                values.put(K_COMPRA_F_R, compra.getF_r());
                values.put(K_COMPRA_F_R_NO, compra.getF_r_no());
                values.put(K_COMPRA_TOTAL_PARES, compra.getTotal_pares());
                values.put(K_COMPRA_SUMA, compra.getSuma());
                values.put(K_COMPRA_IVA, compra.getIva());
                values.put(K_COMPRA_TOTAL, compra.getTotal_compra());

                result = sqlite.insert(T_COMPRA, null, values);
                sqlite.close();

                if (result != -1) {
                    ControllerDetalleCompra cdp = new ControllerDetalleCompra(context);
                    ControllerExternos ce = new ControllerExternos(context, context);

                    boolean addDetallesC = cdp.addDetallesC((int) result, compra.getDetallesCompra());
                    boolean updateSaldo = ce.updateSaldo(compra.getExterno_proveedor().getNoExterno(), compra.getTotal_compra());

                    if (!addDetallesC ||
                            !updateSaldo) {
                        result = -1;
                    }

                }
            }

        } catch (Exception ex) {
            Log.e("addCompra", ex.toString());
        }
        return result != -1;
    }

    public Compra getCompra(int noCompra) {
        Compra compra = null;
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_COMPRA +
                        " WHERE " + K_COMPRA_NOCOMPRA + "=?", new String[]{String.valueOf(noCompra)});
                if (cursor.moveToFirst()) {
                    ControllerExternos ce = new ControllerExternos(context, context);
                    ControllerDetalleCompra cdc = new ControllerDetalleCompra(context);

                    Externo externoAux = ce.getExternoCompleto(cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOEXTERNOPROVEEDOR)));
                    List<DetalleCompra> detallesCompraAux = cdc.getDetallesCompra(cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA)));

                    compra = new Compra(cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA)),
                            externoAux,
                            cursor.getString(cursor.getColumnIndex(K_COMPRA_FECHA)),
                            cursor.getString(cursor.getColumnIndex(K_COMPRA_F_R)),
                            cursor.getInt(cursor.getColumnIndex(K_COMPRA_F_R_NO)),
                            cursor.getInt(cursor.getColumnIndex(K_COMPRA_TOTAL_PARES)),
                            cursor.getDouble(cursor.getColumnIndex(K_COMPRA_SUMA)),
                            cursor.getDouble(cursor.getColumnIndex(K_COMPRA_IVA)),
                            cursor.getDouble(cursor.getColumnIndex(K_COMPRA_TOTAL)),
                            detallesCompraAux);
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return compra;
    }

    public boolean updateCompra(Compra nuevaCompra) {
        int result = 0;
        try {
            Compra compraAnterior = getCompra(nuevaCompra.getNoCompra());

            if (compraAnterior != null) {
                conn = new DataBase(context);
                sqlite = conn.getWritableDatabase();

                values = new ContentValues();
                values.put(K_COMPRA_NOEXTERNOPROVEEDOR, nuevaCompra.getExterno_proveedor().getNoExterno());
                values.put(K_COMPRA_FECHA, nuevaCompra.getFecha());
                values.put(K_COMPRA_F_R, nuevaCompra.getF_r());
                values.put(K_COMPRA_TOTAL_PARES, nuevaCompra.getTotal_pares());
                values.put(K_COMPRA_SUMA, nuevaCompra.getSuma());
                values.put(K_COMPRA_IVA, nuevaCompra.getIva());
                values.put(K_COMPRA_TOTAL, nuevaCompra.getTotal_compra());

                result = sqlite.update(T_COMPRA,
                        values,
                        K_COMPRA_NOCOMPRA + "=?",
                        new String[]{String.valueOf(nuevaCompra.getNoCompra())});

                sqlite.close();


                if (result == 1) {

                    ControllerDetalleCompra cdc = new ControllerDetalleCompra(context);
                    ControllerExternos ce = new ControllerExternos(context, context);

                    boolean updateDetalles = cdc.updateDetalleCompra(nuevaCompra.getNoCompra(), nuevaCompra.getDetallesCompra());

//                    Externo externo = ce.getExternoCompleto(compraAnterior.getExterno_proveedor().getNoExterno());
//                    double saldoAnterior = externo.getSaldo() - compraAnterior.getTotal_compra();
//                    boolean updateSaldoAnterior = ce.updateSaldo(externo.getNoExterno(), saldoAnterior);
//
//                    externo = ce.getExternoCompleto(nuevaCompra.getExterno_proveedor().getNoExterno());
//                    double saldoActual = externo.getSaldo() + nuevaCompra.getTotal_compra();
//                    boolean updateSaldoNuevo = ce.updateSaldo(externo.getNoExterno(), saldoActual);

//                    if (!updateDetalles || !updateSaldoAnterior || !updateSaldoNuevo) {
//                        result = 0;
//                    }
                    if (!updateDetalles) {
                        result = 0;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result != 0;
    }

    public boolean deleteCompra(int noCompra) {
        int result = 0;
        try {
            if (getCompra(noCompra) != null) {
                conn = new DataBase(context);
                sqlite = conn.getWritableDatabase();

                result = sqlite.delete(T_COMPRA,
                        K_COMPRA_NOCOMPRA + "=?",
                        new String[]{String.valueOf(noCompra)});

                sqlite.close();

                if (result == 1) {
                    ControllerDetalleCompra cdc = new ControllerDetalleCompra(context);
                    boolean deleteDetalles = cdc.deleteDetalles(noCompra);
                    if (!deleteDetalles) {
                        result = 0;
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result != 0;
    }


    public List<Compra> getCompras() {
        List<Compra> compras = new ArrayList<>();
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT * FROM " + T_COMPRA, null);

                if (cursor.moveToFirst()) {
                    ControllerExternos ce = new ControllerExternos(context, context);
                    ControllerDetalleCompra cdc = new ControllerDetalleCompra(context);
                    while (!cursor.isAfterLast()) {
                        Externo externoAux = ce.getExternoCompleto(cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOEXTERNOPROVEEDOR)));
                        List<DetalleCompra> detallesCompraAux = cdc.getDetallesCompra(cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA)));

                        Compra compraAux = new Compra(cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA)),
                                externoAux,
                                cursor.getString(cursor.getColumnIndex(K_COMPRA_FECHA)),
                                cursor.getString(cursor.getColumnIndex(K_COMPRA_F_R)),
                                cursor.getInt(cursor.getColumnIndex(K_COMPRA_F_R_NO)),
                                cursor.getInt(cursor.getColumnIndex(K_COMPRA_TOTAL_PARES)),
                                cursor.getDouble(cursor.getColumnIndex(K_COMPRA_SUMA)),
                                cursor.getDouble(cursor.getColumnIndex(K_COMPRA_IVA)),
                                cursor.getDouble(cursor.getColumnIndex(K_COMPRA_TOTAL)),
                                detallesCompraAux);

                        compras.add(compraAux);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getCompras", ex.toString());
        }
        return compras;
    }

    public int getNoCompra() {
        long result = 1;
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT " + K_COMPRA_NOCOMPRA + " FROM " + T_COMPRA +
                        " ORDER BY " + K_COMPRA_NOCOMPRA, null);
                if (cursor.moveToLast()) {
                    result = cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA)) + 1;
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getNoCompra", ex.toString());
        }
        return (int) result;
    }

    public int getNoFactura() {
        long result = 16001;
        try {
            conn = new DataBase(context);
            sqlite = conn.getReadableDatabase();
            if (sqlite != null) {
                Cursor cursor = sqlite.rawQuery("SELECT " + K_COMPRA_NOCOMPRA + " FROM " + T_COMPRA +
                        " ORDER BY " + K_COMPRA_NOCOMPRA, null);
                if (cursor.moveToLast()) {
                    result = result + cursor.getInt(cursor.getColumnIndex(K_COMPRA_NOCOMPRA));
                }
                cursor.close();
                sqlite.close();
            }
        } catch (Exception ex) {
            Log.e("getNoFactura", ex.toString());
        }
        return (int) result;
    }
}
