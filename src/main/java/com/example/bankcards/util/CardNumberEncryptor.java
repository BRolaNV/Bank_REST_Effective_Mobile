package com.example.bankcards.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Component
@Converter
public class CardNumberEncryptor implements AttributeConverter <String, String>{

    @Value("${encryption.secret}")
    private String secret;


    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(String attribute) {

        if (attribute != null) {

            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        }
        return null;
    }


    @SneakyThrows
    @Override
    public String convertToEntityAttribute(String dbData) {

        if (dbData != null) {

            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData.getBytes())));

        }
        return null;
    }
}
