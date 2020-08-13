package com.fin.best.bestfin.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.stereotype.Component

@Component
class MultipartJackson2HttpMessageConverter : AbstractJackson2HttpMessageConverter {
    constructor(objectMapper: ObjectMapper) : super(objectMapper, MediaType.APPLICATION_OCTET_STREAM)
}