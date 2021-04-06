package com.fenixcommunity.centralspace.domain.core.graphql.scalar;

import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.function.Function;

import com.fenixcommunity.centralspace.utilities.time.TimeFormatter;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.stereotype.Component;

@Component
public class ZonedDateTimeScalar extends GraphQLScalarType {

    public ZonedDateTimeScalar() {
        super("ZonedDateTime", "ZonedDateTime Scalar. Example: 2021-04-02T11:57:28.694Z", new Coercing<ZonedDateTime, String>() {
            public String serialize(final Object input) throws CoercingSerializeException {
                final ZonedDateTime zonedDateTime;
                if (input instanceof ZonedDateTime) {
                    zonedDateTime = (ZonedDateTime) input;
                } else {
                    if (!(input instanceof String)) {
                        throw new CoercingSerializeException("Expected something we can convert to 'java.time.ZonedDateTime' but was unknown type");
                    }
                    zonedDateTime = this.parseZonedDateTime(input.toString(), CoercingSerializeException::new);
                }

                try {
                    return TimeFormatter.ZONE_FORMATTER_2.format(zonedDateTime);
                } catch (DateTimeException e) {
                    throw new CoercingSerializeException("Unable to turn TemporalAccessor into ZonedDateTime because of : '" + e.getMessage());
                }
            }

            public ZonedDateTime parseValue(final Object input) throws CoercingParseValueException {
                final ZonedDateTime zonedDateTime;
                if (input instanceof ZonedDateTime) {
                    zonedDateTime = (ZonedDateTime) input;
                } else {
                    if (!(input instanceof String)) {
                        throw new CoercingParseValueException("Expected a 'String' but was unknown type");
                    }
                    zonedDateTime = this.parseZonedDateTime(input.toString(), CoercingParseValueException::new);
                }

                return zonedDateTime;
            }

            public ZonedDateTime parseLiteral(final Object input) throws CoercingParseLiteralException {
                if (!(input instanceof StringValue)) {
                    throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was unknown type");
                } else {
                    return this.parseZonedDateTime(((StringValue) input).getValue(), CoercingParseLiteralException::new);
                }
            }

            private ZonedDateTime parseZonedDateTime(final String s, final Function<String, RuntimeException> exceptionMaker) {
                try {
                    return TimeFormatter.toZonedDateTime(s, TimeFormatter.ZONE_FORMATTER_2);
                } catch (DateTimeParseException e) {
                    throw exceptionMaker.apply("Invalid RFC3339 value : '" + s + "'. because of : '" + e.getMessage() + "'");
                }
            }
        });
    }
}