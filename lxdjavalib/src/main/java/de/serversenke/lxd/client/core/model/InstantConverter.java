package de.serversenke.lxd.client.core.model;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class InstantConverter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    private static final DateTimeFormatter FORMATTER_1 = DateTimeFormatter.ISO_INSTANT;
    private static final DateTimeFormatter FORMATTER_2 = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private static final Pattern datePattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})\\.(\\d*)((\\-|\\+)?-\\d{2}:\\d{2})");

    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER_1.format(src));
    }

    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return FORMATTER_1.parse(json.getAsString(), Instant::from);
        } catch (Exception e) {
            String jsonString;

            Matcher m = datePattern.matcher(json.getAsString());
            if (m.matches()) {
                jsonString = m.group(1) + m.group(3);
            } else {
                jsonString = json.getAsString();
            }

            return FORMATTER_2.parse(jsonString, Instant::from);
        }
    }
}
