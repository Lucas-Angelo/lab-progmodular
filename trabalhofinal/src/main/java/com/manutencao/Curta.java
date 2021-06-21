package com.manutencao;

import java.io.Serializable;
import java.util.Objects;

public class Curta implements IManutencao, Serializable,Comparable<Curta> {

    protected int ultimaManun;
    private static final long serialVersionUID = 100L;
    static final int kmManutCurta = 10000;

    public Curta (){
        init(0);
    }
    public Curta(int kmRodadoAtual){
        init (kmRodadoAtual);
    }

    private void init(int ultimaManun ){

        this.ultimaManun = ultimaManun;
    }


    @Override
    public int proximaManutencao(int kmManutencao) {
        return  ( this.ultimaManun - kmManutencao );
    }

    public boolean executaManutencao(int kmManutencao){
        ultimaManun = kmManutencao;
        return (this.ultimaManun == kmManutencao);
    }

    @Override
    public String toString() {
        return "Ultima Manutencao em: "+this.ultimaManun+" km's.";
    }

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