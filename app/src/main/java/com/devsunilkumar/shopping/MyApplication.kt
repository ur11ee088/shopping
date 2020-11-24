package com.devsunilkumar.shopping

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.devsunilkumar.myzomato.networkcalls.NetworkConnectionInterceptor
import com.devsunilkumar.shopping.factory.HomeViewModelFactory
import com.devsunilkumar.shopping.networkcalls.MyShopApiCallsInterface
import com.devsunilkumar.shopping.repository.HomeRepository
import com.facebook.drawee.backends.pipeline.Fresco
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MyApplication :  MultiDexApplication(), KodeinAware {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        bind() from provider { MyShopApiCallsInterface(instance()) }
        bind() from singleton { NetworkConnectionInterceptor(instance()) }


        /**
         * REPOSITORIES
         * =============================================
         * Bind all the repositories that your create here
         * */
         bind() from provider { HomeRepository(instance()) }


        /**
         * VIEWMODEL FACTORIES
         * =============================================
         * Bind all the ViewModel Factories that your create here
         * */

        bind() from provider { HomeViewModelFactory(instance()) }


    }
}