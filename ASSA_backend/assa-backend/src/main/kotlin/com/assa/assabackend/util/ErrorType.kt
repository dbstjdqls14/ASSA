package com.assa.assabackend.util


enum class ErrorType(
    val code: String,
    val message: String,
    val status: Int
) {
    // 400 Bad Request
    BAD_REQUEST("40000", "잘못된 요청입니다.", 400),
    MISSING_REQUIRED_FIELD("40001", "필수 정보가 누락되었습니다.", 400),
    INVALID_REQUEST_FORMAT("40002", "요청 형식이 올바르지 않습니다.", 400),
    INVALID_PARAMETER("40003", "요청 파라미터가 올바르지 않습니다.", 400),
    INVALID_JSON_FORMAT("40004", "JSON 형식이 올바르지 않습니다.", 400),

    // 401 Unauthorized
    UNAUTHORIZED("40100", "인증이 필요합니다.", 401),
    INVALID_TOKEN("40101", "유효하지 않은 토큰입니다.", 401),
    EXPIRED_TOKEN("40102", "만료된 토큰입니다.", 401),
    MALFORMED_TOKEN("40103", "잘못된 형식의 토큰입니다.", 401),
    MISSING_TOKEN("40104", "토큰이 누락되었습니다.", 401),

    // 403 Forbidden
    FORBIDDEN("40300", "접근 권한이 없습니다.", 403),
    INVALID_REFRESH_TOKEN("40301", "유효하지 않은 리프레시 토큰입니다.", 403),
    ACCESS_DENIED("40302", "접근이 거부되었습니다.", 403),
    INSUFFICIENT_PRIVILEGES("40303", "권한이 부족합니다.", 403),

    // 404 Not Found
    NOT_FOUND("40400", "요청한 리소스를 찾을 수 없습니다.", 404),
    USER_NOT_FOUND("40401", "사용자를 찾을 수 없습니다.", 404),
    ENDPOINT_NOT_FOUND("40402", "존재하지 않는 API 엔드포인트입니다.", 404),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED("40500", "허용되지 않은 HTTP 메서드입니다.", 405),

    // 409 Conflict
    CONFLICT("40900", "리소스 충돌이 발생했습니다.", 409),
    DUPLICATE_EMAIL("40901", "이미 사용 중인 이메일입니다.", 409),
    DUPLICATE_USERNAME("40902", "이미 사용 중인 사용자명입니다.", 409),


    // 429 Too Many Requests
    TOO_MANY_REQUESTS("42900", "요청 횟수가 제한을 초과했습니다.", 429),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("50000", "서버 내부 오류가 발생했습니다.", 500),
    DATABASE_ERROR("50001", "데이터베이스 오류가 발생했습니다.", 500),
    EXTERNAL_API_ERROR("50002", "외부 API 호출 중 오류가 발생했습니다.", 500),
    FILE_PROCESSING_ERROR("50003", "파일 처리 중 오류가 발생했습니다.", 500);
}
