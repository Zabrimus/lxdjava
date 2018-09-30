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
public class StoragePoolVolumeSource {

    @SerializedName("name")
    @Expose
    private String config;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("pool")
    @Expose
    private String pool;

    @SerializedName("certificate")
    @Expose
    private String certificate;

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("operation")
    @Expose
    private String operation;

    @SerializedName("secrets")
    @Expose
    private Map<String, String> secrets;
}
