package com.ih.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ih.model.Merchant;
import com.ih.repository.MerchantRepository;

@Service
public class MerchantDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Merchant merchant = merchantRepository.findByUsername(username);
        if (merchant == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(merchant.getRole().getCode()));

        return new org.springframework.security.core.userdetails.User(merchant.getUsername(), merchant.getPassword(), grantedAuthorities);
    }
}
