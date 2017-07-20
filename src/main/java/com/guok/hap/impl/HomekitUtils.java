package com.guok.hap.impl;

import com.nimbusds.srp6.SRP6Routines;

import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.SecureRandom;

public class HomekitUtils {

    private static volatile SecureRandom secureRandom;

    public static BigInteger generateSalt() {
        return new BigInteger(SRP6Routines.generateRandomSalt(16));
    }

    public static byte[] generateKey() {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName("ed25519-sha-512");
        byte[] seed = new byte[spec.getCurve().getField().getb() / 8];
        getSecureRandom().nextBytes(seed);
        return seed;
    }

    public static String generateMac() {
        int byte1 = ((getSecureRandom().nextInt(255) + 1) | 2) & 0xFE; //Unicast locally administered MAC;
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(Integer.toHexString(byte1));
        stringBuffer.append(":");
        for (int i = 0; i < 5; i++) {
            String s = Integer.toHexString(getSecureRandom().nextInt(255) + 1);
            stringBuffer.append(s);
            if (i != 4)
                stringBuffer.append(":");
        }
        return stringBuffer.toString();
    }

    private static SecureRandom getSecureRandom() {
        if (secureRandom == null) {
            synchronized (HomekitUtils.class) {
                if (secureRandom == null) {
                    secureRandom = new SecureRandom();
                }
            }
        }
        return secureRandom;
    }

    public static <T> boolean isIntanceOf(Object obj, T t) {
        boolean result;
        if (obj == null) {
            result = false;
        } else {
            try {
                T temp = (T) obj; // checkcast
                result = true;
            } catch (ClassCastException e) {
                result = false;
            }
        }
        return result;
    }

    public static Class getGenericType(Object o, int index) {
        Type genType = o.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static Class getSingleGenericType(Object o) {
        return getGenericType(o, 0);
    }

    public static String getTypeFromUUID(String UUID) {
        if (UUID == null || UUID.length() != 36 || !UUID.contains("-"))
            return null;
        return Integer.toHexString(Integer.parseInt(UUID.split("-")[0], 16)).toLowerCase();
    }
}
