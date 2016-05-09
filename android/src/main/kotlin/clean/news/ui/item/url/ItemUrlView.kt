package clean.news.ui.item.url

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.app.util.addTo
import clean.news.core.entity.Item
import clean.news.flow.service.DaggerService
import clean.news.presentation.model.item.ItemUrlViewModel
import clean.news.presentation.model.item.ItemUrlViewModel.Sources
import clean.news.ui.item.url.ItemUrlKey.ItemUrlComponent
import com.jakewharton.rxbinding.support.v7.widget.itemClicks
import com.jakewharton.rxbinding.support.v7.widget.navigationClicks
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemUrlView : RelativeLayout {
	@Inject
	lateinit var model: ItemUrlViewModel

	private val toolbar: Toolbar by bindView(R.id.toolbar)
	private val titleTextView: TextView by bindView(R.id.title_text_view)
	private val progressBar: ProgressBar by bindView(R.id.progress_bar)
	private val webView: WebView by bindView(R.id.web_view)

	private val subscriptions = CompositeSubscription()

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		DaggerService.get<ItemUrlComponent>(context)?.inject(this)
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
		toolbar.inflateMenu(R.menu.item_url_view)

		webView.setWebChromeClient(LoadingWebChromeClient(progressBar))
		webView.setWebViewClient(WebViewClient()) // handle redirects in-app
		webView.settings.apply {
			javaScriptEnabled = true
		}
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()

		val toolbarItemClicks = toolbar.itemClicks()
				.publish()
				.autoConnect()

		val sinks = model.setUp(Sources(
				toolbar.navigationClicks(),
				toolbarItemClicks.filter { it.itemId == R.id.item_details },
				toolbarItemClicks.filter { it.itemId == R.id.item_share }
		))

		sinks.item.subscribe(itemSubscriber).addTo(subscriptions)
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		model.tearDown()
		super.onDetachedFromWindow()
	}

	private val itemSubscriber = { item: Item ->
		toolbar.menu.findItem(R.id.item_details).isVisible = item.type.canComment
		titleTextView.text = item.title

		if (item.url != webView.url) {
			webView.loadUrl(item.url)
		}
	}

	private class LoadingWebChromeClient(private val progressBar: ProgressBar) : WebChromeClient() {
		override fun onProgressChanged(view: WebView?, newProgress: Int) {
			super.onProgressChanged(view, newProgress)
			progressBar.progress = newProgress
			progressBar.visibility = if (newProgress < 100) View.VISIBLE else View.GONE
		}
	}
}
