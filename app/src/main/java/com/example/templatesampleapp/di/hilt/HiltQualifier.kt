package com.example.templatesampleapp.di.hilt

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation  class HttpLoggerInterceptorBasic

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation  class HttpLoggerInterceptorHeader


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation  class HttpLoggerInterceptorBody