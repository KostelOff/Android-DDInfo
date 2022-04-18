package com.ntechnology.ddinfo.di.modules

import com.ntechnology.ddinfo.ui.activities.LauncherActivity
import com.ntechnology.ddinfo.ui.activities.gender.GenderGradeActivity
import com.ntechnology.ddinfo.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {
    @ContributesAndroidInjector
    abstract fun contributeLauncherActivity(): LauncherActivity

    @ContributesAndroidInjector
    abstract fun contributeMainMenuFragment(): MainMenuFragment

    @ContributesAndroidInjector
    abstract fun contributePaymentMenuFragment(): PaymentMenuFragment

    @ContributesAndroidInjector
    abstract fun contributePhysicalMenuFragment(): PhysicalMenuFragment

    @ContributesAndroidInjector
    abstract fun contributeCalculatorFragment(): CalculatorFragment

    @ContributesAndroidInjector
    abstract fun contributeGradesFragment(): GradesFragment

    @ContributesAndroidInjector
    abstract fun contributeGenderGradesFragment(): GenderGradeActivity

    @ContributesAndroidInjector
    abstract fun contributeSelectionFragment(): SelectionFragment

    @ContributesAndroidInjector
    abstract fun contributeGradeSelectionFragment(): GradeSelectionFragment

//    @ContributesAndroidInjector
//    abstract fun contributeGenderGradeSelectionFragment(): GenderGradeSelectionFragment

    @ContributesAndroidInjector
    abstract fun contributeInputFragment(): InputFragment
}