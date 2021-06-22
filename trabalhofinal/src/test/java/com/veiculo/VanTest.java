package com.veiculo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VanTest {
    private Van van;

    @Test
    public void caminhaoCriado() {
        this.van = new Van("XXX-1111");
        assertEquals(0.0, this.van.getDespesaAtual(), .0);
    }

    @Test
    public void tanqueCheioTest() {
        this.van = new Van("YYY-2222");
        this.van.reabastecer();
        assertEquals(60, this.van.getKmRodados(), .0);
    }   
}
