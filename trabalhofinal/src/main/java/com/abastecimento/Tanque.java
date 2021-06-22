package com.abastecimento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.excecoes.abastecimento.LimiteKmException;
import com.excecoes.abastecimento.TrocaCapacidadeException;
import com.excecoes.abastecimento.TrocaCombustivel;

/** Classe para gerenciar os tanques de combustível dos veículos.
 *  Cada tanque possui o tipo de Combustivel que está sendo utilizado, sua capacidade máxima em litros e a quantidade de litros atual.
 *  Serializável e comparável.
* @author Lucas Ângelo.
* @version 1.0
*/
public class Tanque implements Serializable, Comparable<Object> {
    private double capacidade;
    private double quantidade;
    private Combustivel combustivel;

    private static final long serialVersionUID = 102L;

    /** Método init para inicializar os atributos comuns dos contrutores dos tipos de Tanque quando instânciar um novo tanque.
    * @param capacidade double - A capacidade máxima do novo tanque.
    * @param combustivel Combustivel - O tipo de combustível que será utilizado nesse tanque no momento.
    * @author Lucas Ângelo.
    */
    private void init(double capacidade, Combustivel combustivel) {
        this.capacidade = capacidade;
        this.quantidade = 0.0d;
        this.combustivel = combustivel;
    }

    /** Construtor de Tanque que irá inicializar os valores por meio da função init().
    * @param capacidade double - A capacidade máxima do novo tanque.
    * @param combustivel Combustivel - O tipo de combustível que será utilizado nesse tanque no momento.
    * @author Lucas Ângelo.
    * @return Tanque - O objeto de tanque instânciado.
    */
    public Tanque(double capacidade, Combustivel combustivel) {
        init(capacidade, combustivel);
    }

    /** Consumir combustível necessário para o tanto de quilômetros informados.
    * @param kmRodados int - Quantidade de quilômetros à ser perco.
    * @author Lucas Ângelo.
    * @throws LimiteKmException Caso a quantia de combustível no tanque não consiga percorrer a quantidade de quilômetros informados.
    */
    public void consumir(int kmRodados) throws LimiteKmException {
        double kmRestantes = calcularKmRestantes();

        if ((kmRestantes - kmRodados) < 0) {
            throw new LimiteKmException(String.format(
                    "Não é possível rodar mais %d km, pois, o tanque deste veículo suporta apenas mais %.2f km.",
                    kmRodados, kmRestantes));
        }

        setQuantidade((kmRestantes / combustivel.consumo()) - (kmRodados / combustivel.consumo()));
    }

    /** Calcula quantos quilômetros a quantidade de combustível presente no tanque consegue percorrer.
     * Levando em consideração o tipo de combustível.
    * @author Lucas Ângelo.
    * @return Double - A quantidade de quilômetros.
    */
    private double calcularKmRestantes() {
        return this.quantidade * combustivel.consumo();
    }

    /** Reabastecer o tanque com uma quantidade de litros informada.
    * @param litros int - Quantidade de litros à ser reabastecido.
    * @author Lucas Ângelo.
    * @return Double - O valor a ser pago pela quantidade de litros adicionada para aquele tipo de combustível.
    */
    public double reabastecer(int litros) throws Exception {
        double valorAPagar = 0;

        if ((litros) < 0) {
            litros = 0;
        }

        if ((this.quantidade + litros) > this.capacidade) {
            litros = (int) Math.ceil(this.capacidade - this.quantidade);
        }

        if(this.quantidade+litros > this.capacidade)
            this.quantidade += this.capacidade; // Para não adicionar mais litros do que o tanque suporta
        else
            this.quantidade += litros;

        valorAPagar = calcularPrecoAPagar(litros);
        BigDecimal bd = new BigDecimal(valorAPagar).setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    /** Calcula o preço a ser pago por uma quantidade de litros.
     * Levando em consideração o tipo de combustível.
    * @param litros int - Quantidade de litros para o cálculo do preço.
    * @author Lucas Ângelo.
    * @return Double - O preço a pagar por aquele tanto de litros.
    */
    private double calcularPrecoAPagar(int litros) {
        return litros * combustivel.precoMedio();
    }

    /** Retorna qual a capacidade máxima de litros do tanque.
    * @author Lucas Ângelo.
    * @return Double - O capacidade máxima.
    */
    public double getCapacidade() {
        return this.capacidade;
    }

    /** Alterar capacidade máxima de litros de combustível que o tanque suporta.
    * @param capacidade double - A nova capacidade.
    * @author Lucas Ângelo.
    * @throws TrocaCapacidadeException Caso a capacidade máxima nova seja menor do que a quantia de litros dentro do tanque no momento.
    */
    public void setCapacidade(double capacidade) throws Exception {
        if (capacidade < this.quantidade)
            throw new TrocaCapacidadeException(String.format(
                    "Não é possível reduzir a capacidade para %.2f litros, pois, a quantidade atual de litros no tanque é %.2f.",
                    capacidade, this.quantidade));

        this.capacidade = capacidade;
    }

    /** Retorna qual a quantidade de litros do tanque no momento.
    * @author Lucas Ângelo.
    * @return Double - A quantidade.
    */
    public double getQuantidade() {
        return this.quantidade;
    }

    /** Altera a quantidade de litros do tanque no momento. (PS: privado, para não burlar o reabastecer).
    * @param quantidade double - A nova quantia.
    * @author Lucas Ângelo.
    */
    private void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /** Retorna qual a quantidade de litros do tanque no momento.
    * @author Lucas Ângelo.
    * @return Double - A quantidade.
    */
    public Combustivel getCombustivel() {
        return this.combustivel;
    }

    /** Alterar o tipo de combustível atual do tanque.
    * @param combustivel Combustivel - O novo tipo de combustível.
    * @author Lucas Ângelo.
    * @throws TrocaCombustivel Caso tente alterar o combustível com litros dentro do tanque.
    */
    public void setCombustivel(Combustivel combustivel) throws Exception {
        if (this.quantidade != 0)
            throw new TrocaCombustivel(String.format(
                    "Para trocar o tipo de combustível é necessário esvaziar o tanque do veículo primeiramente. Restam %.2f km.",
                    calcularKmRestantes()));

        this.combustivel = combustivel;
    }

    /** Verifica se um objeto é igual a este tanque.
    * @param obj Objeto - O objeto a ser verificado.
    * @author Lucas Ângelo.
    */
    @Override
    public boolean equals(Object obj) {
        boolean igual = false;
        try {
            Tanque tanque = (Tanque) obj;
            if (this == tanque)
                igual = true;
        } catch (ClassCastException e) {
            System.err.println("Cast/Comparação inválida!");
            igual = false;
        }
        return igual;
    }

    /** Verifica se um objeto é igual, menor ou maior do que este tanque.
    * @param obj Objeto - O objeto a ser verificado.
    * @author Lucas Ângelo.
    */
    @Override
    public int compareTo(Object obj) {
        int igual = -1;
        try {
            Tanque tanque = (Tanque) obj;
            if (this == tanque)
                igual = 0;
            else
                igual = 1;
        } catch (ClassCastException e) {
            System.err.println("Cast/Comparação inválida!");
            igual = -1;
        }
        return igual;
    }

    /** Imprime os dados deste tanque.
    * @author Lucas Ângelo.
    */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Tanque de capacidade de ");
        sb.append(this.capacidade + " litros. ");
        sb.append("Atualmente possui ");
        sb.append(this.quantidade + " litros de " + this.combustivel.toString().toLowerCase() + ".");

        return sb.toString();
    }

}
