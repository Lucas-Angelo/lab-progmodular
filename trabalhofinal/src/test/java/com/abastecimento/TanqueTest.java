package com.abastecimento;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.excecoes.abastecimento.LimiteKmException;
import com.excecoes.abastecimento.LimiteLitrosException;
import com.excecoes.abastecimento.NumeroNegativoException;
import com.excecoes.abastecimento.TrocaCapacidadeException;
import com.excecoes.abastecimento.TrocaCombustivel;

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

    @Test
    public void excecaoLimiteLitrosException() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        
        Exception thrown = assertThrows(
            LimiteLitrosException.class,
            () -> this.tanque.reabastecer(11),
            "Esperado dar erro ao tentar reabastecer mais litros do que o tanque suporta."
        );
 
        assertTrue(thrown.getMessage().contains("Não foi possível reabastecer"));
    }

    @Test
    public void excecaoNumeroNegativoException() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        
        Exception thrown = assertThrows(
            NumeroNegativoException.class,
            () -> this.tanque.reabastecer(-1),
            "Esperado dar erro ao tentar reabastecer litros negativos."
        );
 
        assertTrue(thrown.getMessage().contains("Não é possível abastecer quantidades negativas de litros."));
    }

    @Test
    public void exececaoLimiteKmException() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(10);
        
        Exception thrown = assertThrows(
            LimiteKmException.class,
            () -> this.tanque.consumir(71),
            "Esperado dar erro ao tentar andar mais quilometros do que o combustível suporta."
        );
 
        assertTrue(thrown.getMessage().contains("o tanque deste veículo suporta apenas mais"));
    }

    @Test
    public void exececaoTrocaCapacidadeException() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(10);
        
        Exception thrown = assertThrows(
            TrocaCapacidadeException.class,
            () -> this.tanque.setCapacidade(5),
            "Esperado dar erro ao tentar trocar a capacidade para algo menor do que já tem em litros."
        );
 
        assertTrue(thrown.getMessage().contains("a quantidade atual de litros no tanque é"));
    }

    @Test
    public void exececaoTrocaCombustivel() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(1);
        
        Exception thrown = assertThrows(
            TrocaCombustivel.class,
            () -> this.tanque.setCombustivel(Combustivel.GASOLINA),
            "Esperado dar erro ao tentar trocar o tipo de combustível com litros no tanque."
        );
 
        assertTrue(thrown.getMessage().contains("Para trocar o tipo de combustível é necessário esvaziar o tanque do veículo primeiramente."));
    }
    
}
