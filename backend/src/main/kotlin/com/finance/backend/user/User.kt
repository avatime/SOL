package com.finance.backend.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.util.Timestamped
import javax.persistence.*

@Entity(name = "user")
class User(
        name : String,
        password: String
) : Timestamped() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
    var id: String? = null

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

    fun updateUser(userDto: UserDto) {
        this.name = userDto.username
        this.password = userDto.password
    }

}