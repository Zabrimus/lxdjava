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
public class ContainerMetadata {

    @SerializedName("architecture")
    @Expose
    private String architecture;

    @SerializedName("creation_date")
    @Expose
    private String creationDate;

    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;

    @SerializedName("properties")
    @Expose
    private Map<String, String> properties;

    @SerializedName("templates")
    @Expose
    private Map<String, ContainerMetadataTemplate> templates;
}
