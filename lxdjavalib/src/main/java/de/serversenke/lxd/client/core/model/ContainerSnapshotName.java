package de.serversenke.lxd.client.core.model;

import java.time.Instant;
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
public class ContainerSnapshotName {

    @SerializedName("architecture")
    @Expose
    private String architecture;

    @SerializedName("config")
    @Expose
    private Map<String, String> config;

    @SerializedName("created_at")
    @Expose
    private Instant createdAt;

    @SerializedName("devices")
    @Expose
    private Map<String, Map<String, String>> devices;

    @SerializedName("ephemeral")
    @Expose
    private boolean ephemeral;

    @SerializedName("expanded_config")
    @Expose
    private Map<String, String> expandedConfig;

    @SerializedName("expanded_devices")
    @Expose
    private Map<String, Map<String, String>> expandedDevices;

    @SerializedName("last_used_at")
    @Expose
    private Instant lastUsedAt;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("profiles")
    @Expose
    private String[] profiles;

    @SerializedName("stateful")
    @Expose
    private boolean stateful;
}
