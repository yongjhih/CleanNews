package clean.news.presentation.inject

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

@Scope
@Retention(RUNTIME)
annotation class ScreenScope(val value: KClass<*>)
