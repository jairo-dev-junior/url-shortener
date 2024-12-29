package com.roadmap.projects.service

import com.roadmap.projects.exceptions.UrlException
import com.roadmap.projects.model.repository.UrlEntity
import com.roadmap.projects.model.repository.UrlRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.time.Instant
import java.util.*

@Singleton
class UrlService(
    @Inject
    private var repository: UrlRepository
) {
    private val SAVING_ERROR_TEXT = "Error on save the url"
    private val RETRIEVING_ERROR_TEXT = "Error on retrieve the url"
    private val UPDATING_ERROR_TEXT = "Error on update the url"
    private val DELETING_ERROR_TEXT = "Error on update the url"

    fun postUrl(url: String):String =
        try {

            val urlToSave = UrlEntity(url, createShortCode(url))
            repository.save(urlToSave).shortCode ?: throw RuntimeException(SAVING_ERROR_TEXT)
        } catch (e: Exception) {
            throw UrlException(e.message ?: SAVING_ERROR_TEXT, e)
        }

    fun getUrl(shortCode: String): String =
        try {
            repository.findByShortCode(shortCode).url ?: throw RuntimeException(RETRIEVING_ERROR_TEXT)
        } catch (e: Exception) {
            throw UrlException(e.message ?: RETRIEVING_ERROR_TEXT, e)
        }

    fun updateUrl(shortCode: String, newUrl: String): String =
        try {
            repository.update(repository.findByShortCode(shortCode)
                .copy(url = newUrl, updatedAt = Date.from(Instant.now()))).shortCode ?: UPDATING_ERROR_TEXT
        } catch (e: Exception) {
            throw UrlException(e.message ?:UPDATING_ERROR_TEXT, e)
        }

    fun deleteUrl(shortCode: String) =
        try {
            repository.deleteByShortCode(shortCode)
        } catch (e: Exception) {
            throw UrlException(e.message ?: DELETING_ERROR_TEXT, e)
        }

    fun createShortCode(url:String): String {
        return url.split(".").let { String.format("%s-%s", it[1], Instant.now().toEpochMilli().toString()) }
    }

}