package clean.news.app.util

import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Subscription.addTo(compositeSubscription: CompositeSubscription) = apply { compositeSubscription.add(this) }
