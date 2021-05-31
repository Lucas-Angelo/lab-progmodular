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

 /** Classe Pizza, herda de Comida */
public class Pizza extends Comida{
 
    /** Preço base da Pizza */
    private static final double PRECO_PIZZA = 30.0;
    /** Máximo de adicionais de uma Pizza*/
    private static final int MAX_ADICIONAIS = 10;
    /** Multiplicador de adicionais (regra própria da pizza) */
    private static final double MULTIPLICADOR_ADICIONAIS = 2.0;

    /** A pizza pode ter borda recheada */
    private boolean bordaRecheada;

    /**
     * Construtor. Indica pizza com borda recheada ou não
     * @param borda Booleano para borda recheada
     */
    public Pizza(boolean borda){
        super(PRECO_PIZZA,MAX_ADICIONAIS); //construtor da classe mãe
        this.setDescricao("Pizza ");
        this.bordaRecheada = borda;
    }
    

    @Override
    /**
     * Preço final. Tem regras próprias do multiplicador e da borda recheada.
     */
    public double precoFinal() {

        double precoFinal = precoBase+(this.qtAdicionais * VALOR_ADICIONAL * MULTIPLICADOR_ADICIONAIS);
        if(this.bordaRecheada)
            precoFinal +=7.50;
        return precoFinal;
	}

}
