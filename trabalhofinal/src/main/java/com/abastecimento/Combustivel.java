package com.abastecimento;

/** Enum para guardar os tipos de combustíveis do sistema.
 *  Cada combustível possui o seu gasto médio (km/L) e preço médio (R$).
 *  Já estende de java.lang.Enum, ou seja, serializável.
* @author Lucas Ângelo.
* @version 1.0
*/
public enum Combustivel {

    ALCOOL(7d, 4.49d), DIESEL(4d, 4.34d), GASOLINA(10d, 5.79d);

    private double gastoMedio;
    private double precoMedio;
    
    private static final long serialVersionUID = 102L;

    /** Método init para inicializar os atributos comuns dos contrutores dos tipos de Combustivel.
    * @param gastoMedio double - Gasto médio km/L do combustível.
    * @param precoMedio double - Preço médio do combustível.
    * @author Lucas Ângelo.
    */
    private void init(double gastoMedio, double precoMedio) {
        this.gastoMedio = gastoMedio;
        this.precoMedio = precoMedio;
    }

    /** Construtor de Combustivel que irá inicializar os valores por meio da função init().
    * @param gastoMedio double - Gasto médio km/L do combustível.
    * @param precoMedio double - Preço médio do combustível.
    * @author Lucas Ângelo.
    * @return Combustivel - O objeto de combustível instânciado.
    */
    Combustivel(double gastoMedio, double precoMedio) {
        init(gastoMedio, precoMedio);
    }

    /** Retorna qual o gasto médio (km/L) do Combustivel.
    * @author Lucas Ângelo.
    * @return double - O gastoMedio.
    */
    public double consumo() {
        return this.gastoMedio;
    }

    /** Retorna qual o preço médio do Combustivel em reais.
    * @author Lucas Ângelo.
    * @return double - O valor precoMedio.
    */
    public double precoMedio() {
        return this.precoMedio;
    }

}
