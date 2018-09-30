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
public class ContainerStateNetworkAddress {

    @SerializedName("family")
    @Expose
    private String family;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("netmask")
    @Expose
    private String netmask;

    @SerializedName("scope")
    @Expose
    private String scope;
}
