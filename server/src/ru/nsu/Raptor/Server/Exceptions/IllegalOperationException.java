package ru.nsu.Raptor.Server.Exceptions;

public class IllegalOperationException extends ServerException {
    public IllegalOperationException(String error, int errorCode) {
        super( error, errorCode );
    }
}
