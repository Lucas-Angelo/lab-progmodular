package com.veiculo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FurgaoTest {
    private Furgao furgao;

    @Test
    public void caminhaoCriado() {
        this.furgao = new Furgao("XXX-1111");
        assertEquals(0.0, this.furgao.getDespesaAtual(), .0);
    }

    @Test
    public void tanqueCheioTest() {
        this.furgao = new Furgao("YYY-2222");
        this.furgao.reabastecer();
        assertEquals(80, this.furgao.getKmRodados(), .0);
    }   
}
