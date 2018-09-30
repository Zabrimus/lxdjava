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
public class ContainerExecControlWS {

    @SerializedName("command")
    @Expose
    private String command;

    @SerializedName("args")
    @Expose
    private Map<String, String> args;

    @SerializedName("signal")
    @Expose
    private int signal;
}
