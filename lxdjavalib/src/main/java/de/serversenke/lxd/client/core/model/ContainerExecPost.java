package de.serversenke.lxd.client.core.model;

import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ContainerExecPost {

    @SerializedName("command")
    @Expose
    private String[] command;

    @SerializedName("wait-for-websocket")
    @Expose
    private boolean waitForWebsocket;

    @SerializedName("interactive")
    @Expose
    private boolean interactive;

    @SerializedName("environment")
    @Expose
    private Map<String, String> environment;

    @SerializedName("width")
    @Expose
    private int width;

    @SerializedName("height")
    @Expose
    private int height;

    @SerializedName("record-output")
    @Expose
    private boolean recordOutput;

    public void setSingleCommand(String command) {
        this.command = new String[] { command };
    }
}
