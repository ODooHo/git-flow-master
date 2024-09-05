package com.motemote_gitflow.viewmodel

import com.motemote_gitflow.dto.AccountDto
import com.motemote_gitflow.dto.SignUpDto
import com.motemote_gitflow.dto.toDomain
import com.motemote_gitflow.model.Account
import com.motemote_gitflow.service.AccountService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AccountViewModel (private val accountService: AccountService){
    private val logger = LoggerFactory.getLogger(javaClass)

    fun saveAccount(dto: SignUpDto): AccountDto {
        return try{
            val saveAccount = accountService.saveAccount(dto.toDomain())
            saveAccount.toDto()
        }catch (e: Exception){
            logger.error("Error creating user: ${e.message}")
            throw IllegalArgumentException();
        }
    }

    fun loadUserByUsername(username: String){
        try{
            accountService.loadUserByUsername(username)
        }
        catch (e: Exception){
            logger.error("Error loading user: ${e.message}")
        }
    }
}