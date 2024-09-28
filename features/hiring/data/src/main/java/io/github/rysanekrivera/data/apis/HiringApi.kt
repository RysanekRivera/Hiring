package io.github.rysanekrivera.data.apis

import io.github.rysanekrivera.data.models.EmployeeDto
import retrofit2.Response
import retrofit2.http.GET

interface HiringApi {

    @GET("hiring.json")
    suspend fun getAllEmployees(): Response<List<EmployeeDto>>

}