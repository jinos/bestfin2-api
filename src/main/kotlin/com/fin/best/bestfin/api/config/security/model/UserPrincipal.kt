package com.fin.best.bestfin.api.config.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(
        private val username: String,
        private val password: String,
        private val authorities: Set<GrantedAuthority>
) : UserDetails {
    override fun getUsername(): String = username
    override fun getPassword(): String = password
    override fun isEnabled(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun getAuthorities(): Set<GrantedAuthority> = authorities
}