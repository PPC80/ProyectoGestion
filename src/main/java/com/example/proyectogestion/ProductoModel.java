package com.example.proyectogestion;

public class ProductoModel {

    private int stock;
    private double valcompra, valventa;
    private String nombre, detalle, categoria, codigo, iva;

    public ProductoModel(String codigo, int stock, double valcompra, double valventa, String nombre, String detalle, String categoria, String iva) {
        this.codigo = codigo;
        this.stock = stock;
        this.valcompra = valcompra;
        this.valventa = valventa;
        this.nombre = nombre;
        this.detalle = detalle;
        this.categoria = categoria;
        this.iva = iva;
    }

    public ProductoModel(double valventa, String nombre, String detalle, String codigo) {
        this.valventa = valventa;
        this.nombre = nombre;
        this.detalle = detalle;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getValcompra() {
        return valcompra;
    }

    public void setValcompra(double valcompra) {
        this.valcompra = valcompra;
    }

    public double getValventa() {
        return valventa;
    }

    public void setValventa(double valventa) {
        this.valventa = valventa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }
}
