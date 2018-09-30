package de.serversenke.lxd.client.core.model;

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
public class ContainerStatePut {

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("timeout")
    @Expose
    private int timeout;

    @SerializedName("force")
    @Expose
    private boolean force;

    @SerializedName("stateful")
    @Expose
    private boolean stateful;
}
