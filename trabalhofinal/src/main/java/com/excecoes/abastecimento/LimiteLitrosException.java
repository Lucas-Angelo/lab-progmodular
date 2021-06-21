package com.excecoes.abastecimento;

public class LimiteLitrosException extends Exception {
    public LimiteLitrosException(String mensagemErro) {
        super(mensagemErro);
    }
}
