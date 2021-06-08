import java.io.Serializable;
import java.util.ArrayList;

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
 * Cliente com 10% de desconto. Demonstração de composição vs herança
 */

public abstract class Comida implements Serializable {

  /** Por enquanto, todos os adicionais tem o mesmo valor */
  protected static final double VALOR_ADICIONAL;

  /** Descrição ou nome da comida */
  protected String descricao;

  /** Vetor que armazena o nome dos adicionais */
  protected ArrayList<String> adicionais;

  /** Preco inicial da comida, sem adicionais */
  protected double precoBase;

  static {
    VALOR_ADICIONAL = 1.99;
  }

  private void init(String desc, double base) {
    this.descricao = desc;
    if (base < 10.0)
      this.precoBase = 10.0;
    else
      this.precoBase = base;
    this.adicionais = new ArrayList<String>(maxAdicionais());
  }

  /**
   * Construtor.
   * 
   * @param base      Preço base
   */
  public Comida(String desc, double base) {
    init(desc, base);
  }

  /**
   * Adiciona um ingrediente até o limite
   * 
   * @param qual Descrição do ingrediente
   * @return Booleano indicando se houve a adição do ingrediente
   */
  public boolean addIngrediente(String qual) {
    int qtAdicionais = getQtAdicionais();
    int limite = maxAdicionais();
    if (qtAdicionais < limite) {
      this.adicionais.add(qual);
      return true;
    } else
      return false;
  }

  /**
   * Método abstrato para retorno do preço final (base + adicionais diversos)
   * 
   * @return Valor double (preço)
   */
  public abstract double precoFinal(); // TODAS as comidas terao preço final

  /**
   * Get para o máximo de adicionais. Uso interno.
   */
  protected abstract int maxAdicionais();

  /**
   * Get para quantidade de adicionais
   * 
   * @return Quantidade de adicionais (int)
   */
  public int getQtAdicionais() {
    return this.adicionais.size();
  }

  @Override
  /**
   * Descrição: nome, adicionais e valor final.
   */
  public String toString() {
    String aux = this.descricao;
    StringBuilder desc = new StringBuilder(aux + "\n");
    if(!this.adicionais.isEmpty())
      desc.append("Adicionais: ");
    for (String adicional : this.adicionais) {
      desc.append(adicional + ", ");
    }
    desc.append("\nPreço: R$ " + this.precoFinal() + ".\n");
    aux = desc.toString();
    return aux;
  }

}
