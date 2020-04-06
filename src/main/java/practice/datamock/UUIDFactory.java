package practice.datamock;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * UUID Factory
 */
@Component
public class UUIDFactory {

    static final Logger logger = LoggerFactory.getLogger(UUIDFactory.class);

    /**
     * @return
     */
    public String getNewId() {
        String v = uuidToBase64(UUID.randomUUID().toString());
        if (!v.startsWith("_") && !v.endsWith("_") && !v.startsWith("-") && !v.endsWith("-")) {
            return v;
        }
        return getNewId();
    }

    /**
     * @param str
     * @return
     */
    private static String uuidToBase64(String str) {
        UUID uuid = UUID.fromString(str);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base64.encodeBase64URLSafeString(bb.array());
    }
}
