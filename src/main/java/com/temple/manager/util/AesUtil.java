package com.temple.manager.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AesUtil {
    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;
    private static final String INSTANCE_TYPE = "AES/CBC/PKCS5Padding";
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;
    private IvParameterSpec ivParameterSpec;

    public AesUtil(@Value("${custom.path.file}") String keyPath){
        try{
            File keyFile = new File(keyPath);
            BufferedReader br = new BufferedReader(new FileReader(keyFile));
            String key;
            key = br.readLine();
            byte[] keyByte = key.getBytes(ENCODING_TYPE);
            secretKeySpec = new SecretKeySpec(keyByte, "AES");
            cipher = Cipher.getInstance(INSTANCE_TYPE);
            ivParameterSpec = new IvParameterSpec(keyByte);

            br.close();
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | IOException e){
            e.printStackTrace();
        }
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
