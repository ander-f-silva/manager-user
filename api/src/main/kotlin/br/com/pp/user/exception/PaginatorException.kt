package br.com.pp.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY, reason = "The page or size must be greater than zero")
class PaginatorException : RuntimeException()