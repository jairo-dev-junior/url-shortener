package com.roadmap.projects.exceptions

class UrlException(msg: String, err: Throwable): RuntimeException(msg, err) {
}