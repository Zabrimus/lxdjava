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
public class StoragePoolVolumePost {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("pool")
    @Expose
    private String pool;

    @SerializedName("migration")
    @Expose
    private boolean migration;

    @SerializedName("target")
    @Expose
    private StoragePoolVolumeTarget target;
}
