package com.manutencao;

import com.veiculo.Veiculo;

import java.io.Serializable;
import java.util.Date;

public class Manutencao implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 102L;
    private Date data;
    private int kmRodados;
    private IManutencao plano;

    public Manutencao(IManutencao p){
        init(p);
    }

    private void init(IManutencao plano){
        this.plano = plano;
    }

    public IManutencao getPlano() {
        return plano;
    }

    public int proximaManutencao(){
        return this.plano.proximaManutencao(this.kmRodados);
    };

    public void registrarManutencao(int kmRodados) {
        this.kmRodados = kmRodados;
    }

    @Override
    public int compareTo(Object obj) {
        int igual = -1;
        try {
            Manutencao m = (Manutencao) obj;
            if (this == m)
                igual = 0;
            else
                igual = 1;
        } catch (ClassCastException e) {
            System.err.println("Cast/Comparação inválida!");
            igual = -1;
        }
        return igual;
    }

    @Override
    public boolean equals(Object obj) {
        boolean igual = false;
        try {
            Manutencao m = (Manutencao) obj;
            if (this == m)
                igual = true;
        } catch (ClassCastException e) {
            System.err.println("Cast/Comparação inválida!");
            igual = false;
        }
        return igual;
    }

    /** Imprime os dados desta manutencao.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Data: ");
        sb.append(this.data.toString());
        sb.append(" Kms Rodados: ");
        sb.append(this.kmRodados);
        sb.append(" Plano: ");
        sb.append(this.plano.toString());

        return sb.toString();
    }
}
