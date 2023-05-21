package com.example.templatesampleapp.repo.service

/**
 * @author Umer Bilal
 * Created 7/23/2022 at 2:56 PM
 */

sealed class Either<T> {
    data class Success<T>(val data: T) : Either<T>()
    data class Error<T>(val message: String) : Either<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
        fun <T> httpError(error: HttpErrors) = error
    }

    inline fun onSuccess(block: (T) -> Unit): Either<T> = apply {
        if (this is Success) {
            block(data)
        }
    }

    inline fun onFailure(block: (String) -> Unit): Either<T> = apply {
        if (this is Error) {
            block(message)
        }
    }

    sealed class HttpErrors {
        data class ResourceForbidden(val exception: String) : HttpErrors()
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class ResourceRemoved(val exception: String) : HttpErrors()
        data class RemovedResourceFound(val exception: String) : HttpErrors()
    }
}

//class BaseClientBuilder @Inject constructor(private val context: Context) {
//
//    suspend fun <T : Any> safeApiCall(
//        responseBack: suspend () -> Response<T>
//    ): Result<T> {
//        val callToService = responseBack.invoke()
//        return if (callToService.isSuccessful) {
//            Result.success(callToService.body()!!)
//        } else {
//            Result.failure(Exception("Error While Getting Data From Server"))
//        }
//    }
//}