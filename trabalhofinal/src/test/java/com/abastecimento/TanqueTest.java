package com.abastecimento;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TanqueTest {

    private Tanque tanque;

    @Test
    public void tanqueVazioTest() {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        assertEquals(0, this.tanque.getQuantidade(), .0);
    }

    @Test
    public void valorReabastecido() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        double valorReabastecido = this.tanque.reabastecer(10);
        assertEquals(44.90, valorReabastecido, .0);
    }

    @Test
    public void valorConsumido() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(10);
        this.tanque.consumir(70);
        assertEquals(0.0, this.tanque.getQuantidade(), .0);
    }
    
}
