package com.fenixcommunity.centralspace.domain.model.memory.h2;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fenixcommunity.centralspace.domain.model.permanent.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Entity @Table(name = "session_app_info")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true) @FieldDefaults(level = PRIVATE)
public class SessionAppInfo extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "some_info", nullable = false)
    private String someInfo;
}