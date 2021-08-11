package edu.idgs902.macrobios.sistemainventario.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    // Base de datos y version
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Inventario";

    // Tabla Persona
    private static final String T_PERSONA = "Persona";
    private static final String K_PERSONA_NOPERSONA = "noPersona";
    private static final String K_PERSONA_NOMBRE = "nombre";
    private static final String K_PERSONA_CALLE = "calle";
    private static final String K_PERSONA_COLONIA = "colonia";
    private static final String K_PERSONA_TELEFONO = "telefono";
    private static final String K_PERSONA_EMAIL = "email";

    // Tabla Externo
    private static final String T_EXTERNO = "Externo";
    private static final String K_EXTERNO_NOEXTERNO = "noExterno";
    private static final String K_EXTERNO_TIPO = "tipo";
    private static final String K_EXTERNO_RFC = "rfc";
    private static final String K_EXTERNO_CIUDAD = "ciudad";
    private static final String K_EXTERNO_SALDO = "saldo";

    // Tabla Vendedor
    private static final String T_VENDEDOR = "Vendedor";
    private static final String K_VENDEDOR_NOVENDEDOR = "noVendedor";
    private static final String K_VENDEDOR_COMISIONES = "comisiones";

    // Tabla Producto
    private static final String T_PRODUCTO = "Producto";
    private static final String K_PRODUCTO_NOPRODUCTO = "noProducto";
    private static final String K_PRODUCTO_NOMBRE = "nombre";
    private static final String K_PRODUCTO_LINEA = "linea";
    private static final String K_PRODUCTO_EXISTENCIA = "existencia";
    private static final String K_PRODUCTO_P_COSTO = "p_costo";
    private static final String K_PRODUCTO_P_PROMEDIO = "p_promedio";
    private static final String K_PRODUCTO_P_VENTA_MAYOR = "p_venta_mayor";
    private static final String K_PRODUCTO_P_VENTA_MENOR = "p_venta_menor";

    // Tabla Compra
    private static final String T_COMPRA = "Compra";
    private static final String K_COMPRA_NOCOMPRA = "noCompra";
    private static final String K_COMPRA_NOEXTERNOPROVEEDOR = "noExternoProveedor";
    private static final String K_COMPRA_FECHA = "fecha";
    private static final String K_COMPRA_F_R = "f_r";
    private static final String K_COMPRA_F_R_NO = "f_r_no";
    private static final String K_COMPRA_TOTAL_PARES = "total_pares";
    private static final String K_COMPRA_SUMA = "suma";
    private static final String K_COMPRA_IVA = "iva";
    private static final String K_COMPRA_TOTAL = "total";

    // Tabla Venta
    private static final String T_VENTA = "Venta";
    private static final String K_VENTA_NOVENTA = "noVenta";
    private static final String K_VENTA_NOEXTERNOCLIENTE = "noExternoCliente";
    private static final String K_VENTA_NOVENDEDOR = "noVendedor";
    private static final String K_VENTA_FECHA = "fecha";
    private static final String K_VENTA_COMISION = "comision";
    private static final String K_VENTA_F_R = "f_r";
    private static final String K_VENTA_F_R_NO = "f_r_no";
    private static final String K_VENTA_TOTAL_PARES = "total_pares";
    private static final String K_VENTA_SUMA = "suma";
    private static final String K_VENTA_IVA = "iva";
    private static final String K_VENTA_TOTAL = "total";

    // Tabla DetalleCompra
    private static final String T_DETALLECOMPRA = "DetalleCompra";
    private static final String K_DETALLECOMPRA_NODETALLECOMPRA = "noDetalleCompra";
    private static final String K_DETALLECOMPRA_CANTIDAD = "cantidad";
    private static final String K_DETALLECOMPRA_PRECIOCOSTO = "precioCosto";


    // Tabla DetalleVenta
    private static final String T_DETALLEVENTA = "DetalleVenta";
    private static final String K_DETALLEVENTA_NODETALLEVENTA = "noDetalleVenta";
    private static final String K_DETALLEVENTA_CANTIDAD = "cantidad";
    private static final String K_DETALLEVENTA_PRECIOVENTA = "precioVenta";

    // Tabla HistoricoProducto
    private static final String T_HISTORICOPRODUCTO = "HistoricoProducto";
    private static final String K_HISTORICOPRODUCTO_NOHISTORICO = "noHistoricio";
    private static final String K_HISTORICOPRODUCTO_COSTO = "costo";
    private static final String K_HISTORICOPRODUCTO_CANTIDAD = "cantidad";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + T_PERSONA + " (" +
                K_PERSONA_NOPERSONA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_PERSONA_NOMBRE + " TEXT," +
                K_PERSONA_CALLE + " TEXT," +
                K_PERSONA_COLONIA + " TEXT," +
                K_PERSONA_TELEFONO + " TEXT," +
                K_PERSONA_EMAIL + " TEXT)");
        db.execSQL("CREATE TABLE " + T_EXTERNO + " (" +
                K_EXTERNO_NOEXTERNO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_PERSONA_NOPERSONA + " INTEGER," +
                K_EXTERNO_TIPO + " INTEGER," +
                K_EXTERNO_RFC + " TEXT," +
                K_EXTERNO_CIUDAD + " TEXT," +
                K_EXTERNO_SALDO + " REAL)");
        db.execSQL("CREATE TABLE " + T_VENDEDOR + " (" +
                K_VENDEDOR_NOVENDEDOR + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_PERSONA_NOPERSONA + " INTEGER," +
                K_VENDEDOR_COMISIONES + " REAL)");
        db.execSQL("CREATE TABLE " + T_PRODUCTO + " (" +
                K_PRODUCTO_NOPRODUCTO + " TEXT PRIMARY KEY," +
                K_PRODUCTO_NOMBRE + " TEXT," +
                K_PRODUCTO_LINEA + " TEXT," +
                K_PRODUCTO_EXISTENCIA + " INTEGER," +
                K_PRODUCTO_P_COSTO + " REAL," +
                K_PRODUCTO_P_PROMEDIO + " REAL," +
                K_PRODUCTO_P_VENTA_MAYOR + " REAL," +
                K_PRODUCTO_P_VENTA_MENOR + " REAL)");
        db.execSQL("CREATE TABLE " + T_COMPRA + " (" +
                K_COMPRA_NOCOMPRA+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_COMPRA_NOEXTERNOPROVEEDOR + " INTEGER," +
                K_COMPRA_FECHA + " TEXT," +
                K_COMPRA_F_R + " TEXT," +
                K_COMPRA_F_R_NO + " INTEGER," +
                K_COMPRA_TOTAL_PARES + " INTEGER," +
                K_COMPRA_SUMA + " REAL," +
                K_COMPRA_IVA + " REAL," +
                K_COMPRA_TOTAL + " REAL)");
        db.execSQL("CREATE TABLE " + T_VENTA + " (" +
                K_VENTA_NOVENTA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_VENTA_NOEXTERNOCLIENTE + " INTEGER," +
                K_VENDEDOR_NOVENDEDOR + " INTEGER," +
                K_VENTA_FECHA + " TEXT," +
                K_VENTA_COMISION + " REAL," +
                K_VENTA_F_R + " TEXT," +
                K_VENTA_F_R_NO + " INTEGER," +
                K_VENTA_TOTAL_PARES + " INTEGER," +
                K_VENTA_SUMA + " REAL," +
                K_VENTA_IVA + " REAL," +
                K_VENTA_TOTAL + " REAL)");
        db.execSQL("CREATE TABLE " + T_DETALLECOMPRA + " (" +
                K_DETALLECOMPRA_NODETALLECOMPRA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_COMPRA_NOCOMPRA + " INTEGER," +
                K_PRODUCTO_NOPRODUCTO +" INTEGER," +
                K_DETALLECOMPRA_CANTIDAD +" INTEGER," +
                K_DETALLECOMPRA_PRECIOCOSTO +" REAL)");
        db.execSQL("CREATE TABLE " + T_DETALLEVENTA + " (" +
                K_DETALLEVENTA_NODETALLEVENTA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_VENTA_NOVENTA + " INTEGER," +
                K_PRODUCTO_NOPRODUCTO +" INTEGER," +
                K_DETALLEVENTA_CANTIDAD +" INTEGER," +
                K_DETALLEVENTA_PRECIOVENTA +" REAL)");
        db.execSQL("CREATE TABLE " + T_HISTORICOPRODUCTO + " (" +
                K_HISTORICOPRODUCTO_NOHISTORICO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_PRODUCTO_NOPRODUCTO +" TEXT," +
                K_HISTORICOPRODUCTO_COSTO +" REAL," +
                K_HISTORICOPRODUCTO_CANTIDAD +" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + T_HISTORICOPRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS " + T_DETALLECOMPRA);
        db.execSQL("DROP TABLE IF EXISTS " + T_DETALLEVENTA);
        db.execSQL("DROP TABLE IF EXISTS " + T_COMPRA);
        db.execSQL("DROP TABLE IF EXISTS " + T_VENTA);
        db.execSQL("DROP TABLE IF EXISTS " + T_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS " + T_VENDEDOR);
        db.execSQL("DROP TABLE IF EXISTS " + T_EXTERNO);
        db.execSQL("DROP TABLE IF EXISTS " + T_PERSONA);
    }
}
