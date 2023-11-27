package com.ppidev.smartcube.common

enum class EExceptionCode(val code: Int) {
    IOException(-1),
    HTTPException(-2),
    RepositoryError(-3),
    UseCaseError(-4),
}

enum class EHTTPCode(val code: Int) {
    Ok(200),
    Created(201),
    Unauthorized(401),
    ServiceUnavailable(503)
}