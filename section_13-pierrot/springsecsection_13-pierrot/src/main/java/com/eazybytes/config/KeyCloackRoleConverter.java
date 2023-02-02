package com.eazybytes.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyCloackRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (CollectionUtils.isEmpty(realmAccess)) {
            return new ArrayList<>();
        }

        List<String> authorities = (List<String>) realmAccess.get("roles");

        return authorities.stream()
                .map(role -> "ROLE_"+role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
