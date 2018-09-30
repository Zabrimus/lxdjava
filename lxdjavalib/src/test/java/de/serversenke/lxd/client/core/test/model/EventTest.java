package de.serversenke.lxd.client.core.test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import de.serversenke.lxd.client.core.model.Event;

public class EventTest extends TestBase {

    @Test
    void testEvent1() {
        Event responseObject = createObject("response_event_1.json", Event.class);
        Map<String, Object> m = responseObject.getMetadata();

        assertEquals(m.size(), 0);
    }

    @Test
    void testEvent2() {
        Event responseObject = createObject("response_event_2.json", Event.class);

        Map<String, Object> m = responseObject.getMetadata();

        assertTrue(m.size() > 0);
        assertTrue(m.get("context") instanceof Map);
        assertTrue(m.get("level") instanceof String);
        assertTrue(m.get("message") instanceof String);
    }

}
