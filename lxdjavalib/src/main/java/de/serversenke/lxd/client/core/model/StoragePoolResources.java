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
public class StoragePoolResources {

    @SerializedName("inodes")
    @Expose
    private StoragePoolResourcesSpace inodes;

    @SerializedName("space")
    @Expose
    private StoragePoolResourcesSpace space;
}
