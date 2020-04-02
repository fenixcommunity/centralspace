package com.fenixcommunity.centralspace.domain.converter;

import static com.fenixcommunity.centralspace.utilities.logger.MarkersVar.GENERAL_USER;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Objects;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.Transient;

import com.fenixcommunity.centralspace.domain.exception.converter.CryptoJpaConverterException;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.codec.Base64;

@Converter
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CryptoJpaConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM;
    private static final byte[] KEY;
    private static final String algorithm_property_key = "encryption.algorithm";
    private static final String secret_property_key = "encryption.key";
    private static final String security_file = "security.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(CryptoJpaConverter.class.getClassLoader().getResourceAsStream(security_file)));
        } catch (IOException e) {
            log.error(GENERAL_USER, "Unsuccessful loading the properties to converter", e);
        }
        ALGORITHM = (String) properties.get(algorithm_property_key);
        KEY = ((String) properties.get(secret_property_key)).getBytes();
    }

    @Override
    @Transient
    public String convertToDatabaseColumn(final String ccData) {
        if (ccData == null) {
            throw new CryptoJpaConverterException("Data to convert required");
        }
        final Key key = new SecretKeySpec(KEY, ALGORITHM);
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            final String encrypted = new String(Base64.encode(c
                    .doFinal(ccData.getBytes())), StandardCharsets.UTF_8);
            return encrypted;
        } catch (Exception e) {
            throw new CryptoJpaConverterException("Encrypt process has been failed", e);
        }
    }

    @Override
    @Transient
    public String convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            throw new CryptoJpaConverterException("Data to convert required");
        }
        final Key key = new SecretKeySpec(KEY, ALGORITHM);
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            final String decrypted = new String(c.doFinal(Base64
                    .decode(dbData.getBytes(StandardCharsets.UTF_8))));
            return decrypted;
        } catch (Exception e) {
            throw new CryptoJpaConverterException("Decrypt process has been failed", e);
        }
    }
}
