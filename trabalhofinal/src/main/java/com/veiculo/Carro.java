package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.manutencao.Curta;

public class Carro extends Veiculo {
    protected static final double TANQUE_CAPACIDADE;

    static {
        TANQUE_CAPACIDADE = 50;
    }

    public Carro(String placa){
        super(placa, new Tanque(TANQUE_CAPACIDADE, Combustivel.GASOLINA), new Curta());
    }
    
}
