package com.erkindilekci.koincryptoapp.data.repository

import com.erkindilekci.koincryptoapp.data.local.dto.CryptoDto
import com.erkindilekci.koincryptoapp.data.local.CryptoApi
import com.erkindilekci.koincryptoapp.domain.repository.CryptoRepository
import com.erkindilekci.koincryptoapp.util.Resource

class CryptoRepositoryImpl(
    private val api: CryptoApi
) : CryptoRepository {

    override suspend fun getCryptos(): Resource<List<CryptoDto>> {
        return try {
            val response = api.getData()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Couldn't get data.", null)
            } else {
                Resource.error("Couldn't reached the server.", null)
            }
        } catch (e: Exception) {
            Resource.error("Unknown error occurred!", null)
        }
    }
}
