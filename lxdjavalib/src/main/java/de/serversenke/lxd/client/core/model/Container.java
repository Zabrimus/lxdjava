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
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class Container extends ContainerPut {

    @SerializedName("created_at")
    @Expose
    private Instant createdAt;

    @SerializedName("expanded_config")
    @Expose
    private Map<String, String> expandedConfig;

    @SerializedName("expanded_devices")
    @Expose
    private Map<String, Map<String, String>> expandedDevices;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("status_code")
    @Expose
    private StatusCode statusCode;

    @SerializedName("last_used_at")
    @Expose
    private Instant lastUsedAt;

    @SerializedName("location")
    @Expose
    private String location;
}
