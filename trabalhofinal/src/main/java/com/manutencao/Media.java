package com.manutencao;

import java.io.Serializable;
import java.util.Objects;
 /** 
 *  Classe do tipo de manutencao : Media
 *  classe capaz de responder quanto tempo falta para a proxima manutencao 
 *  e executar manutencao para o seu tipo
 *  Serializavel e comparavel
 *  @author Marcos Felipe.
 *  @version 1.0
 */

public class Media implements IManutencao, Serializable,Comparable<Media> {
    protected int ultimaManun;
    private static final long serialVersionUID = 101L;
    static final int kmManuteMedia = 12000;

     /** Construtor vazio de Media que irá inicializar os valores por meio da função init().
    * @author Marcos Felipe.
    * @return Media- O objeto de Media instânciado.
    */
    public Media (){
        init(0);
    }

    /** Construtor de Media que irá inicializar os valores por meio da função init().
    * o param @ultimaManun sera preenchido com o kmAtual do veiculo passado por parametro
    * @author Marcos Felipe.
    * @return Media- O objeto de Media instânciado.
    */
    public Media(int kmRodadoAtual){
        init (kmRodadoAtual);
    }
    
    /** Método init para inicializar os atributos comuns dos contrutores dos tipos de Media quando instânciar uma nova manut Media.
    * @param ultimaManun km atual do carro, passado por quem invocou o metodo
    * @author Marcos Felipe.
    */

    private void init(int ultimaManun ){

        this.ultimaManun = ultimaManun;
    }

    /** Metodo que retorna em Int a quantidade de kms necessário para a proxima manutencao do seu tipo
    * @param int kmManutencao int - Quantidade de quilômetros percorridos.
    * @author Marcos Felipe.
    * @return int qtd de kms para a proxima manutencao, ou quantidade de km's em que a manutencao está atrasada
    */

    @Override
    public int proximaManutencao(int kmManutencao) {
        return  ( this.ultimaManun - kmManutencao );
    }
    
    /** Metodo um booleano informado se a manutencao foi um sucesso
    * @param int kmManutencao int - Quantidade de quilômetros percorridos.
    * necessário informar a qtd de km's pois nessa parte ele va transferir o valor de km's rodados
    * para ultima manutencao
    * @return boolean se a manutencao foi executada com sucesso
    * @author Marcos Felipe.
    */

    public boolean executaManutencao(int kmManutencao){
        ultimaManun = kmManutencao;
        return (this.ultimaManun == kmManutencao);
    }
    
    /** Imprime os dados de quando foi a ultima manut.
    * @author Marcos Felipe.
    */
    @Override
    public String toString() {
        return "Ultima Manutencao em: "+this.ultimaManun+" km's.";
    }

    /** Verifica se um objeto é igual a esta Media.
    * @param o Objeto - O objeto a ser verificado.
    * @author Marcos Felipe
    * @throws ClassCastException Caso não seja um objeto do tipo Media.
    * @throws NullPointerException ponteiro nulo.
    */
    @Override
    public boolean equals(Object o) {
        try{
            if (this == o) return true;
            Media media = (Media) o;
            return this.ultimaManun == media.ultimaManun;
        }catch(ClassCastException e){
            System.err.print("Cast inválido");
            return false;
        }catch (NullPointerException e){
            System.err.print("Ponteiro Nulo");
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ultimaManun);
    }

    /** Verifica se um objeto é igual, menor ou maior do que este obj Media(pelo marametro ultima manut).
    * @param o Objeto - O objeto a ser verificado.
    * @author Marcos Felipe.
    * @throws ClassCastException Caso não seja um objeto do tipo Media.
    * @throws NullPointerException ponteiro nulo.
    */
    @Override
    public int compareTo(Media o) {
        try{
            Media media = (Media) o;
            return this.ultimaManun == media.ultimaManun? 0 : (this.ultimaManun < media.ultimaManun ? 1: -1);
        }catch(ClassCastException e){
            System.err.print("Cast inválido");
            return -1;
        }catch (NullPointerException e){
            System.err.print("Ponteiro Nulo");
            return -1;
        }
    }

}
