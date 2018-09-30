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
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class Network extends NetworkPut {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("used_by")
    @Expose
    private String[] usedBy;

    @SerializedName("managed")
    @Expose
    private boolean managed;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("locations")
    @Expose
    private String[] locations;
}
