package com.motemote_gitflow.model

import com.motemote_gitflow.dto.AccountDto
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime
import java.util.stream.Collectors

@Entity
data  class Account (
    @Id @GeneratedValue
    var id: Long? = null,
    var email: String? = null,
    var password: String? = null,

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableSet<AccountRole> = mutableSetOf(AccountRole.USER),

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

    fun toDto(): AccountDto {
        return AccountDto(
            email = this.email,
            password = this.password
        )
    }

}