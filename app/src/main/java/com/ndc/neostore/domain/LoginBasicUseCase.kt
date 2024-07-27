package com.ndc.neostore.domain

import com.ndc.neostore.data.repository.AuthRepository
import javax.inject.Inject

class LoginBasicUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String, password: String) =
        authRepository.loginBasic(email, password)
}