package com;

import static org.junit.Assert.assertTrue;

import com.manutencao.Curta;
import com.manutencao.Longa;
import com.manutencao.Media;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ManutTeste 
{
 
    //--------------------------------------------------------------------------

    @Test
    public void testContructorCurta()
    {
        Curta c = new Curta();
        assertTrue( c!=null );
    }

    @Test
    public void testeProximaManutCurta()
    {
        Curta c = new Curta(500000);
        int nextManut = c.proximaManutencao(10000);
               
        assertTrue( nextManut>0 );
    }

    @Test
    public void testeExecutaManutCurta()
    {
        Curta c = new Curta(500000);
        boolean manutExecutada = c.executaManutencao(10000);
        assertTrue( manutExecutada == true );
    }
    //-------------------------------------------------------------------------

    @Test
    public void testContructorMedia()
    {
        Media m = new Media();
        assertTrue( m!=null );
    }

    @Test
    public void testeProximaManutMedia()
    {
        Media m = new Media(500000);
        int nextManut = m.proximaManutencao(10000);
               
        assertTrue( nextManut>0 );
    }

    @Test
    public void testeExecutaManutMedia()
    {
        Media m = new Media(500000);
        boolean manutExecutada = m.executaManutencao(10000);
        assertTrue( manutExecutada == true );
    }
    //-----------------------------------------------------------------------------

    @Test
    public void testContructorLonga()
    {
        Longa l = new Longa();
        assertTrue( l!=null );
    }

    @Test
    public void testeProximaManutLonga()
    {
        Longa l = new Longa(500000);
        int nextManut = l.proximaManutencao(10000);
               
        assertTrue( nextManut>0 );
    }

    @Test
    public void testeExecutaManutLonga()
    {
        Longa c = new Longa(500000);
        boolean manutExecutada = c.executaManutencao(10000);
        assertTrue( manutExecutada == true );
    }
    //----------------------------------------------------------------------------------


}
