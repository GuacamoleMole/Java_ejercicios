package org.example;

import org.example.platos.Filete;
import org.example.platos.Pizza;
import org.example.platos.Plato;
import org.example.preparacion.Frito;
import org.example.preparacion.Hervido;
import org.example.preparacion.Horneado;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Pizza pizzaMargaritaDelgada = new Pizza("Margarita", new Horneado(), "Delgada");
        Pizza pizzaCarbonaraOriginal = new Pizza("Carbonara", new Frito(), "Original");

        Filete fileteAngusAlPunto = new Filete("Angus", new Frito(), "Al punto");
        Filete fileteRibeyeBienCocido = new Filete("Ribeye", new Horneado(), "Bien cocido");
        Filete fileteTomahawkJugoso = new Filete("Tomahawk", new Hervido(), "Muy hecho");


        List<Plato> platos = new ArrayList<>();
        platos.add(pizzaMargaritaDelgada);
        platos.add(pizzaCarbonaraOriginal);
        platos.add(fileteAngusAlPunto);
        platos.add(fileteRibeyeBienCocido);
        platos.add(fileteTomahawkJugoso);

        Orden orden1 = new Orden(platos, 1);
        orden1.prepararOrden();

    }
}