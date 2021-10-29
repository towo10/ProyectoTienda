package com.sebasoft.tienda11.esquema;

public class Categoria {
    private int id;
    private String categoria;

    public Categoria(){
        this.id = 0;
        this.categoria = "";
    }

    public Categoria(int id,String categoria){
        this.id = id;
        this.categoria = categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getId() {
        return id;
    }
}
