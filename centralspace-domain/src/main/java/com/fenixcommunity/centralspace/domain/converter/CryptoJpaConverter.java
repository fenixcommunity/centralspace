package com.fenixcommunity.centralspace.domain.converter;

import static com.fenixcommunity.centralspace.utilities.logger.MarkersVar.GENERAL_USER;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.Transient;

import com.fenixcommunity.centralspace.domain.exception.converter.CryptoJpaConverterException;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.codec.Base64;
import org.yaml.snakeyaml.Yaml;

@Converter
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CryptoJpaConverter implements AttributeConverter<String, String> {
    private static final String ALGORITHM;
    private static final byte[] KEY;
    private static final String SECURITY_FILE = "security.yml";
    private static final String ENCRYPTION_PROPERTY_KEY = "encryption";
    private static final String ALGORITHM_PROPERTY_KEY = "algorithm";
    private static final String SECRET_PROPERTY_KEY = "key";

    static {
        final Yaml yaml = new Yaml();
        final InputStream inputStream = CryptoJpaConverter.class.getClassLoader().getResourceAsStream(SECURITY_FILE);
//      final Encryption encryption = yaml.load(inputStream); if only matched data -> security_encryption.yml
        final Map<String, Object> properties = yaml.load(inputStream);
        if (properties.isEmpty()) {
            throw new CryptoJpaConverterException("Invalid properties");
        }
        final Map<String, String> encryptionYaml = (Map) properties.get(ENCRYPTION_PROPERTY_KEY);
        final Encryption encryption = new Encryption(encryptionYaml.get(ALGORITHM_PROPERTY_KEY), encryptionYaml.get(SECRET_PROPERTY_KEY));

        if (isBlank(encryption.getKey()) || isBlank(encryption.getAlgorithm())) {
            log.error(GENERAL_USER, "Unsuccessful loading the properties to converter");
            throw new CryptoJpaConverterException("Invalid parameters of properties");
        }
        ALGORITHM = encryption.getAlgorithm();
        KEY = encryption.getKey().getBytes();
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
