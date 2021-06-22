package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;

public class Furgao extends Veiculo{

    public Furgao(String placa) {
        super( placa, new Tanque(80, Combustivel.DIESEL) );
    }

    @Override
    public double fazerManutencao() {
        // TODO Auto-generated method stub
        return 0;
    }
}
