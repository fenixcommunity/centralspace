package com.fenixcommunity.centralspace.app.utils.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailsDto {
    private String country;
    private String phoneNumber;
}