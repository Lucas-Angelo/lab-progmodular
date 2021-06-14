
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
        int idAtual = 0;
        double totalVendido=0;
        List<Pedido> pedidos;
        for (Cliente cliente : listaClientes) {
            pedidos = cliente.getPedidos();
            idAtual += pedidos.size();
            for (Pedido pedido : pedidos) {
                totalVendido += pedido.valorTotal();
            }
        }
        Pedido.setIdAtual(idAtual);
        Pedido.setValorTotalVendido(totalVendido);

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
        Optional<Cliente> c = Optional.empty();
        while (c.isEmpty()) {
            System.out.print("Digite o CPF do Cliente (Ou digite 0 para sair): ");
            String cpf = teclado.nextLine().replaceAll("[^0-9]", "");
            if (cpf.equals("0"))
                break;
            
            c =  Optional.ofNullable(
                listaClientes.stream()
                    .filter(cliente -> cpf.equals(cliente.getCPF()))
                    .findFirst()
                    .orElse(null)
            );

            if (c.isEmpty())
                System.out.println("Cliente não cadastrado.");
        }
        return c;
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
     * @throws Exception
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
                    borda = (Integer.parseInt(teclado.nextLine()) == 1);
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
                    dobro = (Integer.parseInt(teclado.nextLine()) == 1);
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
                nova.get().addIngrediente("Adicional: " + (i + 1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return nova;
    }

    static void imprimirPedido(Optional<Pedido> pedido) {
        pedido.ifPresentOrElse(
            (pedidoLambda)->{
                System.out.println(pedido.get());
            },
            ()->{
                System.out.print("Pedido ainda não foi aberto.\n");
            }
        );
    }

    static Optional<Pedido> fecharPedido(Optional<Pedido> pedido, Optional<Cliente> cliente) {
        pedido.ifPresentOrElse(
            (pedidoLambda)->{
                try {
                    pedidoLambda.fecharPedido();
                    double aPagar = pedidoLambda.valorTotal() * (1.0 - cliente.get().desconto());
                    cliente.get().addPedido(pedidoLambda);
                    System.out.println(pedidoLambda);
                    System.out.println("Cliente " + cliente.get().nome + " paga R$ " + aPagar);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }, ()->{
                System.out.print("Pedido ainda não foi aberto.\n");
            }
        );
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

            switch (opcao) {
                case 1:
                    if(pedido.isEmpty() || pedido.get().fechado()) {
                        cliente = buscarCliente(teclado, listaClientes);
                        if(!cliente.isEmpty())
                            pedido = criarNovo(pedido);
                        else
                            System.err.println("Não foi possível criar o pedido.");
                    } else
                        System.err.println("Não é possível criar um pedido enquanto outro está aberto.");
                    pausa(teclado);
                    break;
                case 2:
                    pedido.ifPresentOrElse(
                        (pedidoLambda)->{
                            try {
                                Optional<Comida> aux = criarComida(teclado);
                                pedidoLambda.addComida(aux.get());
                            } catch (Exception e) {
                                System.err.println(e.getMessage());
                            }
                        }, 
                        ()->{
                            System.out.print("Pedido ainda não foi aberto.\n");
                        }
                    );
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
                    System.out.println(Pedido.valorTotalVendido());
                    pausa(teclado);
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
