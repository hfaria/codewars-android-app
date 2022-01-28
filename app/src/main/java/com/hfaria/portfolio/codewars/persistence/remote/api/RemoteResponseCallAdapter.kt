package com.hfaria.portfolio.codewars.persistence.remote.api

import com.hfaria.portfolio.codewars.persistence.remote.*
import retrofit2.*
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into an ApiResponse object
 */
class RemoteResponseCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, RemoteResponse<R>> {

    private fun <R> create(error: Throwable): RemoteErrorResponse<R> {
        return RemoteErrorResponse(error.message ?: "UNKNOWN_EXCEPTION")
    }

    private fun <R> create(response: Response<R>): RemoteResponse<R> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null || response.code() == 204) {
                RemoteEmptyResponse()
            } else {
                RemoteSuccessResponse(body)
            }
        } else {
            return if (response.code() == 404) {
                RemoteNotFoundResponse()
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                RemoteErrorResponse(errorMsg ?: "UNKNOWN_ERROR")
            }
        }
    }

    private fun executeCall(call: Call<R>): RemoteResponse<R> {
        return try {
            val retrofitResponse = call.execute()
            create<R>(retrofitResponse)
        } catch (throwable: Throwable) {
            create<R>(throwable)
        }
    }

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): RemoteResponse<R>
            = executeCall(call)
}
