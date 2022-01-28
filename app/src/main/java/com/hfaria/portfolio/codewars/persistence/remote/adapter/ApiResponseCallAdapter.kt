package com.hfaria.portfolio.codewars.persistence.remote.adapter

import com.hfaria.portfolio.codewars.persistence.remote.*
import retrofit2.*
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into an ApiResponse object
 */
class ApiResponseCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, ApiResponse<R>> {

    private fun <R> create(error: Throwable): ApiErrorResponse<R> {
        return ApiErrorResponse(error.message ?: "UNKNOWN_EXCEPTION")
    }

    private fun <R> create(response: Response<R>): ApiResponse<R> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null || response.code() == 204) {
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(body)
            }
        } else {
            return if (response.code() == 404) {
                ApiNotFoundResponse()
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "UNKNOWN_ERROR")
            }
        }
    }

    private fun executeCall(call: Call<R>): ApiResponse<R> {
        return try {
            val retrofitResponse = call.execute()
            create<R>(retrofitResponse)
        } catch (throwable: Throwable) {
            create<R>(throwable)
        }
    }

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): ApiResponse<R>
            = executeCall(call)
}
