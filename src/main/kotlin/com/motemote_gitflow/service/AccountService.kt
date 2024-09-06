package com.motemote_gitflow.service

import com.motemote_gitflow.dto.AccountDto
import com.motemote_gitflow.dto.SignUpDto
import com.motemote_gitflow.dto.toDomain
import com.motemote_gitflow.model.Account
import com.motemote_gitflow.repository.JpaAccountRepository
import org.jooq.DSLContext
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val jpaAccountRepository: JpaAccountRepository,
    private val passwordEncoder: PasswordEncoder,
    @Lazy private val dsl : DSLContext
): UserDetailsService{
    private val logger = LoggerFactory.getLogger(javaClass)

    fun signUp(dto : SignUpDto): Account {
        val account = dto.toDomain()
        account.password = this.passwordEncoder.encode(account.password)
        return jpaAccountRepository.save(account);
    }
    fun saveAccount(account : Account): Account {
        account.password = this.passwordEncoder.encode(account.password)
        return jpaAccountRepository.save(account);
    }

    override fun loadUserByUsername(username: String): UserDetails {
        logger.info("success")
        return jpaAccountRepository.findByEmail(username)?.getAuthorities()
            ?: throw UsernameNotFoundException("$username can't Found")
    }

    fun findByEmail(dto: AccountDto) : AccountDto{
        val account = dto.toDomain()
        val where = dsl.select()
            .from("account")
            .where("email = ?", account.email)
            .fetchOne()

        return where.let{
            AccountDto(
                email = it?.get("email") as String,
                password = it.get("password") as String
            )
        }
    }


}