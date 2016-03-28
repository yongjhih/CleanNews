package clean.news.ui.item.url

import android.content.Context
import android.support.v7.widget.Toolbar
import android.support.v7.widget.Toolbar.OnMenuItemClickListener
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.RelativeLayout
import butterknife.bindView
import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.ComponentService
import clean.news.presentation.model.item.ItemUrlViewModel
import clean.news.ui.item.url.ItemUrlScreen.ItemUrlComponent
import javax.inject.Inject

class ItemUrlView : RelativeLayout, OnMenuItemClickListener {
	@Inject
	lateinit var model: ItemUrlViewModel

	private val toolbar: Toolbar by bindView(R.id.toolbar)

	private val progressBar: ProgressBar by bindView(R.id.progress_bar)
	private val webView: WebView by bindView(R.id.web_view)

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		ComponentService.getService<ItemUrlComponent>(context)?.inject(this)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		model.item.subscribe { item: Item ->
			toolbar.title = item.title
			webView.loadUrl(item.url)
		}
	}

	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
		toolbar.setNavigationOnClickListener { model.backClicks.onNext(null) }
		toolbar.inflateMenu(R.menu.item_url_view)
		toolbar.setOnMenuItemClickListener(this)

		webView.setWebChromeClient(LoadingWebChromeClient(progressBar))
		webView.setWebViewClient(WebViewClient()) // handle redirects in-app
		webView.settings.apply {
			javaScriptEnabled = true
		}
	}

	override fun onMenuItemClick(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.item_details -> {
				model.detailClicks.onNext(null)
				return true
			}
		}
		return false
	}

	private class LoadingWebChromeClient(private val progressBar: ProgressBar) : WebChromeClient() {
		override fun onProgressChanged(view: WebView?, newProgress: Int) {
			super.onProgressChanged(view, newProgress)
			progressBar.progress = newProgress
			progressBar.visibility = if (newProgress < 100) View.VISIBLE else View.GONE
		}
	}
}
