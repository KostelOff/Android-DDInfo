package com.ntechnology.ddinfo.di.components

import android.app.Application
import com.ntechnology.ddinfo.MainApplication
import com.ntechnology.ddinfo.di.modules.BindingModule
import com.ntechnology.ddinfo.di.modules.ContextModule
import com.ntechnology.ddinfo.di.modules.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ContextModule::class, BindingModule::class, DataModule::class])
interface AppComponent : AndroidInjector<MainApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}