
import java.io.*;
import java.util.*;

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
 * Exemplo - Restaurante, comidas, pedidos, clientes e fidelidade Versão 0.3
 */
public class App {

    // #region Utilidades
    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static List<Cliente> carregarClientesDoArquivo(List<Cliente> listaClientes, String arquivo) {
        File f = new File(arquivo);
        if (f.exists() && !f.isDirectory()) {
            LeituraSerializada leitura = new LeituraSerializada();
            leitura.abrirArquivo(arquivo);
            try {
                for (Object objeto : leitura.lerArquivo()) {
                    listaClientes.add((Cliente) objeto);
                }
            } catch (ClassCastException e) {
                System.err.println("Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Cliente, não foi possível carregar os clientes.");
            }
            leitura.fecharArquivo();
        }

        return listaClientes;
    }

    public static void salvarClientesNoArquivo(List<Cliente> listaClientes, String arquivo) {
        EscritaSerializada<Cliente> escrita = new EscritaSerializada<Cliente>();
        escrita.abrirArquivo(arquivo);
        try {
            escrita.escrever(listaClientes);
        } catch (ClassCastException e) {
            System.err.println("Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Cliente, não foi possível carregar os clientes.");
        }
        escrita.fecharArquivo();
    }

    /**
     * Menu para o restaurante
     * 
     * @param teclado Scanner de leitura
     * @return Opção do usuário (int)
     */
    public static int menu(Scanner teclado) {
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
     * 
     * @param teclado Scanner de leitura
     */
    static void pausa(Scanner teclado) {
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }
    // #endregion

    // #region Métodos de controle
    /**
     * Cria uma comida de acordo com opções do menu (método "fábrica")
     * 
     * @param teclado Scanner de leitura
     * @return Uma comida ou nulo
     */
    static Comida criarComida(Scanner teclado) {
        System.out.print("Incluir no pedido(1-Pizza 2-Sanduíche): ");
        int tipo = 0;
        try {
            tipo = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido inserido");
        }

        Comida nova;
        switch (tipo) {
            case 1:
                nova = new Pizza(false);
                break;
            case 2:
                nova = new Sanduiche(false);
                break;
            default:
                nova = null;
                break;
        }
        if (nova != null) {
            System.out.print("Quantos adicionais: ");
            int quantos = 0;
            try {
                quantos = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Valor inválido inserido");
            }
            for (int i = 0; i < quantos; i++)
                nova.addIngrediente("adicional " + (i + 1) + " ");
        }
        return nova;
    }

    /**
     * Apaga o pedido p e cria um novo
     * 
     * @param p O pedido a ser apagado
     */
    static void criarNovo(Pedido p) {
        p = new Pedido();
        System.out.print("Novo pedido criado. ");
    }
    // #endregion

    public static void main(String[] args) throws Exception {
        String arquivo = "arqClientes.bin";
        Scanner teclado = new Scanner(System.in);

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes = carregarClientesDoArquivo(listaClientes, arquivo);

        if (listaClientes.isEmpty()) {
            listaClientes.add(new Cliente("Thom Andrews", "123.456.789-00"));
            listaClientes.add(new Cliente("Jeff Gordon", "234.567.890-11"));
            listaClientes.add(new Cliente("Nick Hill", "345.678.901-22"));
            listaClientes.add(new Cliente("Kim Harris", "456.789.012-33"));
            listaClientes.add(new Cliente("Bianca Jersey", "557.890.123-44"));
        }

        Pedido pedido = null;
        Cliente unicoCliente = null;
        int opcao = -1;

        do {
            opcao = menu(teclado);
            limparTela();

            // Este switch pode ser melhorado BASTANTE com a extração de lógica dos cases
            // e modularização em métodos específicos na região de métodos de controle.
            switch (opcao) {
                case 1:
                    if (pedido == null || pedido.fechado()) {
                        while (unicoCliente == null) {
                            System.out.println("Digite o CPF do Cliente (Ou digite 0 para sair)");
                            String cpf = teclado.nextLine().replaceAll("[^0-9]", "");
                            if (cpf.equals("0"))
                                break;
                            unicoCliente = listaClientes.stream().filter(cliente -> cpf.equals(cliente.getCPF()))
                                    .findFirst().orElse(null);
                            if (unicoCliente == null)
                                System.out.println("Cliente não cadastrado");
                        }

                        if (unicoCliente != null) {
                            pedido = new Pedido();
                            System.out.print("Novo pedido criado. ");
                        } else
                            System.out.print("Pedido não criado. Cliente não definido \n");
                    } else
                        System.out.print("Ainda há pedido aberto. ");
                    pausa(teclado);
                    break;
                case 2:
                    if (pedido != null) {
                        Comida aux = criarComida(teclado);
                        if (aux != null) {
                            if (pedido.addComida(aux))
                                System.out.println("Adicionado: " + aux);
                            else
                                System.out.println("Não foi possível adicionar.");
                        } else
                            System.out.print("Inválido. Favor tentar novamente. ");
                    } else
                        System.out.print("Pedido ainda não foi aberto. ");
                    pausa(teclado);
                    break;
                case 3:
                    if (pedido != null) {
                        System.out.println(pedido);
                    } else
                        System.out.print("Pedido ainda não foi aberto. ");
                    pausa(teclado);
                    break;
                case 4:
                    if (pedido != null) {
                        pedido.fecharPedido();
                        double aPagar = pedido.valorTotal() * (1.0 - unicoCliente.desconto());
                        unicoCliente.addPedido(pedido);
                        System.out.println(pedido);
                        System.out.println("Cliente " + unicoCliente.nome + " paga R$ " + aPagar);
                    } else
                        System.out.print("Pedido ainda não foi aberto. ");
                    pausa(teclado);
                    break;
                case 5:
                    break;
                case 0:
                    salvarClientesNoArquivo(listaClientes, arquivo);
                    break;
            }
        } while (opcao != 0);

        System.out.println("FIM");
        teclado.close();
    }
}
