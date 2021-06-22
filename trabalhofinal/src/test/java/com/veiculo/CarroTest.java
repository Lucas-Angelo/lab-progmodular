package com.veiculo;

import com.excecoes.veiculo.LimiteRotaException;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        try{
            this.carro.addRota(new Date(), 5);
        } catch (LimiteRotaException e){
            System.out.println(e.getMessage());
        }
        assertEquals(5, this.carro.getKmRodados(), .0);
    }

    @Test
    public void erroRota() {
        this.carro = new Carro("XXX-3333");

        Exception thrown = assertThrows(LimiteRotaException.class, () ->  this.carro.addRota(new Date(), 5000000),
                "Esperado dar erro ao tentar realizar uma rota acima do limite.");

        assertTrue(thrown.getMessage().contains("Rota excede o limite do veiculo"));

    }

    @Test
    public void carroManutencao() {
        this.carro = new Carro("XXX-1111");
        this.carro.fazerManutencao();
        assertEquals(200.0, this.carro.getDespesaAtual(), .0);
    }
}
