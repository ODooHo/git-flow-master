package com.motemote_gitflow.dto

import com.motemote_gitflow.model.Account
import com.motemote_gitflow.model.AccountRole
import java.time.LocalDateTime

data class AccountDto (
    val email: String? = null,
    val password: String? = null
)

fun AccountDto.toDomain() = Account(
    email = this.email.orEmpty(),
    password = this.password.orEmpty(),
    roles = mutableSetOf(AccountRole.USER),
    createdAt = LocalDateTime.now()
)