package com.fenixcommunity.centralspace.app.rest.dto.register;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = RegisterType.RegisterTypeSerializer.class)
@JsonDeserialize(using = RegisterType.RegisterTypeDeserializer.class)
@AllArgsConstructor @Getter
public enum RegisterType {
    ADMIN("ADM", 0), STANDARD("STD", 1);

    private final String accountSignature;
    private final int accountValue;

    public static class RegisterTypeDeserializer extends StdDeserializer<RegisterType> {
        public RegisterTypeDeserializer() {
            super(RegisterType.class);
        }

        @Override
        public RegisterType deserialize(JsonParser jsonParser, DeserializationContext dc) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String enumJson = node.asText();
            return RegisterType.valueOf(enumJson.toUpperCase());
            //todo co jak zle? obsluga bledu
        }
    }

    public static class RegisterTypeSerializer extends StdSerializer {
        public RegisterTypeSerializer() {
            super(RegisterType.class);
        }

        @Override
        public void serialize(Object registerType, JsonGenerator generator,
                              SerializerProvider provider)
                throws IOException {
            RegisterType registerTypeMapped = (RegisterType) registerType;
            generator.writeStartObject();
            generator.writeFieldName("registerType");
            generator.writeString(registerTypeMapped.name());
            generator.writeFieldName("signature");
            generator.writeString(registerTypeMapped.getAccountSignature());
            generator.writeFieldName("code");
            generator.writeNumber(registerTypeMapped.getAccountValue());
            generator.writeEndObject();
        }
    }

}
