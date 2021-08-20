package edu.idgs902.macrobios.sistemainventario.model;

public class HistoricoProducto {
    private int noHistorico;
    private int noCompra;
    private int noProducto;
    private double costo;
    private int cantidad;

    public HistoricoProducto(int noHistorico, int noCompra, int noProducto, double costo, int cantidad) {
        this.noHistorico = noHistorico;
        this.noCompra = noCompra;
        this.noProducto = noProducto;
        this.costo = costo;
        this.cantidad = cantidad;
    }

    public HistoricoProducto(int noCompra, int noProducto, double costo, int cantidad) {
        this.noCompra = noCompra;
        this.noProducto = noProducto;
        this.costo = costo;
        this.cantidad = cantidad;
    }

    public int getNoHistorico() {
        return noHistorico;
    }

    public int getNoCompra() {
        return noCompra;
    }

    public int getNoProducto() {
        return noProducto;
    }

    public double getCosto() {
        return costo;
    }

    public int getCantidad() {
        return cantidad;
    }
}
