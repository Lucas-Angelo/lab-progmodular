package com.veiculo;

import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.excecoes.veiculo.LimiteRotaException;
import com.manutencao.*;

import java.io.Serializable;
import java.util.*;

public abstract class Veiculo implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 102L;
    
    protected String placa;
    private Tanque tanque;
    protected Set<Rota> rotas;
    protected double despesaAtual;
    protected int kmRodados;
    protected Queue<Manutencao> manutencoes;

    /** Construtor de Veiculo que irá inicializar os valores por meio da função init().
     * @param p String - A placa do veículo.
     * @param t Tanque - O tanque incluido no veiculo.
     * @param plano IManutencao - O plano de manutenção do veículo
     * @return Veiculo - O objeto de veiculo instânciado.
     */
    public Veiculo(String p, Tanque t, IManutencao plano) {
        init(p,t,plano);
    }

    /** Método init para inicializar os atributos comuns dos contrutores dos tipos de Veiculo quando instânciar um novo veiculo.
     * @param p String - A placa do veículo.
     * @param t Tanque - O tanque incluido no veiculo.
     * @param plano IManutencao - O plano de manutenção do veículo
     */
    private void init(String p, Tanque t, IManutencao plano){
        this.placa = p;
        this.tanque = t;
        this.manutencoes = new LinkedList<Manutencao>();

        Manutencao m = new Manutencao(plano);
        m.registrarManutencao(this.kmRodados);
        this.manutencoes.add(m);
        this.rotas = new LinkedHashSet<Rota>();
    }

    /** Método para retornar a quilometragem
     * @return int - quilometragem total to veículo
     */
    public int getKmRodados() {
        return kmRodados;
    }

    /** Método para retornar o gasto do veículo
     * @return double - gasto total com o veiculo
     */
    public double getDespesaAtual() {
        return despesaAtual;
    }

    /**
     * Adicionar uma nova rota ao veículo
     *
     * @param data - Quando ocorreu a rota
     * @param kmTotal - Quilometragem da rota
     * @throws LimiteRotaException Caso exceda o limite diario do veículo
     */
    public void addRota(Date data, int kmTotal) throws LimiteRotaException {

        Combustivel c = this.tanque.getCombustivel();
        double necessario = kmTotal/c.consumo(); // Calcular qnt precisa para rota;

        if(necessario > this.tanque.getCapacidade()) // A rota excede o limite máximo desse veículo
            throw new LimiteRotaException("Rota excede o limite do veiculo");
        else if(this.tanque.getQuantidade() < necessario) // Verificar se possui combustivel necessário para fazer a rota, caso contrario reabastecer
            this.reabastecer();

        Rota r = new Rota(data, kmTotal);
        rotas.add(r);
        
        this.kmRodados += kmTotal;
        try {
            this.tanque.consumir(kmTotal);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*Reabastece o tanque do veículo
     * @return double - Valor do reabastecimento
     */
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


    /**Realiza a manutencao de um veículo
     *
     * @return double - Valor da manutencao
     */
    public double fazerManutencao(){

        IManutencao tipo = this.manutencoes.peek().getPlano();
        Manutencao m = new Manutencao(tipo);
        double price = m.registrarManutencao(this.kmRodados);

        this.despesaAtual += price;
        this.manutencoes.add(m);
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

    /** Verifica se um objeto é igual a este veiculo.
     * @param obj Objeto - O objeto a ser verificado.
     */
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

    /** Imprime os dados deste veículo.
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
