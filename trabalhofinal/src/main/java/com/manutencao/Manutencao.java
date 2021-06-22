package com.manutencao;

import com.veiculo.Veiculo;

import java.io.Serializable;
import java.util.Date;

public class Manutencao implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 102L;
    private Date data;
    private int kmRodados;
    private IManutencao plano;

    /** Construtor de Manutencao que irá inicializar os valores por meio da função init().
     * @param p - Plano da manutencao
     */
    public Manutencao(IManutencao p){
        init(p);
    }

    /**Método init para inicializar os atributos comuns dos contrutores dos tipos de Manutencao quando instânciar um nova manutencao.
     *
     * @param plano
     */
    private void init(IManutencao plano){
        this.plano = plano;
    }

    /** Retorna o plano da manutencao
     * @return IManutencao
     */
    public IManutencao getPlano() {
        return plano;
    }

    /** Quando e a proxima manutencao
     *
     * @return int - Quando e a proxima manutencao
     */
    public int proximaManutencao(){
        return this.plano.proximaManutencao(this.kmRodados);
    };

    /**
     * @param kmRodados Quantidade de kms rodado pelo veiculo
     * @return double - Valor da manutencao
     */
    public double registrarManutencao(int kmRodados) {
        this.kmRodados = kmRodados;
        double price = 0.0;
        if(plano instanceof Curta)
            price = 200.00;
        else if(plano instanceof Media)
            price = 400.00;
        else if(plano instanceof Longa)
            price = 800.00;

        return price;
    }

    /** Verifica se um objeto é igual, menor ou maior do que esta manutencao.
     * @param obj Objeto - O objeto a ser verificado.
     */
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

    /** Verifica se um objeto é igual a esta manutencao.
     * @param obj Objeto - O objeto a ser verificado.
     */
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
