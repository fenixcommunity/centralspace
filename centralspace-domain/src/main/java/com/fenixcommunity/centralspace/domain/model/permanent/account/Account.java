package com.fenixcommunity.centralspace.domain.model.permanent.account;

import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fenixcommunity.centralspace.domain.converter.CryptoJpaConverter;
import com.fenixcommunity.centralspace.domain.converter.UppercaseConverter;
import com.fenixcommunity.centralspace.domain.core.listener.AccountEntityListener;
import com.fenixcommunity.centralspace.domain.model.permanent.AbstractBaseEntity;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.role.RoleGroup;
import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.PESEL;

// @NamedQuery(name = "Account.findByAccountId" ...
@Entity @Table(name = "account") @EntityListeners(AccountEntityListener.class)
// @DynamicUpdate -> when we update login then update Account set login=?, mail=? ... where id=?
// if we want update only login column then @DynamicUpdate -> update Account set name=? where id=?
// use only when we have a lot of columns -> in other cases performance overhead
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @ToString() @FieldDefaults(level = PRIVATE)
public class Account extends AbstractBaseEntity {
    private static final long serialVersionUID = 2408811853604950911L;

    //todo AuditingEntityListener add some things
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @JMap("uniqueLogin")
    @Convert(converter = UppercaseConverter.class)
    @Length(min = 2, max = 15)
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    //TODO walidacja i opakowac
    @JMap
    @Column(name = "mail", nullable = false)
    private String mail;

    @PESEL
    @Convert(converter = CryptoJpaConverter.class)
    @Column(name = "pesel")
    private String pesel;

    @NIP
    @Convert(converter = CryptoJpaConverter.class)
    @Column(name = "nip")
    private String nip;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Singular @ToString.Exclude
    private List<Password> passwords;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_to_role_group",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_group_id"))
    @OrderBy(clause = "name DESC")
    @Singular
    private Set<RoleGroup> roleGroups;

//     TODO [!] TO Avoid LazyInitException:
//    - @Transactional
//    - spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
//    - " JOIN FETCH a.address " +
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "consent_expired_date")
    private ZonedDateTime dataBaseConsentExpiredDate;

    @Column(name = "blocked", columnDefinition = "boolean default false")
    @ColumnDefault("false")
    private boolean blocked;

}
