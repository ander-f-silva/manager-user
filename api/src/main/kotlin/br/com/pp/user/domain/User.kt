package br.com.pp.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("users")
data class User(@Id val id: String, @Indexed val name: String, @Indexed val userName: String, @Indexed val rank: Int, val createDate: LocalDateTime)