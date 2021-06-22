package com.veiculo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.Test;

public class RotaTest {
    @Test
    public void RotaCriada(){
        Date dataAgora = new Date();
        Rota rota = new Rota(dataAgora, 20 );
        assertEquals(dataAgora, rota.getData());
        assertEquals(20, rota.getKmRota());
    }
    @Test
    public void RotasIguais(){
        Date dataAgora = new Date();
        Rota rota1 = new Rota(dataAgora, 20 );
        Rota rota2 = new Rota(dataAgora, 20 );
        assertEquals(rota1, rota2);
    }
    @Test
    public void RotasDiferentes(){
        Rota rota1 = new Rota(new Date( 1624394369 ), 20 );
        Rota rota2 = new Rota(new Date( 1624394369 ), 20 );
        assertNotEquals(rota1, rota2);
    }
}
