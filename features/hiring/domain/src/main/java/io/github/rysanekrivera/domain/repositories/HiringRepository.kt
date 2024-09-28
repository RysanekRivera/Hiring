package io.github.rysanekrivera.domain.repositories

import io.github.rysanekrivera.data.models.EmployeeDto
import retrofit2.Response

interface HiringRepository {
    suspend fun getAllEmployees(): Response<List<EmployeeDto>>
}