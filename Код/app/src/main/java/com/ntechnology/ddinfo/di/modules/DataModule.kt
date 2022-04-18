package com.ntechnology.ddinfo.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.ntechnology.ddinfo.extensions.getDefaultSharedPreferences
import com.ntechnology.ddinfo.utils.*
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DataModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideCalculatorItemsProvider(assetManager: AssetManager, moshi: Moshi): CalculatorItemsProvider {
        return CalculatorItemsProvider(assetManager, moshi)
    }

    @Provides
    @Singleton
    fun provideGradeItemsProvider(assetManager: AssetManager, moshi: Moshi): GradeItemsProvider {
        return GradeItemsProvider(assetManager, moshi)
    }

    @Provides
    @Singleton
    fun provideGenderGradeItemsProvider(assetManager: AssetManager, moshi: Moshi): GenderGradeItemsProvider {
        return GenderGradeItemsProvider(assetManager, moshi)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getDefaultSharedPreferences()

    @Provides
    @Singleton
    fun provideCalculatorStorage(sharedPreferences: SharedPreferences, moshi: Moshi): CalculatorStorage {
        return CalculatorStorage(sharedPreferences, moshi)
    }

    @Provides
    @Singleton
    fun provideGradeStorage(sharedPreferences: SharedPreferences, moshi: Moshi): GradeStorage {
        return GradeStorage(sharedPreferences, moshi)
    }

    @Provides
    @Singleton
    fun provideCalculationProvider(): CalculationProvider = CalculationProvider()

    @Provides
    @Singleton
    fun provideGradesProvider(): GradesProvider = GradesProvider()
}