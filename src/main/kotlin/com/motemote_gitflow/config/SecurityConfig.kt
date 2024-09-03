package com.motemote_gitflow.config

import com.motemote_gitflow.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.WebSecurityConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
class SecurityConfig(
    @Autowired private val accountService: AccountService,
    @Autowired private val passwordEncoder: PasswordEncoder
) {

    companion object {
        const val LOGIN_SUCCESS_URL: String = "/view/success"
    }

    @Bean
    fun authenticationManager(auth: AuthenticationConfiguration): AuthenticationManager {
        return auth.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form
                    .successHandler { request, response, authentication ->
                        response.sendRedirect(request.getParameter("continue") ?: LOGIN_SUCCESS_URL)
                    }
                    .failureUrl("/login?error=true")
            }
            .logout { logout ->
                logout.logoutSuccessUrl("/login?logout=true")
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return accountService
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return passwordEncoder
    }
}
