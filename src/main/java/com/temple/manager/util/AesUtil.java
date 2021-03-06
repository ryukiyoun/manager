package com.temple.manager.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Component
public class AesUtil {
    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;
    private static final String INSTANCE_TYPE = "AES/CBC/PKCS5Padding";
    private final SecretKeySpec secretKeySpec;
    private final Cipher cipher;
    private final IvParameterSpec ivParameterSpec;

    public AesUtil(Environment environment) throws Exception {
        byte[] byteKey = Objects.requireNonNull(environment.getProperty("aes.encryptor.password")).getBytes();

        secretKeySpec = new SecretKeySpec(byteKey, "AES");
        ivParameterSpec = new IvParameterSpec(byteKey);

        cipher = Cipher.getInstance(INSTANCE_TYPE);
    }

    public String encrypt(String str) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(str.getBytes(ENCODING_TYPE));
        return new String(Base64.getEncoder().encode(encrypted), ENCODING_TYPE);
    }

    public String decrypt(String str) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decoded = Base64.getDecoder().decode(str.getBytes(ENCODING_TYPE));
        return new String(cipher.doFinal(decoded), ENCODING_TYPE);
    }
}
