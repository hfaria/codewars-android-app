package com.hfaria.portfolio.codewars.integration

import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import org.junit.Test
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class CodeWarsApiIntegrationTest {

    val client = Retrofit.Builder()
        .baseUrl("https://www.codewars.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = client.create(CodeWarsApi::class.java)

    @Test
    fun test_get_user() {
        val call = api.getUsers("g964")
        //val response = call.execute()
        //println(response.body().toString())
    }
}