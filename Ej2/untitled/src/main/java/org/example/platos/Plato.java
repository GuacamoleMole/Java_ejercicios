package org.example.platos;

import lombok.extern.slf4j.Slf4j;
import org.example.preparacion.MetodoPreparacion;

@Slf4j
public abstract class Plato {
    protected String nombre;
    protected MetodoPreparacion metodoPreparacion;

    protected Plato(String nombre, MetodoPreparacion metodoPreparacion) {
        this.nombre = nombre;
        this.metodoPreparacion = metodoPreparacion;
    }

    public void preparar() {
        metodoPreparacion.cocinar(this);
        log.info("Plato {} terminado", nombre);
    }

    public abstract String infoPreparacion();

}
