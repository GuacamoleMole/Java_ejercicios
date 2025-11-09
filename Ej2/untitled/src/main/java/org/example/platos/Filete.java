package org.example.platos;

import org.example.preparacion.MetodoPreparacion;

public class Filete extends Plato {

    private String tipoCoccion;

    public Filete(String nombre, MetodoPreparacion metodoPreparacion, String tipoCoccion) {
        super(nombre, metodoPreparacion);
        this.tipoCoccion = tipoCoccion;
    }

    @Override
    public String infoPreparacion() {
        return String.format("Filete %s con tipo de coccion %s", this.nombre, this.tipoCoccion);
    }
}
