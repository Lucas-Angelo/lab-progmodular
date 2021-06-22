package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.manutencao.Media;

public class Furgao extends Veiculo{

    /**
     * Instancia um objeto Furgao com a placa
     * @param placa
     */
    public Furgao(String placa) {
        super( placa, new Tanque(80, Combustivel.DIESEL), new Media() );
    }
}
