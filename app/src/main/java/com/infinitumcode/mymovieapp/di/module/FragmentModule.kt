package com.infinitumcode.mymovieapp.di.module

import com.infinitumcode.mymovieapp.view.ui.child.ChildFragment
import com.infinitumcode.mymovieapp.view.ui.detail.DetailFragment
import com.infinitumcode.mymovieapp.view.ui.favorite.FavoriteFragment
import com.infinitumcode.mymovieapp.view.ui.find.FindFragment
import com.infinitumcode.mymovieapp.view.ui.home.HomeFragment
import com.infinitumcode.mymovieapp.view.ui.popular.PopularFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    fun contributeFindFragment(): FindFragment

    @ContributesAndroidInjector
    fun contributePopularFragment(): PopularFragment

    @ContributesAndroidInjector
    fun contributeChildFragment(): ChildFragment

    @ContributesAndroidInjector
    fun contributeDetailFragment(): DetailFragment

}