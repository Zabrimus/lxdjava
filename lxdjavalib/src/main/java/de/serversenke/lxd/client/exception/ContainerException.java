package de.serversenke.lxd.client.exception;

public class ContainerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ContainerException(String message) {
        super(message);
    }

    public ContainerException(String message, Throwable exception) {
        super(message, exception);
    }
}
