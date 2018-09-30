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
public class ImagePut {

    @SerializedName("auto_update")
    @Expose
    private boolean autoUpdate;

    @SerializedName("properties")
    @Expose
    private Map<String, String> properties;

    @SerializedName("public")
    @Expose
    private boolean _public;
}
