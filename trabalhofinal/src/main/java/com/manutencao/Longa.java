package com.manutencao;

import java.io.Serializable;
import java.util.Objects;

public class Longa implements IManutencao, Serializable,Comparable<Longa> {
    protected int ultimaManun;
    private static final long serialVersionUID = 102L;
    private static final int kmManuteLonga = 20000;

    public Longa(){
        init(0);
    }
    public Longa(int kmRodadoAtual){
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
