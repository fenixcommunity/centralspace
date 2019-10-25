package com.fenixcommunity.centralspace.domain.utils.converter;

import com.fenixcommunity.centralspace.domain.exception.cenverter.CryptoJpaConverterException;
import lombok.extern.java.Log;
import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.Transient;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Properties;
import java.util.logging.Level;


public class CryptoJpaConverter {


}
