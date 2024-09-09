package com.motemote_gitflow.repository

import com.motemote_gitflow.dto.AccountDto
import org.springframework.stereotype.Repository

@Repository
interface JooqAccountRepository {
    fun findByEmail(email : String) : AccountDto
}