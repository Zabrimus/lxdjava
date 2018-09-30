package de.serversenke.lxd.client.core.model;

import java.util.List;
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
public class ServerConfiguration {

    @SerializedName("config")
    @Expose
    private Map<String, String> config;

    @SerializedName("api_extensions")
    @Expose
    private List<String> apiExtensions;

    @SerializedName("api_status")
    @Expose
    private String apiStatus;

    @SerializedName("api_version")
    @Expose
    private String apiVersion;

    @SerializedName("auth")
    @Expose
    private String auth;

    @SerializedName("public")
    @Expose
    private boolean _public;

    @SerializedName("auth_methods")
    @Expose
    private List<String> authMethods;

    @SerializedName("environment")
    @Expose
    private ServerEnvironment environment;
}
