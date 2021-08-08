package edu.idgs902.macrobios.sistemainventario.model;

public class DetalleCompra {

    private int detalle_compra_no;
    private Producto producto;
    private int cantidad_producto;
    private double precio_venta;

    public DetalleCompra(int detalle_compra_no, Producto producto, int cantidad_producto, double precio_venta) {
        this.detalle_compra_no = detalle_compra_no;
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    public DetalleCompra(Producto producto, int cantidad_producto, double precio_venta) {
        this.producto = producto;
        this.cantidad_producto = cantidad_producto;
        this.precio_venta = precio_venta;
    }

    public int getDetalle_compra_no() {
        return detalle_compra_no;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }
}
