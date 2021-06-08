import java.io.*;
import java.util.*;

public class EscritaSerializada<T> {
    private FileOutputStream arqEscrita;
    private ObjectOutputStream streamEscrita;

    public void abrirArquivo(String nomeDoArquivo) {
        try {
            this.arqEscrita = new FileOutputStream(nomeDoArquivo);
            this.streamEscrita = new ObjectOutputStream(arqEscrita);
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo/Diretório do arquivo não encontrado, não foi possível carregar o arquivo.");
        } catch (SecurityException e) {
            System.err.println("Erro: Sem permissão para acessar o arquivo/diretório, não foi possível carregar o arquivo.");
        } catch (IOException e) {
            System.err.println("Erro: I/O ao abrir o arquivo, não foi possível carregar o arquivo.");
        } catch (NullPointerException e) {
            System.err.println("Erro: FileInputStream não instanciado para o ObjectInputStream (Ponteiro nulo), não foi possível carregar o arquivo.");
        }
    }

    public void fecharArquivo() {
        try {
            this.streamEscrita.close();
            this.arqEscrita.close();
        } catch (IOException e) {
            System.err.println("Erro: Falha de I/O, não foi possível fechar o arquivo.");
        }
    }

    public void escrever(List<T> objetos) {
        try {
            for (T t : objetos) {
                this.streamEscrita.writeObject(t);
            }
        } catch (InvalidClassException e) {
            System.err.println("Erro: Classe do objeto para salvar inválida, não foi possível escrever no arquivo.");
        } catch (NotSerializableException e) {
            System.err.println("Erro: Classe não serializavel não pode ser salva serializada, não foi possível escrever no arquivo.");
        } catch (IOException e) {
            System.err.println("Erro: I/O no arquivo, não foi possível escrever no arquivo.");
        }
    }
}
