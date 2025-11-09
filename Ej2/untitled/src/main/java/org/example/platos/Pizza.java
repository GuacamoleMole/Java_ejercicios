package org.example.platos;

import org.example.preparacion.MetodoPreparacion;

public class Pizza extends Plato {

    private String tipoMasa;

    public Pizza(String nombre, MetodoPreparacion metodoPreparacion, String tipoMasa) {
        super(nombre, metodoPreparacion);
        this.tipoMasa = tipoMasa;
    }

    @Override
    public String infoPreparacion() {
        return String.format("Pizza %s con tipo de masa %s", this.nombre, this.tipoMasa);
    }
}
