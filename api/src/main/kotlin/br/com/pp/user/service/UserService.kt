package br.com.pp.user.service

import br.com.pp.user.domain.User
import br.com.pp.user.exception.NotFoundException
import br.com.pp.user.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import org.springframework.data.domain.Sort.Direction.ASC

@Service
class UserService(val userRepository: UserRepository) {
    companion object {
        const val ORDER_RANK = "rank"
        const val ORDER_NAME = "name"
    }

    fun findByNameAndUserName(name: String, userName: String): Flux<User> {
        return userRepository.findByNameAndUserName("^$name", "^$userName", Sort(ASC, ORDER_RANK, ORDER_NAME))
    }
}