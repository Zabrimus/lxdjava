package de.serversenke.lxd.client;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Base {

    final protected static SecureRandom random = new SecureRandom();

    /**
     * Utility method to construct a map
     *
     * @param data the data
     * @return the map
     */
    public Map<String, String> buildMap(String... data) {
        Map<String, String> result = new HashMap<String, String>();

        String key = null;
        int step = 0;
        for (String value : data) {
            if (step % 2 == 0) {
                key = value;
            } else {
                result.put(key, value);
            }

            step++;
        }

        return result;
    }

    /**
     * simple method, which check if an String is not empty
     */
    protected boolean isNotEmpty(String arg) {
        if (arg == null) {
            return false;
        }

        return arg.trim().length() > 0;
    }

    /**
     * Utility method to sleep
     *
     * @param millis
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // ignore this exception and do nothing
        }
    }

    /**
     * creates an alphanumeric random string of length len
     *
     * @param len the length of the desired string
     *
     * @return
     */
    protected String createRandomString(int len) {

        char[] ch = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

        char[] c = new char[len];
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }

        return new String(c);
    }
}
