package de.serversenke.lxd.script.exception;

public class CertificateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CertificateException(String message, Throwable ex) {
        super(message, ex);
    }
}
