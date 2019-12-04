package com.fenixcommunity.centralspace.domain.model.account;

import com.fenixcommunity.centralspace.domain.converter.UppercaseConverter;
import com.fenixcommunity.centralspace.domain.core.AccountEntityListener;
import com.fenixcommunity.centralspace.domain.model.AbstractBaseEntity;
import com.fenixcommunity.centralspace.domain.model.password.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@EntityListeners(AccountEntityListener.class)
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString()
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

}
