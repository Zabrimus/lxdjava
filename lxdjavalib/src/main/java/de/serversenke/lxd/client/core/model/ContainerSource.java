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
public class ContainerSource {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("certificate")
    @Expose
    private String certificate;

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("fingerprint")
    @Expose
    private String fingerprint;

    @SerializedName("properties")
    @Expose
    private Map<String, String> properties;

    @SerializedName("server")
    @Expose
    private String server;

    @SerializedName("secret")
    @Expose
    private String secret;

    @SerializedName("protocol")
    @Expose
    private String protocol;

    @SerializedName("base-image")
    @Expose
    private String baseImage;

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("operation")
    @Expose
    private String operation;

    @SerializedName("secrets")
    @Expose
    private Map<String, String> websockets;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("live")
    @Expose
    private boolean live;

    @SerializedName("container_only")
    @Expose
    private boolean containerOnly;
}
