import java.io.*;
import java.util.*;

public class LeituraSerializada {
    private FileInputStream arqLeitura;
    private ObjectInputStream streamLeitura;

    public void abrirArquivo(String nomeDoArquivo) {
        try {
            this.arqLeitura = new FileInputStream(nomeDoArquivo);
            this.streamLeitura = new ObjectInputStream(arqLeitura);
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo/Diretório do arquivo não encontrado, não foi possível carregar o arquivo.");
        } catch (SecurityException e) {
            System.err.println("Erro: Sem permissão para acessar o arquivo/diretório, não foi possível carregar o arquivo.");
        } catch (StreamCorruptedException e) {
            System.out.println("Erro: Instância da stream corrompida, não foi possível carregar o arquivo.");
        } catch (IOException e) {
            System.err.println("Erro: I/O ao abrir o arquivo, não foi possível carregar o arquivo.");
        } catch (NullPointerException e) {
            System.err.println("Erro: FileInputStream não instanciado para o ObjectInputStream (Ponteiro nulo), não foi possível carregar o arquivo.");
        }
    }

    public void fecharArquivo() {
        try {
            this.streamLeitura.close();
            this.arqLeitura.close();
        } catch (IOException e) {
            System.err.println("Erro: Falha de I/O, não foi possível fechar o arquivo.");
        }
    }

    public List<Object> lerArquivo() {
        List<Object> lista = new ArrayList<Object>();

        boolean cont = true;
        try {
            while (cont) {
                lista.add(streamLeitura.readObject());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Falha ao fazer o casting da classe instanciada no arquivo, não foi possível ler o arquivo.");
        } catch (InvalidClassException e) {
            System.err.println("Erro: Classe utilizada na serialização está com erro, não foi possível ler o arquivo.");
        } catch (StreamCorruptedException e) {
            System.err.println("Erro: Stream instanciada está corrompida, não foi possível ler o arquivo.");
        } catch (OptionalDataException e) {
            System.err.println("Erro: Dados primitivos foram encontrados na stream em vez de objetos, não foi possível ler o arquivo.");
        } catch (EOFException e) {
            cont = false;
        } catch (IOException e) {
            System.err.println("Erro: Falha de I/O, não foi possível ler o arquivo.");
        }

        return lista;
    }
}
