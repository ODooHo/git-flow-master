package com.motemote_gitflow.model

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.proxy.HibernateProxy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime
import java.util.stream.Collectors

@Entity
data class Account (
    @Id @GeneratedValue
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableSet<AccountRole>,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now()
){
        fun getAuthorities(): User{
            return User(
                this.email, this.password,
                this.roles.stream().map{
                    role -> SimpleGrantedAuthority("ROLE_$role")
                }.collect(Collectors.toSet())
            )
        }

}