package com.example.proyectogestion;

import java.util.Date;

public class FacturaModel {

    private int ciempl, cicli,cantidad;
    private double valtotal;
    private Date fechaemi;
    private String numfac, codprod;

    public FacturaModel(String numfac, int ciempl, int cicli, String codprod, int cantidad, double valtotal, Date fechaemi) {
        this.numfac = numfac;
        this.ciempl = ciempl;
        this.cicli = cicli;
        this.codprod = codprod;
        this.cantidad = cantidad;
        this.valtotal = valtotal;
        this.fechaemi = fechaemi;
    }

    public String getNumfac() {
        return numfac;
    }

    public void setNumfac(String numfac) {
        this.numfac = numfac;
    }

    public int getCiempl() {
        return ciempl;
    }

    public void setCiempl(int ciempl) {
        this.ciempl = ciempl;
    }

    public int getCicli() {
        return cicli;
    }

    public void setCicli(int cicli) {
        this.cicli = cicli;
    }

    public String getCodprod() {
        return codprod;
    }

    public void setCodprod(String codprod) {
        this.codprod = codprod;
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

    public Date getFechaemi() {
        return fechaemi;
    }

    public void setFechaemi(Date fechaemi) {
        this.fechaemi = fechaemi;
    }
}
