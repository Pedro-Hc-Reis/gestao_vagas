package br.com.rocketseat.gestao_vagas.exceptions;

public class CompanyFoundException extends RuntimeException {
    public CompanyFoundException ( ) {
        super ( "Empresa já existe" );
    }
}