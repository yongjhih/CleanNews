package clean.news

import javafx.application.Application
import javafx.stage.Stage

class App : Application() {
	override fun start(stage: Stage) {
		stage.title = "Clean News"
		stage.show();
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(App::class.java)
		}
	}
}
