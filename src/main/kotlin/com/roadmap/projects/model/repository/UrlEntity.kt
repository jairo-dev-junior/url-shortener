package com.roadmap.projects.model.repository

import io.micronaut.data.annotation.AutoPopulated
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Cascade
import java.time.Instant
import java.util.Date

@Entity
data class UrlEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val url: String?,
    @Column(unique = true)
    val shortCode: String?,
    @AutoPopulated
    val createdAt: Date?,
    val updatedAt: Date?
) {
    constructor(url: String, shortCode: String) : this(null, url, shortCode, Date.from(Instant.now()), null)
}