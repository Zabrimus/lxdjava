package de.serversenke.lxd.client.core.model;

import java.time.Instant;

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
public class ImageGet extends ImagePut {

    @SerializedName("aliases")
    @Expose
    private ImageAlias[] aliases;

    @SerializedName("architecture")
    @Expose
    private String architecture;

    @SerializedName("cached")
    @Expose
    private boolean cached;

    @SerializedName("filename")
    @Expose
    private String filename;

    @SerializedName("fingerprint")
    @Expose
    private String fingerprint;

    @SerializedName("size")
    @Expose
    private long size;

    @SerializedName("update_source")
    @Expose
    private ImageSource updateSource;

    @SerializedName("created_at")
    @Expose
    private Instant createdAt;

    @SerializedName("expires_at")
    @Expose
    private Instant expiresAt;

    @SerializedName("last_used_at")
    @Expose
    private Instant lastUsedAt;

    @SerializedName("uploaded_at")
    @Expose
    private Instant uploadedAt;
}
