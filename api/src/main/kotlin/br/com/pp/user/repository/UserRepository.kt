package br.com.pp.user.repository

import br.com.pp.user.domain.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface UserRepository : ReactiveMongoRepository<User,String> {
    fun findByNameLikeAndUserNameLike(name: String, userName: String,sort: Sort) : Flux<User>
}