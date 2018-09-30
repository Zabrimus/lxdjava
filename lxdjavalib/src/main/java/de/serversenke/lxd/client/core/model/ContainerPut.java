package de.serversenke.lxd.client.core.model;

import java.util.Map;

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
public class ContainerPut {

    @SerializedName("architecture")
    @Expose
    private String architecture;

    @SerializedName("config")
    @Expose
    private Map<String, String> config;

    @SerializedName("devices")
    @Expose
    private Map<String, Map<String, String>> devices;

    @SerializedName("ephemeral")
    @Expose
    private boolean ephemeral;

    @SerializedName("profiles")
    @Expose
    private String[] profiles;

    @SerializedName("restore")
    @Expose
    private String restore;

    @SerializedName("stateful")
    @Expose
    private boolean stateful;

    @SerializedName("description")
    @Expose
    private String description;
}
