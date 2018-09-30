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
public class ContainerState {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("status_code")
    @Expose
    private StatusCode statusCode;

    @SerializedName("disk")
    @Expose
    private Map<String, ContainerStateDisk> disk;

    @SerializedName("memory")
    @Expose
    private ContainerStateMemory memory;

    @SerializedName("network")
    @Expose
    private Map<String, ContainerStateNetwork> network;

    @SerializedName("pid")
    @Expose
    private long pid;

    @SerializedName("processes")
    @Expose
    private long processes;

    @SerializedName("cpu")
    @Expose
    private ContainerStateCPU cpu;
}
