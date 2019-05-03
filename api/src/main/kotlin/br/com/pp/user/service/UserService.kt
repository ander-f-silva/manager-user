package br.com.pp.user.service

import br.com.pp.user.domain.User
import br.com.pp.user.exception.NotFoundException
import br.com.pp.user.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class UserService(val userRepository: UserRepository) {
    companion object {
        val ORDER_RANK = "rank"
        val ORDER_NAME = "name"
        val ORDER_USER_NAME = "name"
    }

    fun findByNameAndUserName(name: String, userName: String, page: Long, size: Long): Flux<User> {
        return userRepository.findByNameLikeAndUserNameLike(name, userName, Sort.by(Sort.Order.asc(ORDER_RANK), Sort.Order.asc(ORDER_NAME), Sort.Order.asc(ORDER_USER_NAME)))
                .switchIfEmpty(Flux.error(NotFoundException("")))
                .skip(page * size)
                .take(size)
    }
}