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

 /** Cliente com 10% de desconto.
  *  Demonstração de composição vs herança
  */

public abstract class Comida {
  
    /** Por enquanto, todos os adicionais tem o mesmo valor */
    protected static final double VALOR_ADICIONAL;
    
    /** Descrição ou nome da comida */
    protected String descricao;
	  
    /** Vetor que armazena o nome dos adicionais */
    protected String[] adicionais;
	  
    /** Variável de controle para a quantidade de adicionais (vetor)*/
    protected int qtAdicionais;
    
    /** Preco inicial da comida, sem adicionais*/
    protected double precoBase;
      
    
  static{
    VALOR_ADICIONAL = 1.99;
  }

  /**
   * Configura o máximo de ingredientes de uma comida
   * @param maxIngred Número máximo de ingredientes (inteiro positivo)
   */
  private void setMaxAdicionais(int maxIngred){
    if(maxIngred>0)
      this.adicionais = new String[maxIngred];
    else
      this.adicionais = new String[1];
  }
  
  /**
   * Construtor.
   * @param base Preço base
   * @param maxIngred Máximo de adicionais
   */
  public Comida(double base, int maxIngred){
    this.qtAdicionais = 0;
    
    this.setMaxAdicionais(maxIngred);
    
    if(base < 10.0) 
        this.precoBase = 10.0;
    else 
        this.precoBase = base;
  }

  /**
   * Inicia a string com o nome da comida.Uso interno.
   */
	protected void setDescricao(String qual) { 
	    this.descricao = qual;
	}

  @Override
  /**
   * Descrição: nome, adicionais e valor final.
   */
    public String toString(){
        String aux = this.descricao;
        StringBuilder desc = new StringBuilder(aux);
        for(int i=0; i<this.qtAdicionais; i++){
          desc.append(", com "+this.adicionais[i]);
        }
        desc.append("- Preço: R$ "+this.precoFinal()+"\n");
        aux = desc.toString();
        return aux;
    }

    /**
     * Get para quantidade de adicionais
     * @return Quantidade de adicionais (int)
     */
	public int getQtAdicionais() {
		return qtAdicionais;
	}

  /**
   * Get para o máximo de adicionais. Uso interno.
   */
  private int maxAdicionais(){
      return this.adicionais.length;
  }

  /**
   * Adiciona um ingrediente até o limite
   * @param qual Descrição do ingrediente
   * @return Booleano indicando se houve a adição do ingrediente
   */
	public boolean addIngrediente(String qual) {
    int limite = maxAdicionais();
    if(this.qtAdicionais<limite){
       this.adicionais[this.qtAdicionais] = qual;
       this.qtAdicionais++;
       return true;
    }
    else
      return false;
	}
	
  /**
   * Método abstrato para retorno do preço final (base + adicionais diversos)
   * @return Valor double (preço)
   */
	public abstract double precoFinal(); //TODAS as comidas terao preço final

}
