package com.manutencao;

import java.io.Serializable;
import java.util.Objects;

public class Curta implements IManutencao, Serializable {

    protected int ultimaManun;
    private static final long serialVersionUID = 100L;

    public Curta (){
        init(0);
    }
    public Curta(int kmRodadoAtual){
        init (kmRodadoAtual);
    }

    private void init(int ultimaManun ){

        this.ultimaManun = ultimaManun;
    }


    @Override
    public int proximaManutencao(int kmManutencao) {
        return  ( ultimaManun - kmManutencao );
    }

    public boolean executaManutenção(int kmManutencao){
        ultimaManun = kmManutencao;
        return (ultimaManun == kmManutencao);


    }

    @Override
    public String toString() {
        return "Curta{" +
                "ultimaManun= " + ultimaManun +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Longa longa = (Longa) o;
        return ultimaManun == longa.ultimaManun;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ultimaManun);
    }

}