package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.manutencao.Curta;
import com.manutencao.Manutencao;

public class Carro extends Veiculo{
    protected static final double TANQUE_CAPACIDADE;

    static {
        TANQUE_CAPACIDADE = 50;
    }

    public Carro(String placa){
        super(placa, new Tanque(TANQUE_CAPACIDADE, Combustivel.GASOLINA));
    }

    @Override
    public double fazerManutencao() {
        Manutencao m = new Manutencao(new Curta());
        m.registrarManutencao(this.kmRodados);
        this.manutencoes.add(m);
        return m.proximaManutencao(); // O QUE ERA PRA RETORNAR AQUI ?
    }
}
