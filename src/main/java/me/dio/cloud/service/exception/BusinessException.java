package me.dio.cloud.service.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {

    /**
     * Identificador de versão da classe necessário para o processo de serialização.
     * Garante que, durante a desserialização, o objeto seja compatível com a versão da classe.
     * Declarar explicitamente evita conflitos se a estrutura da classe mudar no futuro.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}
