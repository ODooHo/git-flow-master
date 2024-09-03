package com.motemote_gitflow.service

import com.motemote_gitflow.model.Account
import com.motemote_gitflow.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
): UserDetailsService{
    private val logger = LoggerFactory.getLogger(javaClass)

    fun saveAccount(account: Account): Account {
        account.password = this.passwordEncoder.encode(account.password)
        return accountRepository.save(account);
    }

    override fun loadUserByUsername(username: String): UserDetails {
        logger.info("success")
        return accountRepository.findByEmail(username)?.getAuthorities()
            ?: throw UsernameNotFoundException("$username can't Found")
    }

}