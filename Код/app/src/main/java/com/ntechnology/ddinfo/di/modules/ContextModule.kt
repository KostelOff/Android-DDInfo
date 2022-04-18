package com.ntechnology.ddinfo.di.modules

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun provideAssetManager(context: Context): AssetManager = context.assets
}