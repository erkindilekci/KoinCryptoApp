package com.erkindilekci.koincryptoapp.domain.repository

import com.erkindilekci.koincryptoapp.data.local.dto.CryptoDto
import com.erkindilekci.koincryptoapp.util.Resource

interface CryptoRepository {

    suspend fun getCryptos(): Resource<List<CryptoDto>>
}