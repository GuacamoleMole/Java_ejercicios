package org.example.preparacion;

import lombok.extern.slf4j.Slf4j;
import org.example.platos.Plato;

@Slf4j
public class Frito implements MetodoPreparacion {

    @Override
    public void cocinar(Plato plato) {
        log.info("Friendo el plato: {}", plato.infoPreparacion());
    }
}
