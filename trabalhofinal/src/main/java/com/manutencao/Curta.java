package com.manutencao;

import java.io.Serializable;
import java.util.Objects;
 /** 
 *  Classe do tipo de manutencao : Curta
 *  classe capaz de responder quanto tempo falta para a proxima manutencao 
 *  e executar manutencao para o seu tipo
 *  Serializavel e comparavel
 *  @author Marcos Felipe.
 *  @version 1.0
 */

public class Curta implements IManutencao, Serializable,Comparable<Curta> {

    protected int ultimaManun;
    private static final long serialVersionUID = 100L;
    static final int kmManutCurta = 10000;

    /** Construtor vazio de Curta que irá inicializar os valores por meio da função init().
    * @author Marcos Felipe.
    * @return Curta- O objeto de Curta instânciado.
    */
    public Curta (){
        init(0);
    }
    
    /** Construtor de Curta que irá inicializar os valores por meio da função init().
    * o param @ultimaManun sera preenchido com o kmAtual do veiculo passado por parametro
    * @author Marcos Felipe.
    * @return Curta- O objeto de Curta instânciado.
    */
    public Curta(int kmRodadoAtual){
        init (kmRodadoAtual);
    }
   
    /** Método init para inicializar os atributos comuns dos contrutores dos tipos de Curta quando instânciar uma nova manut Curta.
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

    /** Verifica se um objeto é igual a esta Curta.
    * @param o Objeto - O objeto a ser verificado.
    * @author Marcos Felipe
    * @throws ClassCastException Caso não seja um objeto do tipo Curta.
    * @throws NullPointerException ponteiro nulo.
    */

    @Override
    public boolean equals(Object o) {
        try{
            if (this == o) return true;
            Curta curta = (Curta) o;
            return this.ultimaManun == curta.ultimaManun;
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
    /** Verifica se um objeto é igual, menor ou maior do que este obj Curta(pelo marametro ultima manut).
    * @param o Objeto - O objeto a ser verificado.
    * @author Marcos Felipe.
    * @throws ClassCastException Caso não seja um objeto do tipo Curta.
    * @throws NullPointerException ponteiro nulo.
    */
    @Override
    public int compareTo(Curta o) {
        try{
            Curta curta = (Curta) o;
            return this.ultimaManun == curta.ultimaManun? 0 : (this.ultimaManun < curta.ultimaManun ? 1: -1);
        }catch(ClassCastException e){
            System.err.print("Cast inválido");
            return -1;
        }catch (NullPointerException e){
            System.err.print("Ponteiro Nulo");
            return -1;
        }
    }

}