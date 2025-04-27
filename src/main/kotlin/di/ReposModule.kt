package com.example.di

import com.example.data.repos.BlogRepositoryImpl
import com.example.data.repos.UserRepositoryImpl
import com.example.domain.repos.UserRepository
import com.example.domain.repos.BlogRepository
import org.koin.dsl.module

val reposModule = module {

    single<UserRepository> {
        UserRepositoryImpl()
    }

    single<BlogRepository> {
        BlogRepositoryImpl()
    }

}