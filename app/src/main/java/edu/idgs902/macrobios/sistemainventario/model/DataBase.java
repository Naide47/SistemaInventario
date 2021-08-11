package edu.idgs902.macrobios.sistemainventario.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    // Base de datos y version
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Inventario";

    // Tabla Persona
    public static final String T_PERSONA = "Persona";
    public static final String K_PERSONA_NOPERSONA = "noPersona";
    public static final String K_PERSONA_NOMBRE = "nombre";
    public static final String K_PERSONA_CALLE = "calle";
    public static final String K_PERSONA_COLONIA = "colonia";
    public static final String K_PERSONA_TELEFONO = "telefono";
    public static final String K_PERSONA_EMAIL = "email";

    // Tabla Externo
    public static final String T_EXTERNO = "Externo";
    public static final String K_EXTERNO_NOEXTERNO = "noExterno";
    public static final String K_EXTERNO_TIPO = "tipo";
    public static final String K_EXTERNO_RFC = "rfc";
    public static final String K_EXTERNO_CIUDAD = "ciudad";
    public static final String K_EXTERNO_SALDO = "saldo";

    // Tabla Vendedor
    public static final String T_VENDEDOR = "Vendedor";
    public static final String K_VENDEDOR_NOVENDEDOR = "noVendedor";
    public static final String K_VENDEDOR_COMISIONES = "comisiones";

    // Tabla Producto
    public static final String T_PRODUCTO = "Producto";
    public static final String K_PRODUCTO_NOPRODUCTO = "noProducto";
    public static final String K_PRODUCTO_NOMBRE = "nombre";
    public static final String K_PRODUCTO_LINEA = "linea";
    public static final String K_PRODUCTO_EXISTENCIA = "existencia";
    public static final String K_PRODUCTO_P_COSTO = "p_costo";
    public static final String K_PRODUCTO_P_PROMEDIO = "p_promedio";
    public static final String K_PRODUCTO_P_VENTA_MAYOR = "p_venta_mayor";
    public static final String K_PRODUCTO_P_VENTA_MENOR = "p_venta_menor";

    // Tabla Compra
    public static final String T_COMPRA = "Compra";
    public static final String K_COMPRA_NOCOMPRA = "noCompra";
    public static final String K_COMPRA_NOEXTERNOPROVEEDOR = "noExternoProveedor";
    public static final String K_COMPRA_FECHA = "fecha";
    public static final String K_COMPRA_F_R = "f_r";
    public static final String K_COMPRA_F_R_NO = "f_r_no";
    public static final String K_COMPRA_TOTAL_PARES = "total_pares";
    public static final String K_COMPRA_SUMA = "suma";
    public static final String K_COMPRA_IVA = "iva";
    public static final String K_COMPRA_TOTAL = "total";

    // Tabla Venta
    public static final String T_VENTA = "Venta";
    public static final String K_VENTA_NOVENTA = "noVenta";
    public static final String K_VENTA_NOEXTERNOCLIENTE = "noExternoCliente";
    public static final String K_VENTA_NOVENDEDOR = "noVendedor";
    public static final String K_VENTA_FECHA = "fecha";
    public static final String K_VENTA_COMISION = "comision";
    public static final String K_VENTA_F_R = "f_r";
    public static final String K_VENTA_F_R_NO = "f_r_no";
    public static final String K_VENTA_TOTAL_PARES = "total_pares";
    public static final String K_VENTA_SUMA = "suma";
    public static final String K_VENTA_IVA = "iva";
    public static final String K_VENTA_TOTAL = "total";

    // Tabla DetalleCompra
    public static final String T_DETALLECOMPRA = "DetalleCompra";
    public static final String K_DETALLECOMPRA_NODETALLECOMPRA = "noDetalleCompra";
    public static final String K_DETALLECOMPRA_CANTIDAD = "cantidad";
    public static final String K_DETALLECOMPRA_PRECIOCOSTO = "precioCosto";


    // Tabla DetalleVenta
    public static final String T_DETALLEVENTA = "DetalleVenta";
    public static final String K_DETALLEVENTA_NODETALLEVENTA = "noDetalleVenta";
    public static final String K_DETALLEVENTA_CANTIDAD = "cantidad";
    public static final String K_DETALLEVENTA_PRECIOVENTA = "precioVenta";

    // Tabla HistoricoProducto
    public static final String T_HISTORICOPRODUCTO = "HistoricoProducto";
    public static final String K_HISTORICOPRODUCTO_NOHISTORICO = "noHistoricio";
    public static final String K_HISTORICOPRODUCTO_COSTO = "costo";
    public static final String K_HISTORICOPRODUCTO_CANTIDAD = "cantidad";

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
                K_EXTERNO_SALDO + " REAL DEFAULT 0)");
        db.execSQL("CREATE TABLE " + T_VENDEDOR + " (" +
                K_VENDEDOR_NOVENDEDOR + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                K_PERSONA_NOPERSONA + " INTEGER," +
                K_VENDEDOR_COMISIONES + " REAL DEFAULT 0)");
        db.execSQL("CREATE TABLE " + T_PRODUCTO + " (" +
                K_PRODUCTO_NOPRODUCTO + " TEXT PRIMARY KEY," +
                K_PRODUCTO_NOMBRE + " TEXT," +
                K_PRODUCTO_LINEA + " TEXT," +
                K_PRODUCTO_EXISTENCIA + " INTEGER," +
                K_PRODUCTO_P_COSTO + " REAL DEFAULT 0," +
                K_PRODUCTO_P_PROMEDIO + " REAL DEFAULT 0," +
                K_PRODUCTO_P_VENTA_MAYOR + " REAL DEFAULT 0," +
                K_PRODUCTO_P_VENTA_MENOR + " REAL DEFAULT 0)");
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
