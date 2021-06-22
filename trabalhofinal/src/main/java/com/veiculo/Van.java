package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;

public class Van extends Veiculo{

    public Van(String placa) {
        super( placa, new Tanque(70, Combustivel.DIESEL) );
    }

    @Override
    public double fazerManutencao() {
        // TODO Auto-generated method stub
        return 0;
    }
}
