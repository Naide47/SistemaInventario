package edu.idgs902.macrobios.sistemainventario.model;

import java.util.List;

public class Compra {

    private int venta_no; //Nùmero
    private Externo externo_proveedor;
    private String fecha;
    private String f_r;
    private int f_r_no;
    private int total_pares;
    private double suma;
    private double iva;
    private double total_venta;
    private List<DetalleCompra> detallesCompra;

    public Compra(int venta_no, Externo externo_proveedor, String fecha, String f_r, int f_r_no, int total_pares, double suma, double iva, double total_venta) {
        this.venta_no = venta_no;
        this.externo_proveedor = externo_proveedor;
        this.fecha = fecha;
        this.f_r = f_r;
        this.f_r_no = f_r_no;
        this.total_pares = total_pares;
        this.suma = suma;
        this.iva = iva;
        this.total_venta = total_venta;
    }

    public Compra(Externo externo_proveedor, String fecha, String f_r, int f_r_no, int total_pares, double suma, double iva, double total_venta) {
        this.externo_proveedor = externo_proveedor;
        this.fecha = fecha;
        this.f_r = f_r;
        this.f_r_no = f_r_no;
        this.total_pares = total_pares;
        this.suma = suma;
        this.iva = iva;
        this.total_venta = total_venta;
    }

    public int getVenta_no() {
        return venta_no;
    }

    public Externo getExterno_proveedor() {
        return externo_proveedor;
    }

    public String getFecha() {
        return fecha;
    }

    public String getF_r() {
        return f_r;
    }

    public int getF_r_no() {
        return f_r_no;
    }

    public int getTotal_pares() {
        return total_pares;
    }

    public double getSuma() {
        return suma;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal_venta() {
        return total_venta;
    }
}
