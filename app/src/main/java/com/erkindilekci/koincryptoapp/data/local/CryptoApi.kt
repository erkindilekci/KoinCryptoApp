package com.erkindilekci.koincryptoapp.data.local

import com.erkindilekci.koincryptoapp.data.local.dto.CryptoDto
import retrofit2.Response
import retrofit2.http.GET

interface CryptoApi {

    @GET("/erkindil/Json/main/cryptolist.json")
    suspend fun getData(): Response<List<CryptoDto>>
}