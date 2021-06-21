package com.abastecimento;

public enum Combustivel {

    ALCOOL(7d, 4.49d), DIESEL(4d, 4.34d), GASOLINA(10d, 5.79d);

    private double gastoMedio;
    private double precoMedio;

    private void init(double gastoMedio, double precoMedio) {
        this.gastoMedio = gastoMedio;
        this.precoMedio = precoMedio;
    }

    Combustivel(double gastoMedio, double precoMedio) {
        init(gastoMedio, precoMedio);
    }

    public double consumo() {
        return this.gastoMedio;
    }

    public double precoMedio() {
        return this.precoMedio;
    }

}
