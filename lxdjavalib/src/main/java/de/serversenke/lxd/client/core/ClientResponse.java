package de.serversenke.lxd.client.core;

import de.serversenke.lxd.client.core.model.StatusCode;
import okhttp3.Headers;

public class ClientResponse<T> {
    public enum ResponseType {
        SYNC("sync"), ASYNC("async"), ERROR("error"), UNKNOWN("unknown");

        private String type;

        ResponseType(String type) {
            this.type = type;
        }

        @Override
        public String toString(){
            return type;
        }

        public static ResponseType getEnum(String type) {
            if (type == null) {
                return ResponseType.UNKNOWN;
            }

            for(ResponseType v : values()) {
                if (v.type.equals(type)) {
                    return v;
                }
            }

            throw new IllegalArgumentException();
        }
    }

    private ResponseType type;

    // sync values
    private StatusCode statusCode;
    private String status;

    // async values
    private String operation;

    // error values
    private int errorCode;
    private String errorMessage;

    // HTTP header
    private Headers headers;

    private T response;

    public ClientResponse(Headers headers, int code, T response, String type, String status, String operation, int errorCode, String errorMessage) {
        this.response = response;
        statusCode = StatusCode.fromCode(code);
        this.status = status;
        this.operation = operation;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.type = ResponseType.getEnum(type);
        this.headers = headers;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public ResponseType getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getOperation() {
        return operation;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getResponse() {
        return response;
    }

    public Headers getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "ClientResponse [type=" + type + ", statusCode=" + statusCode + ", status=" + status + ", operation="
                + operation + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", headers=" + headers
                + ", response=" + response + "]";
    }
}
