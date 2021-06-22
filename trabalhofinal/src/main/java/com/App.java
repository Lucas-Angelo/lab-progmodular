package com;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.veiculo.Caminhao;
import com.veiculo.Carro;
import com.veiculo.Furgao;
import com.veiculo.Rota;
import com.veiculo.Van;
import com.veiculo.Veiculo;
import com.abastecimento.Combustivel;
import com.abastecimento.Tanque;
import com.serializacao.*;

/**
 * Hello world!
 *
 */
public class App 
{
    // #region Utilidades
    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static List<Veiculo> carregarVeiculosDoArquivo(List<Veiculo> listaVeiculos, String arquivo) {
        File f = new File(arquivo);
        if (f.exists() && !f.isDirectory()) {
            LeituraSerializada leitura = new LeituraSerializada();
            leitura.abrirArquivo(arquivo);
            try {
                for (Object objeto : leitura.lerArquivo()) {
                    listaVeiculos.add((Veiculo) objeto);
                }
            } catch (ClassCastException e) {
                System.err.println(
                        "Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Veiculo, não foi possível carregar os veículos.");
            }
            leitura.fecharArquivo();
        }

        return listaVeiculos;
    }

    public static void salvarVeiculosNoArquivo(List<Veiculo> listaVeiculos, String arquivo) {
        EscritaSerializada<Veiculo> escrita = new EscritaSerializada<Veiculo>();
        escrita.abrirArquivo(arquivo);
        try {
            escrita.escrever(listaVeiculos);
        } catch (ClassCastException e) {
            System.err.println(
                    "Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Veiculo, não foi possível salvar os veículos.");
        }
        escrita.fecharArquivo();
    }

    public static int menu(Scanner teclado) {
        limparTela();
        System.out.println("XULAMBS ENTREGAS");
        System.out.println("==========================");
        System.out.println("1 - Novo veículo");
        System.out.println("2 - Nova rota");
        System.out.println("3 - Adicionar rota para um veículo");
        System.out.println("4 - Calcular despesa total de um veículo");
        System.out.println("0 - Salvar e sair");

        int opcao = teclado.nextInt();
        teclado.nextLine();
        return opcao;
    }

    static void pausa(Scanner teclado) {
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }
    // #endregion

    // #region Métodos de controle
    static Optional<Veiculo> buscarVeiculo(Scanner teclado, List<Veiculo> listaVeiculos, Optional<Veiculo> veiculoBuscado, String placaBuscada) {
        Object veiculoFicticioParaBusca = new Carro(placaBuscada);

        veiculoBuscado = Optional.ofNullable(
            listaVeiculos.stream()
                .filter(veiculo -> veiculo.equals(veiculoFicticioParaBusca))
                .findFirst()
                .orElse(null)
        );

        return veiculoBuscado;
    }

    static Optional<Rota> criarNovaRota(Scanner teclado, Optional<Rota> rota) {

        System.out.println("Informe quilômetros a rota possui: ");
        int km = teclado.nextInt();

        rota = Optional.of(new Rota());
        
        if(rota.isPresent())
            System.out.println("Nova rota criada.");
        else
            System.out.println("Não foi possível criar nova a rota.");
 
        return rota;
    }

    static void menuCarros() {
        System.out.println("Tipos de veículos:");
        System.out.println("1 - Carro");
        System.out.println("2 - Furgão");
        System.out.println("3 - Van");
        System.out.println("3 - Caminhão");
    }

    static String getPlacaVeiculo(Scanner teclado, String placa) {
        placa = teclado.nextLine();
        return placa;
    }

    static Optional<Veiculo> criarVeiculo(Scanner teclado, String placa) throws Exception {
        int tipo = 0;

        menuCarros();
        System.out.print("Digite o tipo de veículo: ");
        tipo = teclado.nextInt();
        System.out.println("Digite a placa do veículo: ");


        Optional<Veiculo> novo = Optional.empty();
        switch (tipo) {
            case 1:
                novo = Optional.of(new Carro(placa));
                break;
            case 2:
                novo = Optional.of(new Furgao(placa));
                break;
            case 3:
                novo = Optional.of(new Van(placa));
                break;
            case 4:
                novo = Optional.of(new Caminhao(placa));
                break;
            default:
                throw new Exception("Veículo inválido");
        }

        return novo;
    }

    static void imprimirVeiculo(Optional<Veiculo> veiculo) {
        veiculo.ifPresentOrElse(
            (veiculoLambda)->{
                System.out.println(veiculo);
            },
            ()->{
                System.out.print("Veículo ainda não foi criado.\n");
            }
        );
    }

    // #endregion

    public static void main(String[] args) throws Exception {
        String arquivo = "arqVeiculos.bin";
        Scanner teclado = new Scanner(System.in);

        List<Veiculo> listaVeiculos = new LinkedList<>();
        listaVeiculos = carregarVeiculosDoArquivo(listaVeiculos, arquivo);

        if (listaVeiculos.isEmpty()) {
            listaVeiculos.add(new Carro("LVI-7368"));
            listaVeiculos.add(new Furgao("CSE-6837"));
            listaVeiculos.add(new Van("MNX-2556"));
            listaVeiculos.add(new Caminhao("MUW-7826"));
        }

        Optional<Rota> rota = Optional.empty();
        Optional<Veiculo> veiculo = Optional.empty();
        int opcao = -1;

        String placa = new String();
        do {
            opcao = menu(teclado);
            limparTela();

            switch (opcao) {
                case 1:
                    System.out.print("Digite a placa do novo veículo: ");
                    placa = getPlacaVeiculo(teclado, placa);
                    veiculo = buscarVeiculo(teclado, listaVeiculos, veiculo, placa);
                    if(veiculo.isEmpty()) {
                        veiculo = criarVeiculo(teclado, placa);
                        listaVeiculos.add(veiculo.get());
                    }
                    else
                        System.err.println("Veículo com essa placa já existe.");
                    pausa(teclado);
                    break;
                case 2:
                    rota = criarNovaRota(teclado, rota);
                    pausa(teclado);
                    break;
                case 3:
                    
                    pausa(teclado);
                    break;
                case 4:
                    System.out.print("Digite a placa do veículo desejado: ");
                    placa = getPlacaVeiculo(teclado, placa);
                    veiculo = buscarVeiculo(teclado, listaVeiculos, veiculo, placa);
                    veiculo.ifPresentOrElse(
                        (veiculoLambda)->{
                            System.out.print("A despesa atual desse veículo é: R$" + veiculoLambda.getDespesaAtual());
                        }, 
                        ()->{
                            System.out.println("Veículo não encontrado.");
                        }
                    );
                    break;
                case 0:
                    salvarVeiculosNoArquivo(listaVeiculos, arquivo);
                    break;
            }
        } while (opcao != 0);

        System.out.println("FIM");
        teclado.close();
    }

}
