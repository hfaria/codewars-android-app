package com.hfaria.portfolio.codewars.persistence.network.reactive


import com.hfaria.portfolio.codewars.persistence.DataWrapper
import retrofit2.*
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into a DataWrapper
*/
class DataWrapperCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, DataWrapper<R>> {

    private fun executeCall(call: Call<R>): ApiResponse<R> {
        return try {
            val retrofitResponse = call.execute()
            ApiResponse.create(retrofitResponse)
        } catch (throwable: Throwable) {
            ApiResponse.create(throwable)
        }
    }

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): DataWrapper<R> {
        val response: ApiResponse<R> = executeCall(call)
        return when(response) {
            is ApiSuccessResponse -> DataWrapper.success(response.body)
            is ApiEmptyResponse -> DataWrapper.error("Empty Response", null)
            is ApiErrorResponse -> DataWrapper.error(response.errorMessage, null)
        }
    }
}
