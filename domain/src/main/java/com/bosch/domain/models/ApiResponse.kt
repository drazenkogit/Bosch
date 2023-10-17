package com.bosch.domain.models

sealed class ApiResponse<T : Any> {
    class Loading<T: Any>: ApiResponse<T>()
    class Success<T: Any>(val data: T) : ApiResponse<T>()
    class Error<T: Any>(val code: Int, val message: String?) : ApiResponse<T>()
    class Exception<T: Any>(val e: Throwable) : ApiResponse<T>()
}