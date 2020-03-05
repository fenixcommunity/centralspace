package com.fenixcommunity.centralspace.domain.model.mounted.account;

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
import com.fenixcommunity.centralspace.domain.model.mounted.AbstractBaseEntity;
import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
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

    //  TODO  AuditingEntityListener co to?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Convert(converter = UppercaseConverter.class)
    @Column(name = "login", nullable = false)
    private String login;

    //TODO walidacja i opakowac
    @Column(name = "mail", nullable = false)
    private String mail;

    @ToString.Exclude
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Password> passwords;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;
}
