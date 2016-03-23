package clean.news.presentation.inject

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

@Scope
@Retention(RUNTIME)
annotation class ActivityScope // TODO: Non-Android name
