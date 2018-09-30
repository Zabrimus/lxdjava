package de.serversenke.lxd.script.exception;

public class ProvisioningException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProvisioningException(String message) {
        super(message);
    }

    public ProvisioningException(String message, Throwable ex) {
        super(message, ex);
    }
}
