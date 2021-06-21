package com.veiculo;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CarroTest {
    private Carro carro;

    @Test
    public void carroCriado() {
        this.carro = new Carro("XXX-1111");
        assertEquals(0.0, this.carro.getDespesaAtual(), .0);
    }

    @Test
    public void tanqueCheioTest() {
        this.carro = new Carro("XXX-2222");
        this.carro.addRota(new Date(), 5);
        assertEquals(5, this.carro.getKmRodados(), .0);
    }
}
