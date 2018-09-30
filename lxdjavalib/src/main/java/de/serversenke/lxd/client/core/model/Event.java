package de.serversenke.lxd.client.core.model;

import java.time.Instant;
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
@ToString(callSuper=false)
public class Event {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("timestamp")
    @Expose
    private Instant time;

    @SerializedName("metadata")
    @Expose
    private Map<String, Object> metadata;
}
