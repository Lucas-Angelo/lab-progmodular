package com.abastecimento;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CombustivelTest {

    private Combustivel combustivel;
    
    @Test
    @DisplayName("Verificar o consumo e preço médio do alcool.")
    public void ALCOOLTest() {
        this.combustivel = Combustivel.ALCOOL;
        assertEquals(7, this.combustivel.consumo(), .0);
        assertEquals(4.49d, this.combustivel.precoMedio(), .0);
    }

    @Test
    @DisplayName("Verificar o consumo e preço médio da diesel.")
    public void DIESELTest() {
        this.combustivel = Combustivel.DIESEL;
        assertEquals(4, this.combustivel.consumo(), .0);
        assertEquals(4.34d, this.combustivel.precoMedio(), .0);
    }

    @Test
    @DisplayName("Verificar o consumo e preço médio da gasolina.")
    public void GASOLINATest() {
        this.combustivel = Combustivel.GASOLINA;
        assertEquals(10, this.combustivel.consumo(), .0);
        assertEquals(5.79d, this.combustivel.precoMedio(), .0);
    }

}
