package ru.nsu.Raptor.Server.Exceptions;

public class AuthException extends ServerException {
    public AuthException(String error, int errorCode) {
        super( error, errorCode );
    }
}
