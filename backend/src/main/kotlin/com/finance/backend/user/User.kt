package com.finance.backend.user

import lombok.Getter
import javax.persistence.*

@Entity(name = "user")
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(updatable = false, nullable = false, columnDefinition = "INT UNSIGNED")
        var id: String? = null,
        name: String
) {
        @Column
        var name : String = ""
            protected set

}