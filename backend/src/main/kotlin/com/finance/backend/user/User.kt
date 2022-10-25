package com.finance.backend.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.finance.backend.auth.response.LoginDao
import com.finance.backend.auth.request.SignupDto
import com.finance.backend.util.Timestamped
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity(name = "user")
class User(
        name : String,
        password: String,
        phone : String,
        birth : Date,
        sex : Int,
        type : String
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

    @Column(nullable = false, unique=true)
    var phone: String = phone
        protected set

    @Column
    var birth: Date = birth
        protected set

    @Column
    var sex: Boolean = (sex % 2 == 0)

    @Column(nullable = false)
    var pfId: Long = (Random().nextInt(9) + 1).toLong()

    @Column
    var point : Long = 0

    @Column
    var account: String? = null

    @Column
    var type: String = type

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
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

    fun pfId(pfId: Long) {
        this.pfId = pfId
    }

    fun addPoint(point : Int) {
        this.point += point
    }

    fun account(account: String) {
        this.account = account
    }

    fun toMember(password: String, birth : Date, sex : Int) {
        this.password = password
        this.birth = birth
        this.sex = (sex % 2 == 0)
        this.type = "회원"
    }

    fun toLoginEntity() : LoginDao = LoginDao(this.name, this.id, this.accessToken, this.refreshToken)
}