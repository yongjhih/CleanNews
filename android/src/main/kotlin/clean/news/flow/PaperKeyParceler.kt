package clean.news.flow

import android.os.Parcelable
import flow.KeyParceler
import nz.bradcampbell.paperparcel.PaperParcelable

class PaperKeyParceler : KeyParceler {
	override fun toParcelable(key: Any): Parcelable {
		if (key !is PaperParcelable) {
			throw IllegalArgumentException("Key must be annotated with @PaperParcel and implement PaperParcelable.")
		}
		return key
	}

	override fun toKey(parcelable: Parcelable): Any {
		return parcelable
	}
}
