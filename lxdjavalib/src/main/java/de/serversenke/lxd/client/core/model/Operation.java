package de.serversenke.lxd.client.core.model;

import java.time.Instant;
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
public class Operation {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("class")
    @Expose
    private String clazz;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("created_at")
    @Expose
    private Instant createdAt;

    @SerializedName("updated_at")
    @Expose
    private Instant updatedAt;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("status_code")
    @Expose
    private StatusCode statusCode;

    @SerializedName("resources")
    @Expose
    private Map<String, List<String>> resources;

    @SerializedName("metadata")
    @Expose
    private Map<String, String> metadata;

    @SerializedName("may_cancel")
    @Expose
    private boolean mayCancel;

    @SerializedName("err")
    @Expose
    private String errorMessage;
}
