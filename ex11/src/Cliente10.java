import java.time.LocalDate;

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
public class Cliente10 implements IFidelidade {
    /** Data de referência. */
    LocalDate data = LocalDate.now();
    /** Data de referência. */
    Data hoje = new Data(data.getDayOfMonth(),data.getMonthValue(), data.getYear());

    
    /**
     * Desconto do cliente: concedido com R$100 nos últimos 31 dias ou 25 pedidos no ano
     */
    @Override
    public double desconto(Pedido[] pedidos) {
        double desconto = 0.0;
        double valorPedidos=0.0;
        int totalPedidos = 0;
        for (Pedido pedido : pedidos) {
            if(pedido!=null){
                Data auxMes = pedido.dataPedido.acrescentaDias(31);
                Data auxAno = pedido.dataPedido.acrescentaDias(366);
                if(!hoje.maisRecente(auxMes))
                    valorPedidos += pedido.valorTotal();
                if(!hoje.maisRecente(auxAno))
                    totalPedidos++;
            }
        }

        if(valorPedidos>=100.00 || totalPedidos>=25)
            desconto = 0.1;
        
        
        return desconto;
    }
    
    
}
