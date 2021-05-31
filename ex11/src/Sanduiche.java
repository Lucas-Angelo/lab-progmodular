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

 /** Classe Sanduíche, herda de Comida */
public class Sanduiche extends Comida{
 
    /** Preço base do sanduíche */
    private static final double PRECO_SANDUICHE = 12.0;
    /** Máximo de adicionais de um sanduíche */
    private static final int MAX_ADICIONAIS = 7;

    /** Regra própria: pode ter o dobro de carne */
    private boolean dobroDeCarne;
    
    /**
     * Construtor: indica se tem o dobro de carne
     */
    public Sanduiche(boolean dobro){
        super(PRECO_SANDUICHE, MAX_ADICIONAIS); //construtor da classe mãe
        this.setDescricao("Sanduíche ");
        this.dobroDeCarne = dobro;
        if(this.dobroDeCarne)
            this.descricao += "com duas carnes ";
    }

    /**
     * Calcula o preço final, com a regra própria do dobro de carne
     */
    @Override
    public double precoFinal() {        
        double precoFinal = precoBase+(this.qtAdicionais * VALOR_ADICIONAL);
        if(this.dobroDeCarne)        
            precoFinal+=5.0;
        return precoFinal;
	}
}
