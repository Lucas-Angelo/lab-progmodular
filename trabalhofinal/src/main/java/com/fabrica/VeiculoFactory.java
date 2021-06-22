package com.fabrica;

import com.veiculo.Caminhao;
import com.veiculo.Carro;
import com.veiculo.Furgao;
import com.veiculo.Van;
import com.veiculo.Veiculo;

public class VeiculoFactory implements Factory<Veiculo> {
    @Override
    public Veiculo getProduct(int choice, String placa){
        Veiculo novo = null;
        switch (choice){
            case 1:
                novo = new Carro(placa);
                break;
            case 2:
                novo = new Furgao(placa);
                break;
            case 3:
                novo = new Van(placa);
                break;
            case 4:
                novo = new Caminhao(placa);
                break;
        }
        return novo;
    }
}
