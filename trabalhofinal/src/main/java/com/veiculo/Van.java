package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.manutencao.Media;

public class Van extends Veiculo{

    /**
     * Instancia um objeto Van com a placa
     * @param placa
     */
    public Van(String placa) {
        super( placa, new Tanque(70, Combustivel.DIESEL), new Media() );
    }

}
