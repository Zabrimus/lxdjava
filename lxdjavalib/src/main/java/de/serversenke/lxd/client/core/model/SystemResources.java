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
public class SystemResources {

    @SerializedName("cpu")
    @Expose
    private SystemResourcesCPU cpu;

    @SerializedName("memory")
    @Expose
    private SystemResourcesMemory memory;

    @SerializedName("pool")
    @Expose
    private SystemResourcesStoragePool storagePool;
}
