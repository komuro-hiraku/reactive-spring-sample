package jp.classmethod.aws.metropolis.r2dbcspring

import io.r2dbc.spi.ConnectionFactory
import jp.classmethod.aws.metropolis.r2dbcspring.exception.CustomerNotFoundException
import jp.classmethod.aws.metropolis.r2dbcspring.handler.CustomerHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import kotlin.io.path.Path

// references original: https://tech.uzabase.com/entry/2021/01/13/150022
@SpringBootApplication
class R2dbcSpringApplication {

	@Bean
	fun setupEndPoints(customerHandler: CustomerHandler): RouterFunction<ServerResponse> {
		return route()
			.nest(path("/customers")
			) { builder ->
				builder
					.GET("/{id}", customerHandler::find)
					.GET(customerHandler::findAll)
					.POST(customerHandler::create)
					.filter { req, res ->
						res.handle(req)
							.onErrorResume(
								CustomerNotFoundException::class.java
							) { e -> ServerResponse.notFound().build() }
					}.build()
			}.build()
	}

	@Bean
	fun initializer(factory:ConnectionFactory): ConnectionFactoryInitializer {
		val initializer = ConnectionFactoryInitializer()
		initializer.setConnectionFactory(factory)

		val populator = CompositeDatabasePopulator()
		populator.addPopulators(ResourceDatabasePopulator(
			ClassPathResource("./db-schema.sql")
		))

		initializer.setDatabasePopulator(populator)
		return initializer
	}
}

// program entry point
fun main(args: Array<String>) {
	runApplication<R2dbcSpringApplication>(*args)
}


