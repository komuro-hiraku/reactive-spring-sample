package jp.classmethod.aws.metropolis.r2dbcspring.handler

import jp.classmethod.aws.metropolis.r2dbcspring.dto.CustomerDto
import jp.classmethod.aws.metropolis.r2dbcspring.entity.Customer
import jp.classmethod.aws.metropolis.r2dbcspring.repositories.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class CustomerHandler(val customerRepository: CustomerRepository) {

    // 顧客情報一覧取得ハンドラ
    fun findAll(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(customerRepository.findAll()
                .map { CustomerDto(it.id, it.userName) }, CustomerDto::class.java
            )
    }

    // ID 指定で任意の顧客情報取得ハンドラ
    fun find(request: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().build()

    // 顧客情報登録ハンドラ
    fun create(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(CustomerDto::class.java)
            .map { Customer(it.id, it.name) }
            .map { customerRepository.save(it) }
            .flatMap {c -> ServerResponse.status(HttpStatus.CREATED)
                .body(c.map {CustomerDto(it.id, it.userName)}, CustomerDto::class.java)}
    }
}
