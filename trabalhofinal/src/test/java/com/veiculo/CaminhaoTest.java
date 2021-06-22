package com.veiculo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import com.excecoes.veiculo.LimiteRotaException;

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
        try{
            this.caminhao.addRota(new Date(), 5);
        } catch (LimiteRotaException e){
            System.out.println(e.getMessage());
        }
        assertEquals(5, this.caminhao.getKmRodados(), .0);
    }

    @Test
    public void erroRota() {
        this.caminhao = new Caminhao("ZZZ-3333");

        Exception thrown = assertThrows(LimiteRotaException.class, () ->  this.caminhao.addRota(new Date(), 5000000),
                "Esperado dar erro ao tentar realizar uma rota acima do limite.");

        assertTrue(thrown.getMessage().contains("Rota excede o limite do veiculo"));

    }

    @Test
    public void caimnhaoManutencao() {
        this.caminhao = new Caminhao("AAA-4444");
        this.caminhao.fazerManutencao();
        assertEquals(800.0, this.caminhao.getDespesaAtual(), .0);
    }   
}
