package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.excecoes.veiculo.LimiteRotaException;
import com.manutencao.*;

import java.io.Serializable;
import java.util.*;

public abstract class Veiculo implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 102L;
    
    private String placa;
    private Tanque tanque;
    protected Set<Rota> rotas;
    protected double despesaAtual;
    protected int kmRodados;
    protected Queue<Manutencao> manutencoes;

    public Veiculo(String p, Tanque t, IManutencao plano) {
        init(p,t,plano);
    }

    private void init(String p, Tanque t, IManutencao plano){
        this.placa = p;
        this.tanque = t;
        this.manutencoes = new LinkedList<Manutencao>();

        Manutencao m = new Manutencao(plano);
        m.registrarManutencao(this.kmRodados);
        this.manutencoes.add(m);
    }

    public int getKmRodados() {
        return kmRodados;
    }

    public double getDespesaAtual() {
        return despesaAtual;
    }

    public void addRota(Date data, int kmTotal) throws LimiteRotaException {

        Combustivel c = this.tanque.getCombustivel();
        double necessario = kmTotal/c.consumo(); // Calcular qnt precisa para rota;

        if(necessario > this.tanque.getCapacidade()) // A rota excede o limite máximo desse veículo
            throw new LimiteRotaException("Rota excede o limite do veiculo");
        else if(this.tanque.getQuantidade() < necessario) // Verificar se possui combustivel necessário para fazer a rota, caso contrario reabastecer
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
        try {
            this.tanque.consumir(kmTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public double fazerManutencao(){

        IManutencao tipo = this.manutencoes.peek().getPlano();
        Manutencao m = new Manutencao(tipo);
        m.registrarManutencao(this.kmRodados);
        this.manutencoes.add(m);

        double price = 0.0;
        if(tipo instanceof Curta)
            price = 200.00;
        else if(tipo instanceof Media)
            price = 400.00;
        else if(tipo instanceof Longa)
            price = 800.00;

        this.despesaAtual += price;

        return price;

    }

    @Override
    public int compareTo(Object obj) {
        int igual = -1;
        try {
            Veiculo v = (Veiculo) obj;
            if (this.placa.equals(v.placa))
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
            Veiculo v = (Veiculo) obj;
            if (this.placa.equals(v.placa))
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

        sb.append("Placa: ");
        sb.append(this.placa);
        sb.append(" Tanque: ");
        sb.append(this.tanque.toString());
        sb.append(" Despesa Atual: ");
        sb.append(String.format("%.2f",this.despesaAtual));
        sb.append(" Kms Rodados: ");
        sb.append(this.kmRodados);
        sb.append(" Rotas Realizadas: ");
        sb.append(this.rotas.size());
        sb.append(" Manutencoes Realizadas: ");
        sb.append(this.manutencoes.size());

        return sb.toString();
    }
}
