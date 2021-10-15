package jp.classmethod.aws.metropolis.r2dbcspring.repositories

import jp.classmethod.aws.metropolis.r2dbcspring.entity.Customer
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CustomerRepository: ReactiveCrudRepository<Customer, Int> {

    @Query("select * from customer c where c.user_name == $1")
    fun findByUserName(userName: String);
}