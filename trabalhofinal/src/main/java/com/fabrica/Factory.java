package com.fabrica;

public interface Factory<T> {
    
    public T getProduct(int choice, String placa);

}
