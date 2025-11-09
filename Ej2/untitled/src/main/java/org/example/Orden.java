package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.platos.Plato;

import java.util.List;

@Slf4j
public class Orden {

    private List<Plato> platos;
    private int numOrden;

    public Orden(List<Plato> platos, int numOrden) {
        this.platos = platos;
        this.numOrden = numOrden;
    }

    public void prepararOrden() {
        log.info("Preparando orden {}", numOrden);
        for (Plato plato : platos) {
            plato.preparar();
        }
        log.info("Orden {} finalizada", numOrden);
    }

}
