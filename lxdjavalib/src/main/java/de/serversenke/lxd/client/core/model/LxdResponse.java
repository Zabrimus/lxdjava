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
public class LxdResponse<T> {

    @SerializedName("type")
    @Expose
    private String responseType;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("status_code")
    @Expose
    private int statusCode;

    @SerializedName("operation")
    @Expose
    private String operation;

    @SerializedName("error_code")
    @Expose
    private int errorCode;

    @SerializedName("error")
    @Expose
    private String errorMessage;

    @SerializedName("metadata")
    @Expose
    private T metadata;
}
