package com.abastecimento;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.excecoes.abastecimento.LimiteKmException;
import com.excecoes.abastecimento.LimiteLitrosException;
import com.excecoes.abastecimento.NumeroNegativoException;
import com.excecoes.abastecimento.TrocaCapacidadeException;
import com.excecoes.abastecimento.TrocaCombustivel;

public class Tanque {
    private double capacidade;
    private double quantidade;
    private Combustivel combustivel;

    private void init(double capacidade, int quantidade, Combustivel combustivel) {
        this.capacidade = capacidade;
        this.quantidade = quantidade;
        this.combustivel = combustivel;
    }
    
    public Tanque(double capacidade, Combustivel combustivel) {
        init(capacidade, 0, combustivel);
    }

    public void consumir(int kmRodados) throws Exception {
        double kmRestantes = calcularKmRestantes();

        if ((kmRestantes - kmRodados) < 0) {
            throw new LimiteKmException(String.format(
                    "Não é possível rodar mais %d km, pois, o tanque deste veículo suporta apenas mais %.2f km.",
                    kmRodados, kmRestantes));
        }

        setQuantidade( (kmRestantes / combustivel.consumo()) - (kmRodados / combustivel.consumo()));
    }

    private double calcularKmRestantes() {
        return this.quantidade * combustivel.consumo();
    }

    public double reabastecer(int litros) throws Exception {
        double valorAPagar = 0;

        if ((litros) < 0) {
            throw new NumeroNegativoException("Não é possível abastecer quantidades negativas de litros.");
        }

        if ((this.quantidade + litros) > this.capacidade) {
            throw new LimiteLitrosException(String.format(
                    "Não foi possível reabastecer %d litros, pois, a capacidade do tanque é %.2f litros. A quantidade final de litros seria %.2f, já que havia %.2f.",
                    litros, this.capacidade, this.quantidade + litros, this.quantidade));
        }

        valorAPagar = calcularPrecoAPagar(litros);
        this.quantidade += litros;

        BigDecimal bd = new BigDecimal(valorAPagar).setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    private double calcularPrecoAPagar(int litros) {
        return litros * combustivel.precoMedio();
    }

    public double getCapacidade() {
        return this.capacidade;
    }
    public void setCapacidade(double capacidade) throws Exception {
        if (capacidade < this.quantidade)
            throw new TrocaCapacidadeException(String.format(
                    "Não é possível reduzir a capacidade para %.2f litros, pois, a quantidade atual de litros no tanque é %.2f.",
                    capacidade, this.quantidade));

        this.capacidade = capacidade;
    }

    public double getQuantidade() {
        return this.quantidade;
    }
    private void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Combustivel getCombustivel() {
        return this.combustivel;
    }
    public void setCombustivel(Combustivel combustivel) throws Exception {

        if (this.quantidade != 0)
            throw new TrocaCombustivel(String.format(
                    "Para trocar o tipo de combustível é necessário esvaziar o tanque do veículo primeiramente. Restam %.2f km.",
                    calcularKmRestantes()));

        this.combustivel = combustivel;
    }

    // @Override
    // public boolean equals(Object o) {
    // if (o == this)
    // return true;
    // if (!(o instanceof Tanque)) {
    // return false;
    // }
    // Tanque tanque = (Tanque) o;
    // return capacidade == tanque.capacidade && quantidade == tanque.quantidade &&
    // Objects.equals(combustivel, tanque.combustivel);
    // }

    // @Override
    // public String toString() {
    // return "{" +
    // " capacidade='" + getCapacidade() + "'" +
    // ", quantidade='" + getQuantidade() + "'" +
    // ", combustivel='" + getCombustivel() + "'" +
    // "}";
    // }

}
