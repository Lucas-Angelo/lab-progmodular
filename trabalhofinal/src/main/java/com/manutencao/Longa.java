package com.manutencao;

import java.io.Serializable;
import java.util.Objects;


 /** 
 *  Classe do tipo de manutencao : Longa
 *  classe capaz de responder quanto tempo falta para a proxima manutencao 
 *  e executar manutencao para o seu tipo
 *  Serializavel e comparavel
 *  @author Marcos Felipe.
 *  @version 1.0
 */
public class Longa implements IManutencao, Serializable,Comparable<Longa> {
    protected int ultimaManun;
    private static final long serialVersionUID = 102L;
    static final int kmManuteLonga = 20000;


    /** Construtor vazio de Longa que irá inicializar os valores por meio da função init().
    * @author Marcos Felipe.
    * @return Longa- O objeto de Longa instânciado.
    */
    public Longa(){
        init(0);
    }

    /** Construtor de Longa que irá inicializar os valores por meio da função init().
    * o param @ultimaManun sera preenchido com o kmAtual do veiculo passado por parametro
    * @author Marcos Felipe.
    * @return Longa- O objeto de Longa instânciado.
    */
    public Longa(int kmRodadoAtual){
        init (kmRodadoAtual);
    }

    /** Método init para inicializar os atributos comuns dos contrutores dos tipos de Longa quando instânciar uma nova manut Longa.
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


    /** Verifica se um objeto é igual a esta Longa.
    * @param o Objeto - O objeto a ser verificado.
    * @author Marcos Felipe
    * @throws ClassCastException Caso não seja um objeto do tipo Longa.
    * @throws NullPointerException ponteiro nulo.
    */
    @Override
    public boolean equals(Object o) {
        try{
            if (this == o) return true;
            Longa longa = (Longa) o;
            return this.ultimaManun == longa.ultimaManun;
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

   
    /** Verifica se um objeto é igual, menor ou maior do que este tanque.
    * @param o Objeto - O objeto a ser verificado.
    * @author Marcos Felipe.
    * @throws ClassCastException Caso não seja um objeto do tipo Tanque.
    * @throws NullPointerException ponteiro nulo.
    */
    @Override
    public int compareTo(Longa o) {
        try{
            Longa longa = (Longa) o;
            return this.ultimaManun == longa.ultimaManun? 0 : (this.ultimaManun < longa.ultimaManun ? 1: -1);
        }catch(ClassCastException e){
            System.err.print("Cast inválido");
            return -1;
        }catch (NullPointerException e){
            System.err.print("Ponteiro Nulo");
            return -1;
        }
    }

}
