package de.serversenke.lxd.client.core;

import com.google.gson.Gson;

import de.serversenke.lxd.client.core.model.Event;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class EventWebsocket extends ApiBase {

    public enum LogType {
        operation, logging, lifecycle
    }

    public interface Listener {
        /**
         * Invoked when a web socket has been accepted by the remote peer and may begin
         * transmitting messages.
         */
        public void onOpen(Response response);

        /** Invoked when a text (type {@code 0x1}) message has been received. */
        public void onMessage(Event event);

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

    private String url;
    private String logTypes;
    private Listener listener;
    private WebSocket ws;

    public EventWebsocket(String url, Listener listener, LogType... types) {
        this.url = url;
        this.listener = listener;

        logTypes = "";

        System.out.println("TYPES: " + types);

        if (types == null || types.length == 0) {
            logTypes = "operation,logging,lifecycle";
        } else {
            for (LogType lt : types) {
                logTypes = logTypes + "," + lt.toString();
            }
        }
    }

    public void connect() {
        Gson gson = createGson();
        OkHttpClient client = createClient();

        Request request = new Request.Builder().url(url + "/1.0/events?type=" + logTypes).build();

        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                listener.onOpen(response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Event event = gson.fromJson(text, Event.class);
                listener.onMessage(event);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Event event = gson.fromJson(bytes.utf8(), Event.class);
                listener.onMessage(event);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                listener.onClosing(code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                listener.onClosed(code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
                listener.onFailure(t, response);
            }
        });

        // wait for the socket to shutdown

        client.dispatcher().executorService().shutdown();
    }

    public void close() {
        ws.close(1000, null);
    }
}
