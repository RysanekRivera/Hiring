package io.github.rysanekrivera.domain.repositories

import io.github.rysanekrivera.data.apis.HiringApi
import io.github.rysanekrivera.data.models.EmployeeDto
import retrofit2.Response
import javax.inject.Inject

class HiringRepositoryImpl @Inject constructor(
    private val hiringApi: HiringApi
): HiringRepository {

    override suspend fun getAllEmployees(): Response<List<EmployeeDto>> = hiringApi.getAllEmployees()

}