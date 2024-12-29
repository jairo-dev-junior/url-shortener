package com.roadmap.projects.model.request

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class UrlBodyRequest (
    val url: String
)