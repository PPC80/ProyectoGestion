package com.example.proyectogestion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductoModel {

    private int stock, cantidad;
    private double valcompra, valventa, valtotal, aux;
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

    public ProductoModel(double valventa, double valtotal, String nombre, String detalle, String codigo) {
        this.valventa = valventa;
        this.valtotal = valtotal;
        this.nombre = nombre;
        this.detalle = detalle;
        this.codigo = codigo;
    }

    public ProductoModel(int cantidad, double valventa, double valtotal, String nombre, String detalle, String codigo) {
        aux = valtotal * cantidad;
        BigDecimal valTotalBigDec = new BigDecimal(aux).setScale(2, RoundingMode.HALF_UP);
        double auxFormatted = valTotalBigDec.doubleValue();
        this.cantidad = cantidad;
        this.valventa = valventa;
        this.valtotal = auxFormatted;
        this.nombre = nombre;
        this.detalle = detalle;
        this.codigo = codigo;
    }

    public ProductoModel() {
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValtotal() {
        return valtotal;
    }

    public void setValtotal(double valtotal) {
        this.valtotal = valtotal;
    }
}
