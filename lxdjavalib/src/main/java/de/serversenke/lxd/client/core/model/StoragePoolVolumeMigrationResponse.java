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
public class StoragePoolVolumeMigrationResponse {

    @SerializedName("control")
    @Expose
    private String control;

    @SerializedName("fs")
    @Expose
    private Map<String, String> fs;
}
