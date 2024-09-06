package com.motemote_gitflow.repository

import com.motemote_gitflow.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaAccountRepository: JpaRepository<Account,Long> {
    fun findByEmail(email: String): Account?
}