import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

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

/**
 * Classe cliente do restaurante. Contém pedidos (composição)
 * 
 * @author João Caram
 */
public class Cliente implements Serializable {

    /** Nome do cliente (livre) */
    public String nome;
    /** CPF do cliente (sem validação) */
    private String CPF;
    /** Vetor de pedidos. A ser melhorado */
    private List<Pedido> pedidos;
    /** Quantidade de pedidos até o momento */
    private int qtPedidos;
    /**
     * Categoria: injeção de dependência com interface. Composição em lugar de
     * herança
     */
    private IFidelidade categoriaFidelidade;

    private void init(String nome, String CPF) {
        this.nome = nome;
        try {
            CPF = CPF.replaceAll("[^0-9]", "");
            if (new CPF().validarCPF(CPF))
                this.CPF = CPF;
            else
                throw new CPFInvalidoException(CPF);
        } catch (CPFInvalidoException e) {
            System.err.println(e.getMessage());
        }
        this.pedidos = new LinkedList<>();
        this.qtPedidos = 0;
        this.categoriaFidelidade = null;
    }

    /**
     * Construtor. Devolve um cliente com 0 pedidos e categoria de fidelidade
     * 
     * @param nome Nome do cliente (livre)
     * @param CPF  CPF do cliente (sem validação)
     */
    public Cliente(String nome, String CPF) {
        init(nome, CPF);
    }

    /**
     * Adiciona um pedido
     * 
     * @param p O pedido já pronto
     * @return V/F se foi possível adicionar
     */
    public void addPedido(Object o) {
        
        try {
            Pedido p = (Pedido) o;
            if (p != null) {
                this.pedidos.add(p);
                this.qtPedidos++;
                this.mudarCategoria();
            } else {
                throw new NullPointerException("Erro: O pedido já deve estar pronto (Não nulo!).");
            }
        } catch (ClassCastException e) {
            System.err.println("Erro: deve ser adicionado pedidos para a lista de pedidos do cliente!");
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retorna o desconto atual do cliente, em %. Calculado por sua categoria de
     * fidelidade.
     */
    public double desconto() {
        if (this.categoriaFidelidade == null)
            return 0;
        else
            return this.categoriaFidelidade.desconto(pedidos);
    }

    /**
     * Verifica mudança de categoria. Só verifica upgrades, não rebaixa o cliente.
     */
    private void mudarCategoria() {
        IFidelidade teste; // vou testar se ele pode subir de categoria

        if (this.categoriaFidelidade == null)
            teste = new Cliente10();
        else
            teste = new Cliente25();

        if (teste.desconto(this.pedidos) > 0)
            this.categoriaFidelidade = teste;
    }

    /**
     * Busca o CPF do cliente
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * Descrição do cliente: nome, CPF, total de pedidos
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(this.nome + "\n");
        sb.append("CPF: " + this.CPF + "\n");
        sb.append("Total de pedidos: " + this.qtPedidos + "\n");
        return sb.toString();
    }


    @Override
    public String toString(){
        return "";
    }

    @Override
    public boolean equals(Object obj){
        try{
            Cliente clt = (Cliente)obj;
            return (this.CPF.equals(clt.CPF));
        }catch(ClassCastException ex){
            System.err.println("Cast/comparação inválida");
            return false;
        }
    }


}
