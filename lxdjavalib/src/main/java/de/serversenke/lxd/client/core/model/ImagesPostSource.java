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
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class ImagesPostSource extends ImageSource {

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("fingerprint")
    @Expose
    private String fingerprint;

    @SerializedName("secret")
    @Expose
    private String secret;
}
