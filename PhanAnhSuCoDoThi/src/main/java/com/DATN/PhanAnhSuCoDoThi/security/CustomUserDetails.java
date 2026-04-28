package com.DATN.PhanAnhSuCoDoThi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String email;
    private String matKhau;   // mật khẩu
    private String maTaiKhoan;     // mã tài khoản
    private List<String> quyens;
    private String refMa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return quyens.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return matKhau;
    }

    @Override
    public String getUsername() {

        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
