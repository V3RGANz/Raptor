package ru.nsu.Raptor.Server.Exceptions;

class ServerException extends Exception {
    ServerException(String error, int errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return error;
    }

    private final String error;
    private final int errorCode;
}
