package com.fenixcommunity.centralspace.app.configuration.features.flags;

import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.configuration.annotation.AppFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor @Getter @FieldDefaults(level = PRIVATE)
@AppFeature
public class BatchFeature {
    private boolean accountLoginUpdater;
}
