package de.serversenke.lxd.client.core.model;

import java.util.List;

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
public class ServerEnvironment {

    @SerializedName("addresses")
    @Expose
    private List<String> addresses;

    @SerializedName("architectures")
    @Expose
    private List<String> architectures;

    @SerializedName("certificate")
    @Expose
    private String certificate;

    @SerializedName("certificate_fingerprint")
    @Expose
    private String certificateFingerprint;

    @SerializedName("driver")
    @Expose
    private String driver;

    @SerializedName("driver_version")
    @Expose
    private String driverVersion;

    @SerializedName("kernel")
    @Expose
    private String kernel;

    @SerializedName("kernel_architecture")
    @Expose
    private String kernelArchitecture;

    @SerializedName("kernel_version")
    @Expose
    private String kernelVersion;

    @SerializedName("server")
    @Expose
    private String server;

    @SerializedName("server_pid")
    @Expose
    private int serverPid;

    @SerializedName("server_version")
    @Expose
    private String serverVersion;

    @SerializedName("storage")
    @Expose
    private String storage;

    @SerializedName("storage_version")
    @Expose
    private String storageVersion;

    @SerializedName("server_clustered")
    @Expose
    private boolean serverClustered;

    @SerializedName("server_name")
    @Expose
    private String serverName;
}
