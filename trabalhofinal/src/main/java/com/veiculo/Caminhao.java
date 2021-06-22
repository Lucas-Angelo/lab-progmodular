package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;

public class Caminhao extends Veiculo{

    public Caminhao(String placa) {
        super( placa, new Tanque(250, Combustivel.DIESEL) );
    }

    @Override
    public double fazerManutencao() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
