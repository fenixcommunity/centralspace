package com.fenixcommunity.centralspace.domain.model.account;

import com.fenixcommunity.centralspace.domain.core.AccountEntityListener;
import com.fenixcommunity.centralspace.domain.model.AbstractBaseEntity;
import com.fenixcommunity.centralspace.domain.model.password.Password;
import com.fenixcommunity.centralspace.domain.utils.converter.UppercaseConverter;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@EntityListeners(AccountEntityListener.class)
@Table(name = "account")
//@NoArgsConstructor @AllArgsConstructor(access= AccessLevel.PUBLIC)
@Data @Builder @EqualsAndHashCode(callSuper = true) @ToString()
public class Account extends AbstractBaseEntity {

//  TODO  AuditingEntityListener co to?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Convert(converter = UppercaseConverter.class)
    @Column(name = "login", nullable = false)
    private String login;

    //TODO walidacja i opakowac
    @Column(name = "email", nullable = false)
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Password> passwords;




}
