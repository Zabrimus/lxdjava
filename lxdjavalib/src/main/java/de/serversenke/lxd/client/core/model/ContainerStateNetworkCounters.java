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
public class ContainerStateNetworkCounters {

    @SerializedName("bytes_received")
    @Expose
    private long bytesReceived;

    @SerializedName("bytes_sent")
    @Expose
    private long bytesSent;

    @SerializedName("packets_received")
    @Expose
    private long packetsReceived;

    @SerializedName("packets_sent")
    @Expose
    private long packetsSent;
}
