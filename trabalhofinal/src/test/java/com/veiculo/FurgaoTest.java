package com.veiculo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import com.excecoes.veiculo.LimiteRotaException;

public class FurgaoTest {
    private Furgao furgao;

    @Test
    public void furgaoCriado() {
        this.furgao = new Furgao("XXX-1111");
        assertEquals(0.0, this.furgao.getDespesaAtual(), .0);
    }

    @Test
    public void tanqueCheioTest() {
        this.furgao = new Furgao("YYY-2222");
        try{
            this.furgao.addRota(new Date(), 5);
        } catch (LimiteRotaException e){
            System.out.println(e.getMessage());
        }
        assertEquals(5, this.furgao.getKmRodados(), .0);
    }   

    @Test
    public void erroRota() {
        this.furgao = new Furgao("ZZZ-3333");

        Exception thrown = assertThrows(LimiteRotaException.class, () ->  this.furgao.addRota(new Date(), 5000000),
                "Esperado dar erro ao tentar realizar uma rota acima do limite.");

        assertTrue(thrown.getMessage().contains("Rota excede o limite do veiculo"));

    }

    @Test
    public void furgaoManutencao() {
        this.furgao = new Furgao("AAA-4444");
        this.furgao.fazerManutencao();
        assertEquals(400.0, this.furgao.getDespesaAtual(), .0);
    }   
}
