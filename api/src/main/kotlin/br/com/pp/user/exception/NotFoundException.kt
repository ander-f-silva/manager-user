package br.com.pp.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "User not found with parameters informed")
class NotFoundException(message: String?) : RuntimeException(message)