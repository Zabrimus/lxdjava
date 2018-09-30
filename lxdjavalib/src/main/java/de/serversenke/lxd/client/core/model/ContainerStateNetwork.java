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
public class ContainerStateNetwork {

    @SerializedName("addresses")
    @Expose
    private ContainerStateNetworkAddress[] addresses;

    @SerializedName("counters")
    @Expose
    private ContainerStateNetworkCounters counters;

    @SerializedName("hwaddr")
    @Expose
    private String hwaddr;

    @SerializedName("host_name")
    @Expose
    private String hostName;

    @SerializedName("mtu")
    @Expose
    private int mtu;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("type")
    @Expose
    private String type;
}
