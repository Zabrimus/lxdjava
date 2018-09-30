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
public class ContainerStateMemory {

    @SerializedName("usage")
    @Expose
    private long usage;

    @SerializedName("usage_peak")
    @Expose
    private long usagePeak;

    @SerializedName("swap_usage")
    @Expose
    private long swapUsage;

    @SerializedName("swap_usage_peak")
    @Expose
    private long swapUsagePeak;
}
