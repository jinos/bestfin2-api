package com.fin.best.bestfin.api.app.test

import com.fin.best.bestfin.api.config.security.BestFinAccountService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/bestfin"])
class TestController
@Autowired constructor(
        private val authTokenService: BestFinAccountService
) {
    private val logger = LoggerFactory.getLogger(TestController::class.java);

    /*@RequestMapping(value = ["/test"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchTokens(
    ): ResponseHandler<*> {
        val result = authTokenService.fetchTokens()
        return ResponseHandler(
                statusCode = 200,
                responseBody = result)
    }*/
}