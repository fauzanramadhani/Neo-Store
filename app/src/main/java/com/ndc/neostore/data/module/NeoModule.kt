package com.ndc.neostore.data.module

import android.content.Context
import com.ndc.neostore.data.source.local.sharedpref.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NeoModule {
    @Provides
    fun provideSharedPref(
        @ApplicationContext context: Context,
    ) = SharedPreferencesManager(context)
}