package com.fenixcommunity.centralspace.Password;

import lombok.Builder;
import lombok.Value;

//immutable objects, like @Data
@Value
// Builder gdy mamy pola final i chcemy je tworzyc w elastycznym konstruktorze
// Builder stosujemy gdy tworzymy obiekt raz i nie zmianiamy go potem przez sety
@Builder
public class Password {
}
