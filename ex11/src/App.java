
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
                System.err.println(
                        "Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Cliente, não foi possível carregar os clientes.");
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
            System.err.println(
                    "Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Cliente, não foi possível carregar os clientes.");
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

    static Optional<Cliente> buscarCliente(Scanner teclado, List<Cliente> listaClientes) {
        Optional<Cliente> cliente = Optional.empty();

        System.out.print("Digite o nome do cliente: ");
        String nome = teclado.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = teclado.nextLine().replaceAll("[^0-9]", "");

        cliente = listaClientes.stream().filter(clienteStream -> new Cliente(nome, cpf).equals(clienteStream)).findFirst();

        if(cliente.isEmpty()) {
            System.err.println("Cliente não cadastrado.");
        }

        return cliente;
    }

    /**
     * Apaga o pedido p e cria um novo
     * 
     * @param p O pedido a ser apagado
     */
    static Optional<Pedido> criarNovo(Optional<Pedido> pedido) {
        if(pedido.isEmpty() || pedido.get().fechado()) {
            pedido = Optional.of(new Pedido());
            System.out.println("Novo pedido criado.");
        } else {
            System.err.println("Erro: é necessário fechar o pedido aberto primeiro.");
        }  
        return pedido;
    }

    /**
     * Cria uma comida de acordo com opções do menu (método "fábrica")
     * 
     * @param teclado Scanner de leitura
     * @return Uma comida ou nulo
     */
    static Optional<Comida> criarComida(Scanner teclado) throws Exception {
        int tipo = 0;
        System.out.println("Menu comidas:");
        System.out.println("1 - Pizza");
        System.out.println("2 - Sanduíche");
        System.out.print("Incluir no pedido: ");
        try {
            tipo = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido inserido");
        }

        Optional<Comida> nova = Optional.empty();
        switch (tipo) {
            case 1:
                boolean borda = false;
                System.out.println("\nDeseja borda recheada?");
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                System.out.print("Digite a opção: ");
                try {
                    borda = (Integer.parseInt(teclado.nextLine()) == 1) ? true : false;
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido inserido");
                }
                nova = Optional.of(new Pizza(borda));
                break;
            case 2:
                boolean dobro = false;
                System.out.println("\nDeseja duas carnes?");
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                System.out.print("Digite a opção: ");
                try {
                    dobro = (Integer.parseInt(teclado.nextLine()) == 1) ? true : false;
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido inserido");
                }
                nova = Optional.of(new Sanduiche(dobro));
                break;
            default:
                throw new Exception("Comida inválida");
        }

        System.out.print("\nQuantos adicionais: ");
        int quantos = 0;
        try {
            quantos = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido inserido");
        }
        try {
            for (int i = 0; i < quantos; i++)
                nova.get().addIngrediente("adicional " + (i + 1) + " ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return nova;
    }

    static void imprimirPedido(Optional<Pedido> pedido) {
        if (!pedido.isEmpty()) {
            System.out.println(pedido.get());
        } else
            System.out.print("Pedido ainda não foi aberto. ");
    }

    static Optional<Pedido> fecharPedido(Optional<Pedido> pedido, Optional<Cliente> cliente) {
        if (!pedido.isEmpty()) {
            try {
                pedido.get().fecharPedido();
                double aPagar = pedido.get().valorTotal() * (1.0 - cliente.get().desconto());
                cliente.get().addPedido(pedido.get());
                System.out.println(pedido.get());
                System.out.println("Cliente " + cliente.get().nome + " paga R$ " + aPagar);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else
            System.out.print("Pedido ainda não foi aberto. ");
        return pedido;
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

        Optional<Pedido> pedido = Optional.empty();
        Optional<Cliente> cliente = Optional.empty();
        int opcao = -1;

        do {
            opcao = menu(teclado);
            limparTela();

            // Este switch pode ser melhorado BASTANTE com a extração de lógica dos cases
            // e modularização em métodos específicos na região de métodos de controle.
            switch (opcao) {
                case 1:
                    cliente = buscarCliente(teclado, listaClientes);
                    if(!cliente.isEmpty()) {
                        pedido = criarNovo(pedido);
                    } else {
                        System.err.println("Não foi possível criar o pedido.");
                    }
                    pausa(teclado);
                    break;
                case 2:
                    Optional<Comida> aux = criarComida(teclado);
                    if (!pedido.isEmpty()) {
                        try {
                            pedido.get().addComida(aux.get());
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    } else
                        System.out.print("Pedido ainda não foi aberto. ");
                    pausa(teclado);
                    break;
                case 3:
                    imprimirPedido(pedido);
                    pausa(teclado);
                    break;
                case 4:
                    pedido = fecharPedido(pedido, cliente);
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
