package com.hfaria.portfolio.codewars.persistence.remote.adapter


import com.hfaria.portfolio.codewars.persistence.DataWrapper
import retrofit2.*
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into an ApiResponse object
*/
class ApiResponseCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, ApiResponse<R>> {

    private fun executeCall(call: Call<R>): ApiResponse<R> {
        return try {
            val retrofitResponse = call.execute()
            ApiResponse.create(retrofitResponse)
        } catch (throwable: Throwable) {
            ApiResponse.create(throwable)
        }
    }

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): ApiResponse<R>
        = executeCall(call)
}
