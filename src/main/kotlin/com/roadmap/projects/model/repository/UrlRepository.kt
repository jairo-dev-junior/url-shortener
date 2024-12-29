package com.roadmap.projects.model.repository

import com.roadmap.projects.exceptions.UrlException
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface UrlRepository: JpaRepository<UrlEntity, Long> {
    fun findByShortCode(shortCode: String): UrlEntity
    fun deleteByShortCode(shortCode: String)
}