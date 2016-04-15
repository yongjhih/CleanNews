package clean.news.data.retrofit

import clean.news.core.entity.Item

fun Item.threadUrl(): String = "https://news.ycombinator.com/item?id=${this.id}"
