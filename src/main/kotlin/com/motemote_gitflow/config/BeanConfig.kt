package com.motemote_gitflow.config

import com.motemote_gitflow.model.Account
import com.motemote_gitflow.model.AccountRole
import com.motemote_gitflow.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeanConfig {
    @Bean
    fun passwordEncoder() : PasswordEncoder{
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun applicationRunner(): ApplicationRunner {
        return object : ApplicationRunner {

            @Autowired
            lateinit var accountService: AccountService

            @Throws(Exception::class)
            override fun run(args: ApplicationArguments) {
                val admin = Account(null,
                    "engh0205@naver.com",
                    "1234",
                    mutableSetOf(AccountRole.ADMIN, AccountRole.USER))
                accountService.saveAccount(admin)
            }
        }
    }
}