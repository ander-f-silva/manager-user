package br.com.pp.user.controller

import br.com.pp.user.dto.UserResponse
import br.com.pp.user.service.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/users")
@CrossOrigin(value = "http://localhost",
        allowedHeaders = ["Access-Control-Allow-Origin"],
        maxAge = 900
)
class UserController(val userService: UserService) {
    @GetMapping("/name/{name}/username/{userName}")
    fun findByNameAndUserName(@PathVariable name: String, @PathVariable userName: String, @RequestParam(defaultValue = "0") page: Long, @RequestParam(defaultValue = "15") size: Long): Flux<UserResponse> {
        return userService.findByNameAndUserName(name, userName, page, size).map { UserResponse(it.id, it.name, it.userName) }
    }
}