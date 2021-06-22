package com.veiculo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CaminhaoTest {
    private Caminhao caminhao;

    @Test
    public void caminhaoCriado() {
        this.caminhao = new Caminhao("XXX-1111");
        assertEquals(0.0, this.caminhao.getDespesaAtual(), .0);
    }

    @Test
    public void tanqueCheioTest() {
        this.caminhao = new Caminhao("YYY-2222");
        this.caminhao.reabastecer();
        assertEquals(250, this.caminhao.getKmRodados(), .0);
    }   
}
