package uz.turgunboyevjurabek.weatherapp.model.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.turgunboyevjurabek.weatherapp.model.network.ApiService
import uz.turgunboyevjurabek.weatherapp.utils.ConsUtils.BASE_URL
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideBaseUrl():String=BASE_URL

    @Singleton
    @Provides
    fun providesRetrofit(string: String):Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(string)
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

}