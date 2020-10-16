package com.fenixcommunity.centralspace.domain.model.permanent.account;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fenixcommunity.centralspace.domain.converter.UppercaseConverter;
import com.fenixcommunity.centralspace.domain.core.AccountEntityListener;
import com.fenixcommunity.centralspace.domain.model.permanent.AbstractBaseEntity;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@Entity @Table(name = "account") @EntityListeners(AccountEntityListener.class)
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @ToString() @FieldDefaults(level = PRIVATE)
public class Account extends AbstractBaseEntity {

    //todo AuditingEntityListener add some things
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Convert(converter = UppercaseConverter.class)
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    //TODO walidacja i opakowac
    @Column(name = "mail", nullable = false)
    private String mail;

    @ToString.Exclude
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    //todo Set<...  sort by ...
    private List<Password> passwords;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    private String createdBy; //todo add Admin
}
