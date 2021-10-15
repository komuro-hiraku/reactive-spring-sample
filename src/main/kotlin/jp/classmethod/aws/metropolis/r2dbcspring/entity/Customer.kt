package jp.classmethod.aws.metropolis.r2dbcspring.entity

import org.springframework.data.annotation.Id

data class Customer (
    @Id
    val id: Int?,

    val userName: String
)

