package com.roadmap.projects.service

import com.roadmap.projects.exceptions.UrlException
import com.roadmap.projects.model.repository.UrlEntity
import com.roadmap.projects.model.repository.UrlRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.Instant
import java.time.temporal.TemporalAccessor
import java.util.*


class UrlServiceTest {

    @MockK
    private lateinit var repository: UrlRepository

    @InjectMockKs
    private lateinit var target: UrlService


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given a long url should call the repository and return a url shorter`() {
        val savedUrl = UrlEntity(
            1,
            "www.google.com",
            "url12345543",
            Date.from(Instant.now()),
            Date.from(Instant.now())
        )
        every { repository.save(any()) } returns savedUrl

        assertEquals(savedUrl.shortCode, target.postUrl("www.google.com"))
    }

    @Test
    fun `given a url when has an error should return an exception`() {
        every { repository.save(any()) } throws RuntimeException()

        assertThrows(UrlException::class.java) { target.postUrl("www.google.com") }
    }

    @Test
    fun `given a shorturl should return the true url`() {
        val savedUrl = UrlEntity(
            1,
            "www.google.com",
            "url123",
            Date.from(Instant.now()),
            Date.from(Instant.now())
        )
        every { repository.findByShortCode(any()) } returns savedUrl

        assertEquals(savedUrl.url, target.getUrl("url1233"))
    }

    @Test
    fun `given a wrong shorturl  should return an error`() {
        every { repository.findByShortCode(any()) } throws RuntimeException()

        assertThrows(UrlException::class.java) { target.getUrl("url123") }
    }

    @Test
    fun `given a shortUrl should delete it`() {
        every { repository.deleteByShortCode(any()) } just runs

        assertDoesNotThrow{target.deleteUrl("url1233")}
    }

    @Test
    fun `given a shortcode and a url should update shortCode`() {
        val savedUrl = UrlEntity(
            1,
            "www.google.com",
            "url123",
            null,
            null
        )
        val newUrl = savedUrl.copy(url = "www.youtube.com", updatedAt = Date())

        every { repository.findByShortCode(any()) } returns savedUrl
        every { repository.update(any()) } returns newUrl

        assertEquals(newUrl.shortCode, target.updateUrl("url1233", "www.youtube.com"))
        verify { repository.update(any()) }
    }
}