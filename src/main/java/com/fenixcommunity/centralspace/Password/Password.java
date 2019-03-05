package com.fenixcommunity.centralspace.Password;

import com.fenixcommunity.centralspace.Account.Account;
import com.fenixcommunity.centralspace.Converter.CryptoJpaConverter;
import com.fenixcommunity.centralspace.Jpa.AbstractBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

//immutable objects, like @Value
// Builder gdy mamy pola final i chcemy je tworzyc w elastycznym konstruktorze
// Builder stosujemy gdy tworzymy obiekt raz i nie zmianiamy go potem przez sety
//@Setter(value = AccessLevel.PACKAGE)
@Entity
@Table(name = "password")
//@Getter @Setter
@Data @Builder @EqualsAndHashCode(callSuper = true)
public class Password extends AbstractBaseEntity implements Serializable {

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