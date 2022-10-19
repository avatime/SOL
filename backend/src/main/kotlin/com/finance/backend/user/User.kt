package com.finance.backend.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.auth.SignupDto
import com.finance.backend.util.Timestamped
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity(name = "user")
class User(
        name : String,
        password: String
) : Timestamped() {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID = UUID.randomUUID()

    @Column
    var accessToken: String = ""

    @Column
    var refreshToken: String = ""

    @Column(nullable = false)
    var name: String = name
        protected set

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    var password: String = password
        protected set

    fun updateUser(userDto: SignupDto) {
        this.name = userDto.username
        this.password = userDto.password
    }

    fun accessToken(accessToken: String) {
        this.accessToken = accessToken
    }

    fun refreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

}