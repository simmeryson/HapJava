package com.guok.hap.impl.pairing;

import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.responses.OkResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class TypeLengthValueUtils {

    private final static Logger logger = LoggerFactory.getLogger(TypeLengthValueUtils.class);

    private TypeLengthValueUtils() {
    }

    /**
     * decode request body. get request type and content
     */
    public static DecodeResult decode(byte[] content) throws IOException {
        DecodeResult ret = new DecodeResult();
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        while (bais.available() > 0) {
            byte type = (byte) (bais.read() & 0xFF);
            int length = bais.read();
            byte[] part = new byte[length];
            bais.read(part);
            ret.add(type, part);
        }
        return ret;
    }

    public static Encoder getEncoder() {
        return new Encoder();
    }

    public static HttpResponse createErrorResponse(String msg, short state, TLVError error) {
        Encoder encoder = getEncoder();
        encoder.add(MessageType.ERROR, error.getKey());
        encoder.add(MessageType.STATE, state);
        logger.warn(msg);
        return new OkResponse(encoder.toByteArray());
    }

    public static final class Encoder {

        private final ByteArrayOutputStream baos;

        private Encoder() {
            baos = new ByteArrayOutputStream();
        }

        public void add(MessageType type, BigInteger i) throws IOException {
            add(type, ByteUtils.toByteArray(i));
        }

        public void add(MessageType type, short b) {
            baos.write(type.getKey());
            baos.write(1);
            baos.write(b);
        }

        public void add(MessageType type, byte[] bytes) throws IOException {
            InputStream bais = new ByteArrayInputStream(bytes);
            while (bais.available() > 0) {
                int toWrite = bais.available();
                toWrite = toWrite > 255 ? 255 : toWrite;
                baos.write(type.getKey());
                baos.write(toWrite);
                ByteUtils.copyStream(bais, baos, toWrite);
            }
        }

        public byte[] toByteArray() {
            return baos.toByteArray();
        }

    }

    public static final class DecodeResult {
        private final Map<Short, byte[]> result = new HashMap<>();

        private DecodeResult() {
        }

        public byte getByte(MessageType type) {
            return result.get(type.getKey())[0];
        }

        public BigInteger getBigInt(MessageType type) {
            return new BigInteger(1, result.get(type.getKey()));
        }

        public byte[] getBytes(MessageType type) {
            return result.get(type.getKey());
        }

        public void getBytes(MessageType type, byte[] dest, int srcOffset) {
            byte[] b = result.get(type.getKey());
            System.arraycopy(b, srcOffset, dest, 0, Math.min(dest.length, b.length));
        }

        public int getLength(MessageType type) {
            return result.get(type.getKey()).length;
        }

        private void add(short type, byte[] bytes) {
//			result.merge(type, bytes, ByteUtils::joinBytes);
            byte[] old = result.get(type);
            byte[] newValue = (old == null) ? bytes : ByteUtils.joinBytes(old, bytes);
            if (newValue == null)
                result.remove(type);
            else
                result.put(type, newValue);
        }

    }

}
