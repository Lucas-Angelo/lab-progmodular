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

import java.util.Scanner;

/**
 * Exemplo - Restaurante, comidas, pedidos, clientes e fidelidade
 * Versão 0.3
 */
public class App {

    //#region Utilidades
    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    public static void limparTela(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    /**
     * Menu para o restaurante
     * @param teclado Scanner de leitura
     * @return Opção do usuário (int)
     */
    public static int menu(Scanner teclado){
        limparTela();
        System.out.println("XULAMBS DELIVERY");
        System.out.println("==========================");
        System.out.println("1 - Novo pedido");
        System.out.println("2 - Incluir comida em pedido");
        System.out.println("3 - Detalhes do pedido");
        System.out.println("4 - Fechar pedido");
        System.out.println("5 - Contabilidade");
        System.out.println("0 - Sair");

        int opcao = teclado.nextInt();
        teclado.nextLine();
        return opcao;
    }

    /**
     * Pausa para leitura de mensagens em console
     * @param teclado Scanner de leitura
     */
    static void pausa(Scanner teclado){
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }
    //#endregion

    //#region Métodos de controle
    /**
     * Cria uma comida de acordo com opções do menu (método "fábrica")
     * @param teclado Scanner de leitura
     * @return Uma comida ou nulo
     */
    static Comida criarComida(Scanner teclado){
        System.out.print("Incluir no pedido(1-Pizza 2-Sanduíche): ");
        int tipo = Integer.parseInt(teclado.nextLine());
        Comida nova;
        switch(tipo){
            case 1: nova = new Pizza(false);
                break;
            case 2: nova = new Sanduiche(false);
                break;
            default: nova= null;
                break;
        }
        if(nova!=null){
            System.out.print("Quantos adicionais: ");
            int quantos = Integer.parseInt(teclado.nextLine());
            for(int i=0; i<quantos;i++) 
                nova.addIngrediente("adicional "+(i+1)+" ");
        }
        return nova;
    }

    /**
     * Apaga o pedido p e cria um novo
     * @param p O pedido a ser apagado
     */
    static void criarNovo(Pedido p){
        p = new Pedido();
        System.out.print("Novo pedido criado. ");
    }
    //#endregion

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        Pedido pedido=null;
        Cliente unicoCliente = new Cliente("Joao", "123456");
        int opcao = -1;
       
        do{
            opcao = menu(teclado);
            limparTela();

            // Este switch pode ser melhorado BASTANTE com a extração de lógica dos cases
            // e modularização em métodos específicos na região de métodos de controle.
            switch(opcao){
                case 1: if(pedido==null || pedido.fechado()){
                            pedido = new Pedido();
                            System.out.print("Novo pedido criado. ");
                        }
                        else
                        System.out.print("Ainda há pedido aberto. ");
                        pausa(teclado);
                    break;
                case 2: if(pedido!=null){
                            Comida aux = criarComida(teclado);
                            if(aux!=null) {
                                if(pedido.addComida(aux))
                                    System.out.println("Adicionado: "+aux);
                                else
                                    System.out.println("Não foi possível adicionar.");
                            }
                            else
                                System.out.print("Inválido. Favor tentar novamente. ");
                        }
                        else
                            System.out.print("Pedido ainda não foi aberto. ");
                        pausa(teclado);
                    break;
                case 3: if(pedido!=null){
                            System.out.println(pedido);
                        }
                        else
                            System.out.print("Pedido ainda não foi aberto. ");
                        pausa(teclado);
                    break;
                case 4: if(pedido!=null){
                            pedido.fecharPedido();
                            double aPagar = pedido.valorTotal()*(1.0 -unicoCliente.desconto());
                            unicoCliente.addPedido(pedido);
                            System.out.println(pedido);
                            System.out.println("Cliente "+unicoCliente.nome+" paga R$ "+aPagar);
                        }
                        else
                            System.out.print("Pedido ainda não foi aberto. ");
                        pausa(teclado);
                    break;
                case 5: 
                    break;
            }
        }while(opcao!=0);

        System.out.println("FIM");
        teclado.close();
    }
}
