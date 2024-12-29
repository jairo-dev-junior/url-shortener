package com.roadmap.projects.controller

import com.roadmap.projects.model.request.UrlBodyRequest
import com.roadmap.projects.service.UrlService
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import jakarta.inject.Inject
import io.micronaut.http.HttpResponse as Response

@Controller
class UrlEntrypoint(
    @Inject
    private val service: UrlService
) {
    @Get("/shorten/{short_code}")
    fun getUrlByShortCode(@QueryValue("short_code") shortCode: String): MutableHttpResponse<String>? =
        service.getUrl(shortCode).let { Response.ok(it) }

    @Post("/shorten")
    fun postUrl(@Body body: UrlBodyRequest): MutableHttpResponse<String>? =
        service.postUrl(body.url).let { Response.created(it) }

    @Put("/shorten/{short_code}")
    fun updateUrl(@QueryValue("short_code") shortCode: String, @Body body: UrlBodyRequest): MutableHttpResponse<String>? =
        service.updateUrl(shortCode, body.url).let { Response.ok(it) }

    @Delete("/shorten/{short_code}")
    fun deleteUrl(@QueryValue("short_code") shortCode: String): MutableHttpResponse<Unit> =
        service.deleteUrl(shortCode).let { Response.ok() }


}