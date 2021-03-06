
/** 
 * MIT License
 *
 * Copyright(c) 2021 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.Serializable;

/** Classe pedido. Um pedido contém várias comidas (agregação) */
public class Pedido implements Serializable {
    /** Constante: máximo de comidas por pedido */
    private static final int MAXCOMIDAS;
    /** Atributo de classe para gerar novos IDs */
    private static int ultPedido;
    /** Salva o valor vendido pelo restaurante */
    private static double valorTotalVendido;

    /** ID do pedido. Sequencial */
    private int idPedido;
    /** Quantidade de comidas adicionadas */
    private int quantComidas;
    /** Sumário/descrição do pedido */
    private String sumario;
    /** Vetor com comidas (a ser melhorado) */
    private Comida[] comidas;
    /** Pedido aberto ou fechado */
    private boolean fechado;
    /** Data do pedido */
    Data dataPedido;

    static {
        MAXCOMIDAS = 10;
        ultPedido = 0;
        valorTotalVendido = 0;
    }

    public static String valorTotalVendido() {
        return "R$ " + String.format("%.2f", valorTotalVendido);
    }

    /** Construtor, cria pedido vazio */
    public Pedido() {
        this.idPedido = ultPedido;
        this.comidas = new Comida[MAXCOMIDAS];
        this.fechado = false;
    }

    /**
     * Get para a quantidade de comidas
     * 
     * @return Quantidade de comidas
     */
    public int getQuantComidas() {
        return this.quantComidas;
    }

    public static void setIdAtual(int qtd) {
        Pedido.ultPedido = qtd;
    }

    public static void setValorTotalVendido(double valor) {
        Pedido.valorTotalVendido = valor;
    }

    /**
     * Pedido aberto ou fechado
     * 
     * @return V/F para pedido fechado
     */
    public boolean fechado() {
        return this.fechado;
    }

    /**
     * Adiciona uma comida, se ainda houver capacidade
     * 
     * @param c A comida a ser adicionada
     * @return V/F para o sucesso da operação de adicionar
     * @throws Exception
     */
    public void addComida(Comida c) throws Exception {

        if (!this.fechado) {
            if (this.quantComidas < MAXCOMIDAS) {
                comidas[quantComidas] = c;
                this.quantComidas++;
            }
        }
        else{
            throw new Exception("Pedido fechado.");
        }

    }

    /**
     * Retorna o valor total do pedido (sem descontos)
     * 
     * @return Valor do pedido (double)
     */
    public double valorTotal() {
        double valor = 0.0;
        for (int i = 0; i < this.quantComidas; i++) {
            valor += comidas[i].precoFinal();
        }
        return valor;
    }

    /**
     * Método interno para gerar ou retornar o sumário.
     * 
     * @return String com o sumário (detalhamento) do pedido
     */
    private String sumario() {
        if (!this.fechado) {
            StringBuilder relat = new StringBuilder("PEDIDO Nº " + this.idPedido + "\n");

            for (int i = 0; i < quantComidas; i++) {
                relat.append(comidas[i].toString());
            }

            this.sumario = relat.toString();
        }
        return this.sumario;
    }

    /**
     * Fecha o pedido, se contiver ao menos uma comida. Gera o sumário no momento do
     * fechamento.
     * 
     * @return V/F para o fechamento do pedido.
     * @throws Exception
     */
    public void fecharPedido() throws Exception {
        if (this.quantComidas == 0)
            throw new Exception("Erro: não é possível fechar um pedido sem comidas.");
        
        if (this.fechado)
            throw new Exception("Erro: não é possível fechar um pedido já fechado.");

        this.dataPedido = new Data(5, 4, 2021);
        valorTotalVendido += this.valorTotal();
        this.sumario();
        this.fechado = true;
        ultPedido++;
    }

    /**
     * Sumário contendo a descrição de cada comida e o valor total.
     */
    @Override
    public String toString() {
        return this.sumario() + "Valor total = R$ " + this.valorTotal() + ".";
    }
}
