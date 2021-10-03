/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hfaria.portfolio.codewars.di

import android.content.Context
import com.example.android.architecture.blueprints.todoapp.taskdetail.di.SearchUserComponent
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelBuilderModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    //fun addEditTaskComponent(): AddEditTaskComponent.Factory
    //fun statisticsComponent(): StatisticsComponent.Factory
    //fun taskDetailComponent(): TaskDetailComponent.Factory
    fun searchUserComponent(): SearchUserComponent.Factory

    //val tasksRepository: TasksRepository
}
