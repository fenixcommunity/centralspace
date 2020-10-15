package com.fenixcommunity.centralspace.domain.model.permanent.password;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fenixcommunity.centralspace.domain.converter.CryptoJpaConverter;
import com.fenixcommunity.centralspace.domain.model.permanent.AbstractBaseEntity;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

//immutable objects, like @Value
// Builder gdy mamy pola final i chcemy je tworzyc w elastycznym konstruktorze
// Builder stosujemy gdy tworzymy obiekt raz i nie zmianiamy go potem przez sety
//@Setter(value = AccessLevel.PACKAGE)
@Entity @Table(name = "password") // , schema = "security" -> we should add schema manually
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @FieldDefaults(level = PRIVATE)
public class Password extends AbstractBaseEntity {

    //TODO         return Optional.ofNullable(isoCountryCode)
    //             Optional.of(nowa wartosc gdy damy geta optional);


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    private Account account;

    @Convert(converter = CryptoJpaConverter.class)
    @Column(name = "password", nullable = false)
    private String password;
    //todo replace to byte[] or char[]

    @Column(name = "password_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PasswordType passwordType;

}