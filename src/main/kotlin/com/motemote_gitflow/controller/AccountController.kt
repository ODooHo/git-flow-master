package com.motemote_gitflow.controller


import com.motemote_gitflow.dto.AccountDto
import com.motemote_gitflow.dto.SignUpDto
import com.motemote_gitflow.service.AccountService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class AccountController(private val accountService: AccountService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpDto: SignUpDto): AccountDto{
        logger.info("email: ${signUpDto.email}")
        val result = accountService.signUp(signUpDto)
        return result.toDto()
    }

    @PostMapping("/login")
    fun login(@RequestBody accountDto: AccountDto): String{
        logger.info("email: ${accountDto.email}")
        val email= accountDto.email ?: throw IllegalArgumentException("can't be null!")
        return accountService.loadUserByUsername(email).toString()
    }

    @PostMapping("/findEmail")
    fun findEmail(@RequestBody accountDto: AccountDto): ResponseEntity<AccountDto>{
        logger.info("email : ${accountDto.email}")
        val result = accountService.findByEmail(accountDto)
        return ResponseEntity.ok(result)
    }
}