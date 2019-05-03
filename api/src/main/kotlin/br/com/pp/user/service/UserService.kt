package br.com.pp.user.service

import br.com.pp.user.domain.User
import br.com.pp.user.exception.NotFoundException
import br.com.pp.user.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

import org.springframework.data.domain.Sort.Order.asc
import org.springframework.data.domain.Sort.by


@Service
class UserService(val userRepository: UserRepository) {
    companion object {
        const val ORDER_RANK = "rank"
        const val ORDER_NAME = "name"
        const val ORDER_USER_NAME = "name"
    }

    fun findByNameAndUserName(name: String, userName: String, page: Long, size: Long): Flux<User> {
        return userRepository.findByNameAndUserName("^$name", "^$userName", by(asc(ORDER_RANK), asc(ORDER_NAME), asc(ORDER_USER_NAME)))
                .switchIfEmpty(Flux.error(NotFoundException()))
                .skip(page * size)
                .take(size)
    }
}