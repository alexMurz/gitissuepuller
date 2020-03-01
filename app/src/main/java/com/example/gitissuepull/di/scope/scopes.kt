package com.example.gitissuepull.di.scope

import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DataScope // Data(Repo) Holders

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class InjectorScope // Deps on DataScope
