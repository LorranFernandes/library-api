package io.github.LorranFernandes.libraryapi.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException{
    public OperacaoNaoPermitidaException(String msg){
        super(msg);
    }
}
