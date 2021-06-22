package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.manutencao.Longa;

public class Caminhao extends Veiculo{

    /**
     * Instancia um objeto Caminhao com a placa
     * @param placa
     */
    public Caminhao(String placa) {
        super( placa, new Tanque(250, Combustivel.DIESEL), new Longa() );
    }
    
}
