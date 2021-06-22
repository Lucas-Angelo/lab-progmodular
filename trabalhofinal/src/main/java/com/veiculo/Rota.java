package com.veiculo;

import java.io.Serializable;
import java.util.Date;

public class Rota implements Serializable{
    
    private static final long serialVersionUID = 102L;

    private Date data;
    private int kmRota;

    /**
     * Instancia um objeto Rota com a data e kms da rota
     * @param placa
     */
    public Rota(Date data, int kmRota) {
        this.data = data;
        this.kmRota = kmRota;
    }

    /**
     * Getters
     */
    public Date getData() {
        return data;
    }
    public int getKmRota() {
        return kmRota;
    }

    /**
     * Método equals para comparar se uma rota é igual a outra
     * Necessária para a implementação do conjunto de rotas do veículo a partir de um Set
     * O único comparador é a data, para barrar que um veículo tenha mais de uma rota no mesmo dia
     * @param o Objeto a ser comaparado
     * @return valor booleano que representa se o objeto passado é igual a classe instanciada
     */
    @Override
    public boolean equals(Object o){
        boolean igual;
        try {
            Rota v = (Rota) o;
            if (this.data == v.data)
                igual = true;
            else
                igual = false;
        } catch (ClassCastException e) {
            igual = false;
        }
        return igual;
    }

}
