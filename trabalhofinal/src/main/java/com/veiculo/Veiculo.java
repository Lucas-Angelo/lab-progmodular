package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.manutencao.Manutencao;

import java.util.Date;
import java.util.Queue;
import java.util.Set;

public abstract class Veiculo {
    
    private String placa;
    private Tanque tanque;
    protected Set<Rota> rotas;
    protected double despesaAtual;
    protected int kmRodados;
    protected Queue<Manutencao> manutencoes;

    Veiculo(String p, Tanque t) {
        this.placa = p;
        this.tanque = t;
    }

    public int getKmRodados() {
        return kmRodados;
    }

    public double getDespesaAtual() {
        return despesaAtual;
    }

    public void addRota(Date data, int kmTotal){

        Combustivel c = this.tanque.getCombustivel();

        double necessario = kmTotal/c.consumo(); // Calcular qnt precisa para rota;
        double qtdCombustivel = this.tanque.getQuantidade();
        if(qtdCombustivel < necessario) // Precisa de mais gasolina
            this.reabastecer();

        /*Rota r = new Rota(data, kmTotal);

        * Rota r = new Rota();
        * r.setData(data);
        * r.setKmTotal(kmTotal);
        *
        * rotas.add(r);
        *
        */
        this.kmRodados += kmTotal;
        this.tanque.consumir(kmTotal);
    }

    public double reabastecer(){
        int quantidade = (int) this.tanque.getCapacidade() - (int) this.tanque.getQuantidade();
        try{
            double preco = this.tanque.reabastecer(quantidade);
            this.despesaAtual += preco;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  0.0;
    }

    // VERIFICAR SE É ABSTRATO MESMO
    public abstract double fazerManutencao();


}
