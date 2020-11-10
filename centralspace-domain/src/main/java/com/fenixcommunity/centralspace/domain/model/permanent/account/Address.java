package com.fenixcommunity.centralspace.domain.model.permanent.account;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fenixcommunity.centralspace.domain.model.permanent.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity @Table(name = "address") // , schema = "contact_info" -> we should add schema manually
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @ToString() @FieldDefaults(level = PRIVATE)
public class Address extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city")
    private String city;

    @Singular @ToString.Exclude
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Account> accounts;
}