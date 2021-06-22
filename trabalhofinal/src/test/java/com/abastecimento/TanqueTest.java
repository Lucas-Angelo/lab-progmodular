package com.abastecimento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.excecoes.abastecimento.LimiteKmException;
import com.excecoes.abastecimento.NumeroNegativoException;
import com.excecoes.abastecimento.TrocaCapacidadeException;
import com.excecoes.abastecimento.TrocaCombustivel;

public class TanqueTest {

    private Tanque tanque;

    @Test
    @DisplayName("Verificar se ao criar o tanque, ele está vazio.")
    public void tanqueVazioTest() {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        assertEquals(0, this.tanque.getQuantidade(), .0);
    }

    @Test
    @DisplayName("Verificar o valor do combustível reabastecido.")
    public void valorReabastecido() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        double valorReabastecido = this.tanque.reabastecer(10);
        assertEquals(44.90, valorReabastecido, .0);
    }

    @Test
    @DisplayName("Verificar consumo de combustível.")
    public void valorConsumido() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(10);
        this.tanque.consumir(70);
        assertEquals(0.0, this.tanque.getQuantidade(), .0);
    }

    @Test
    @DisplayName("Verificar preço e quantidade no reabastecimento com valores maiores que a capcidade.")
    public void reabastecerQtdEValor() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(11);
        
        assertEquals(10, this.tanque.getQuantidade(), .0);
        assertEquals(0, this.tanque.reabastecer(10), .0);
    }

    @Test
    @DisplayName("Verificar se exceção é gerada ao reabastecer quantidade negativa de litros.")
    public void excecaoNumeroNegativoException() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);

        Exception thrown = assertThrows(NumeroNegativoException.class, () -> this.tanque.reabastecer(-1),
                "Esperado dar erro ao tentar reabastecer litros negativos.");

        assertTrue(thrown.getMessage().contains("Não é possível abastecer quantidades negativas de litros."));
    }

    @Test
    @DisplayName("Verificar se exceção é gerada ao tentar percorrer mais km do que os litros suportam.")
    public void exececaoLimiteKmException() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(10);

        Exception thrown = assertThrows(LimiteKmException.class, () -> this.tanque.consumir(71),
                "Esperado dar erro ao tentar andar mais quilometros do que o combustível suporta.");

        assertTrue(thrown.getMessage().contains("o tanque deste veículo suporta apenas mais"));
    }

    @Test
    @DisplayName("Verificar se exceção é gerada ao tentar diminuir capacidade do tanque com litros dentro.")
    public void exececaoTrocaCapacidadeException() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(10);

        Exception thrown = assertThrows(TrocaCapacidadeException.class, () -> this.tanque.setCapacidade(5),
                "Esperado dar erro ao tentar trocar a capacidade para algo menor do que já tem em litros.");

        assertTrue(thrown.getMessage().contains("a quantidade atual de litros no tanque é"));
    }

    @Test
    @DisplayName("Verificar se exceção é gerada ao tentar trocar o tipo de combustível com litros dentro.")
    public void exececaoTrocaCombustivel() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(1);

        Exception thrown = assertThrows(TrocaCombustivel.class, () -> this.tanque.setCombustivel(Combustivel.GASOLINA),
                "Esperado dar erro ao tentar trocar o tipo de combustível com litros no tanque.");

        assertTrue(thrown.getMessage().contains(
                "Para trocar o tipo de combustível é necessário esvaziar o tanque do veículo primeiramente."));
    }

    @Test
    @DisplayName("Verificar se o mesmo tanque é igual por equals.")
    public void iguaisPorEquals() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        Object o = this.tanque;
        assertTrue(this.tanque.equals(o));
    }

    @Test
    @DisplayName("Verificar se o mesmo tanque é igual por compareTo.")
    public void iguaisPorCompareTo() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        Object o = this.tanque;
        assertEquals(0, this.tanque.compareTo(o));
    }

    @Test
    @DisplayName("Verificar impressão do toString() do tanque.")
    public void impressao() throws Exception {
        this.tanque = new Tanque(10, Combustivel.ALCOOL);
        this.tanque.reabastecer(2);
        assertEquals("Tanque de capacidade de 10.0 litros. Atualmente possui 2.0 litros de alcool.",
                this.tanque.toString());
    }

}
