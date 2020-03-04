package com.fenixcommunity.centralspace.app.utils.mapper;

import static com.fenixcommunity.centralspace.utilities.common.Var.ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.LOGIN;
import static com.fenixcommunity.centralspace.utilities.common.Var.MAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto;
import com.fenixcommunity.centralspace.app.rest.dto.AccountDto2;
import com.fenixcommunity.centralspace.app.rest.mapper.AccountMapper;
import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import com.fenixcommunity.centralspace.utilities.common.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.AbstractProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.config.Configuration;

public class ModelMapperTest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime dateTime;

    private ModelMapper modelMapper;
    private Account initAccount;
    private AccountDto initAccountDto;

    @BeforeEach
    public void before() {
        Provider<AccountDto2> barProvider = new AbstractProvider<>() {
            @Override
            protected AccountDto2 get() {
                return AccountDto2.builder().build();
            }
        };
//        modelMapper = new ModelMapper();
//        Configuration builderConfiguration = modelMapper.getConfiguration().copy();
////                .setDestinationNameTransformer(NameTransformers.builder())
////                .setDestinationNamingConvention(NamingConventions.builder());
//        builderConfiguration.setDestinationNamingConvention((name, type) -> true);
//        builderConfiguration.setDestinationNameTransformer((name, type) -> name);
//        modelMapper.createTypeMap(Account.class, AccountDto2.AccountDto2Builder.class, builderConfiguration);


//        modelMapper = new ModelMapper();
//        modelMapper.addMappings(new AccountMap());
//        modelMapper.getConfiguration()
//                .setProvider(barProvider)
//                .setMatchingStrategy(MatchingStrategies.STRICT);


        initAccount = Account.builder()
                .id(ID)
                .login(LOGIN)
//                .mail(MAIL)
                .passwords(Collections.singletonList(null))
                .build();
        initAccountDto = AccountMapper.mapToDto(initAccount, Level.HIGH);
    }

    @Test
    void basicMappingTest() {
        AccountMap accountMap = new AccountMap();
        AccountDto accountDto =  accountMap.map(initAccount);
        assertEquals(ID, accountDto.getId());
        assertEquals(LOGIN, accountDto.getLogin());
        assertEquals(MAIL, accountDto.getMail());
    }
//    https://stackoverflow.com/questions/46530323/java-object-mapping-framework-working-with-builder-pattern
}
