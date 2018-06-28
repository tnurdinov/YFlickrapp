package com.tnurdinov.yflickr.di

import android.app.Application
import com.tnurdinov.yflickr.FlickrApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class, AndroidInjectionModule::class, ActivityBuilder::class))
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: FlickrApplication): AppComponent.Builder

        fun build(): AppComponent
    }

    fun inject (app:Application)
}