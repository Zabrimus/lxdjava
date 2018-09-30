package de.serversenke.lxd.client.core;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class OperationWebsocket extends ApiBase {

    public interface Listener {
        /**
         * Invoked when a web socket has been accepted by the remote peer and may begin
         * transmitting messages.
         */
        public void onOpen(Response response);

        /**
         * Invoked when a text (type {@code 0x1}) message has been received.
         */
        public void onMessage(String text);

        /**
         * Invoked when a binary (type {@code 0x2}) message has been received.
         */
        public void onMessage(ByteString bytes);

        /**
         * Invoked when the remote peer has indicated that no more incoming messages
         * will be transmitted.
         */
        public void onClosing(int code, String reason);

        /**
         * Invoked when both peers have indicated that no more messages will be
         * transmitted and the connection has been successfully released. No further
         * calls to this listener will be made.
         */
        public void onClosed(int code, String reason);

        /**
         * Invoked when a web socket has been closed due to an error reading from or
         * writing to the network. Both outgoing and incoming messages may have been
         * lost. No further calls to this listener will be made.
         */
        public void onFailure(Throwable t, Response response);
    }

    private String uuid;
    private String secret;
    private String url;
    private Listener listener;
    private WebSocket ws;

    static OkHttpClient client = null;

    public OperationWebsocket(String url, String uuid, String secret, Listener listener) {
        this.url = url;
        this.uuid = uuid;
        this.secret = secret;
        this.listener = listener;

        if (client == null) {
            client = createClient();
        }
    }

    public void connect() {

        Request request = new Request.Builder().url(url + "/1.0/operations/" + uuid + "/websocket?secret=" + secret).build();

        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                if (listener != null) {
                    listener.onOpen(response);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                if (listener != null) {
                    listener.onMessage(text);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                if (listener != null) {
                    listener.onMessage(bytes);
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                if (listener != null) {
                    listener.onClosing(code, reason);
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                if (listener != null) {
                    listener.onClosed(code, reason);
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                if (listener != null) {
                    listener.onFailure(t, response);
                }
            }
        });
    }

    public void send(String message) {
        if (!ws.send(message)) {
            // the socket seems to be closed, close finally
            close();
        }
    }

    public void close() {
//        client.dispatcher().executorService().shutdown();

        try {
            ws.close(1000, null);
        } catch (Exception e) {
            // already closed? ignore this....
        }

        // client.dispatcher().executorService().shutdown();


//        try {
//            ws.cancel();
//            ws.close(1000, "Good bye !");
//
//            // client.dispatcher().executorService().shutdown();
//            // client.connectionPool().evictAll();
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }
    }
}
