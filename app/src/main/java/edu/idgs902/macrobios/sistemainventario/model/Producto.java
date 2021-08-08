package edu.idgs902.macrobios.sistemainventario.model;

public class Producto {

    private String producto_no; //Nùmero
    private String nombre;
    private String linea;
    private int existencia;
    private double p_costo;
    private double p_promedio;
    private double p_venta_mayor;
    private double p_venta_menor;

    public Producto(String producto_no, String nombre, String linea, int existencia, double p_costo, double p_promedio, double p_venta_mayor, double p_venta_menor) {
        this.producto_no = producto_no;
        this.nombre = nombre;
        this.linea = linea;
        this.existencia = existencia;
        this.p_costo = p_costo;
        this.p_promedio = p_promedio;
        this.p_venta_mayor = p_venta_mayor;
        this.p_venta_menor = p_venta_menor;
    }

    public Producto(String nombre, String linea, int existencia, double p_costo, double p_promedio, double p_venta_mayor, double p_venta_menor) {
        this.nombre = nombre;
        this.linea = linea;
        this.existencia = existencia;
        this.p_costo = p_costo;
        this.p_promedio = p_promedio;
        this.p_venta_mayor = p_venta_mayor;
        this.p_venta_menor = p_venta_menor;
    }

    public String getProducto_no() {
        return producto_no;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLinea() {
        return linea;
    }

    public int getExistencia() {
        return existencia;
    }

    public double getP_costo() {
        return p_costo;
    }

    public double getP_promedio() {
        return p_promedio;
    }

    public double getP_venta_mayor() {
        return p_venta_mayor;
    }

    public double getP_venta_menor() {
        return p_venta_menor;
    }
}
