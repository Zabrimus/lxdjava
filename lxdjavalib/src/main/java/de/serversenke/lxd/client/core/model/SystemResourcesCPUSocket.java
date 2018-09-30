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
public class SystemResourcesCPUSocket {

    @SerializedName("cores")
    @Expose
    private long cores;

    @SerializedName("frequency")
    @Expose
    private long frequency;

    @SerializedName("frequency_turbo")
    @Expose
    private long frequencyTurbo;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("vendor")
    @Expose
    private String vendor;

    @SerializedName("threads")
    @Expose
    private long threads;
}
