package com.fenixcommunity.centralspace.Account;


import com.fenixcommunity.centralspace.Password.Password;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Data
// bez tego pola bylyby final
@AllArgsConstructor(access= AccessLevel.PUBLIC)
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String login;
//    private Password password;

}
