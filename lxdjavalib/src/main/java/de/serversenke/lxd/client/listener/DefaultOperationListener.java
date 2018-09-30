package de.serversenke.lxd.client.listener;

import de.serversenke.lxd.client.core.OperationWebsocket;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okio.ByteString;

@Slf4j
public class DefaultOperationListener implements OperationWebsocket.Listener {

    @Override
    public void onOpen(Response response) {
        log.trace("onOpen:" + response);
    }

    @Override
    public void onMessage(String text) {
        log.trace("onMessage:" + text);
    }

    @Override
    public void onMessage(ByteString bytes) {
        log.trace("onMessage:" + bytes);
    }

    @Override
    public void onClosing(int code, String reason) {
        log.trace("onClosing:" + code + ", " + reason);
    }

    @Override
    public void onClosed(int code, String reason) {
        log.trace("onClosed:" + code + ", " + reason);
    }

    @Override
    public void onFailure(Throwable t, Response response) {
        log.trace("onFailure:" + response, t);
    }
}
