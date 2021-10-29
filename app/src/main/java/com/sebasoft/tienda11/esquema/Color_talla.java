package com.sebasoft.tienda11.esquema;

public class Color_talla {

    private int id;
    private String color,talle,stock;

    public Color_talla(){

    }
    public  Color_talla(int id,String color,String stock,String talle){
        this.id = id;
        this.color = color;
        this.talle = talle;
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getTalle() {
        return talle;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTalle(String talle) {
        this.talle = talle;
    }
}
