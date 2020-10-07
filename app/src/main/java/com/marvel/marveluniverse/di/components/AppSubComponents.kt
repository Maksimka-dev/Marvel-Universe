package com.marvel.marveluniverse.di.components

import dagger.Module

@Module(subcomponents = [UserComponent::class, AuthorizationComponent::class])
class AppSubComponents
