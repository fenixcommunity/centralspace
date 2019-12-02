package com.fenixcommunity.centralspace.domain.model.password;

import com.fenixcommunity.centralspace.domain.model.AbstractBaseEntity;
import com.fenixcommunity.centralspace.domain.model.account.Account;
import com.fenixcommunity.centralspace.domain.utils.converter.CryptoJpaConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
import java.io.Serializable;

//immutable objects, like @Value
// Builder gdy mamy pola final i chcemy je tworzyc w elastycznym konstruktorze
// Builder stosujemy gdy tworzymy obiekt raz i nie zmianiamy go potem przez sety
//@Setter(value = AccessLevel.PACKAGE)
@Entity
@Table(name = "password")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Password extends AbstractBaseEntity implements Serializable {

    //TODO         return Optional.ofNullable(isoCountryCode)
    //             Optional.of(nowa wartosc gdy damy geta optional);


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Convert(converter = CryptoJpaConverter.class)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "password_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PasswordType passwordType;

}