package com.erkindilekci.koincryptoapp.di

import com.erkindilekci.koincryptoapp.domain.repository.CryptoRepository
import com.erkindilekci.koincryptoapp.data.repository.CryptoRepositoryImpl
import com.erkindilekci.koincryptoapp.data.local.CryptoApi
import com.erkindilekci.koincryptoapp.util.Constants.BASE_URL
import com.erkindilekci.koincryptoapp.presentation.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }

    single<CryptoRepository> {
        CryptoRepositoryImpl(get())
    }

    viewModel {
        ListViewModel(get())
    }
}
