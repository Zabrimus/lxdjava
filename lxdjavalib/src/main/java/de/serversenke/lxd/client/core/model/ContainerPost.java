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
public class ContainerPost {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("migration")
    @Expose
    private boolean migration;

    @SerializedName("live")
    @Expose
    private boolean live;

    @SerializedName("container_only")
    @Expose
    private boolean containerOnly;

    @SerializedName("target")
    @Expose
    private ContainerPostTarget target;
}
