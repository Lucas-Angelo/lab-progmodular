package com.abastecimento;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.excecoes.LimiteLitrosException;
import com.excecoes.NumeroNegativoException;

public class Tanque {
    private double capacidade;
    private double quantidade;
    private Combustivel combustivel;

    private void init(double capacidade, double quantidade, Combustivel combustivel) {
        this.capacidade = capacidade;
        this.quantidade = quantidade;
        this.combustivel = combustivel;
    }
    
    public Tanque(double capacidade, double quantidade, Combustivel combustivel) {
        init(capacidade, quantidade, combustivel);
    }
    public Tanque(double capacidade, Combustivel combustivel) {
        init(capacidade, 0.0d, combustivel);
    }

    public void consumir(int kmRodados) {
        
    }

    public double reabastecer(int litros) throws Exception {
        double valorAPagar = 0;

        if( litros < 0 ) {
            throw new NumeroNegativoException("Não é possível abastecer quantidades negativas de litros.");
        }

        if( (this.quantidade+litros) <= this.capacidade ) {
            valorAPagar = litros * combustivel.precoMedio();
            this.quantidade += litros;
        } else {
            throw new LimiteLitrosException(
                String.format("Não foi possível reabastecer %i litros, pois, a capacidade do tanque é %d litros. A quantidade final de litros seria %d, já que havia %d", litros, this.capacidade, this.quantidade+litros, this.quantidade)
            );
        }

        BigDecimal bd = new BigDecimal(valorAPagar).setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }


    // @Override
    // public boolean equals(Object o) {
    //     if (o == this)
    //         return true;
    //     if (!(o instanceof Tanque)) {
    //         return false;
    //     }
    //     Tanque tanque = (Tanque) o;
    //     return capacidade == tanque.capacidade && quantidade == tanque.quantidade && Objects.equals(combustivel, tanque.combustivel);
    // }

    // @Override
    // public String toString() {
    //     return "{" +
    //         " capacidade='" + getCapacidade() + "'" +
    //         ", quantidade='" + getQuantidade() + "'" +
    //         ", combustivel='" + getCombustivel() + "'" +
    //         "}";
    // }

    public double getCapacidade() {
        return this.capacidade;
    }
    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    public double getQuantidade() {
        return this.quantidade;
    }
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Combustivel getCombustivel() {
        return this.combustivel;
    }
    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

}
