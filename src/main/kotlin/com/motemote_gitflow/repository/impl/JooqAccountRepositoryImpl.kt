package com.motemote_gitflow.repository.impl

import com.motemote_gitflow.dto.AccountDto
import com.motemote_gitflow.repository.JooqAccountRepository
import com.motemote_gitflow.tables.Account
import org.jooq.DSLContext
import org.springframework.context.annotation.Lazy

class JooqAccountRepositoryImpl(
    @Lazy private val dsl : DSLContext
): JooqAccountRepository {
    override fun findByEmail(email: String): AccountDto{
        val account = Account.ACCOUNT
        val selectedUser = dsl.select()
            .from(account)
            .where("email = ?", email)
            .fetchOne()

        val result = selectedUser.let {
            AccountDto(
                email = it?.get("email") as String,
                password = it.get("password") as String
            )
        }
        return result
    }
}