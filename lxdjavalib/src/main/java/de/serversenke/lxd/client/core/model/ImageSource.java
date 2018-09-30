package de.serversenke.lxd.client.core.model;

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
public class ImageSource {

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("certificate")
    @Expose
    private String certificate;

    @SerializedName("protocol")
    @Expose
    private String protocol;

    @SerializedName("server")
    @Expose
    private String server;
}
