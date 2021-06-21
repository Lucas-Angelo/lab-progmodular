package com.manutencao;

import java.util.Date;

public class Manutencao {
    private Date data;
    private int kmRodados;
    private IManutencao plano;

    public Manutencao(IManutencao p){
        init(p);
    }

    private void init(IManutencao plano){
        this.plano = plano;
    }

    public int proximaManutencao(){
        return this.plano.proximaManutencao(this.kmRodados);
    };

    public void registrarManutencao(int kmRodados) {
        this.kmRodados = kmRodados;
    }
}
