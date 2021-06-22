package com.veiculo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import com.excecoes.veiculo.LimiteRotaException;

public class VanTest {
    private Van van;

    @Test
    public void vanCriada() {
        this.van = new Van("XXX-1111");
        assertEquals(0.0, this.van.getDespesaAtual(), .0);
    }

    @Test
    public void tanqueCheioTest() {
        this.van = new Van("YYY-2222");
        try{
            this.van.addRota(new Date(), 5);
        } catch (LimiteRotaException e){
            System.out.println(e.getMessage());
        }
        assertEquals(5, this.van.getKmRodados(), .0);
    }   

    @Test
    public void erroRota() {
        this.van = new Van("ZZZ-3333");

        Exception thrown = assertThrows(LimiteRotaException.class, () ->  this.van.addRota(new Date(), 5000000),
                "Esperado dar erro ao tentar realizar uma rota acima do limite.");

        assertTrue(thrown.getMessage().contains("Rota excede o limite do veiculo"));

    }

    @Test
    public void vanManutencao() {
        this.van = new Van("AAA-4444");
        this.van.fazerManutencao();
        assertEquals(400.0, this.van.getDespesaAtual(), .0);
    }   
}
