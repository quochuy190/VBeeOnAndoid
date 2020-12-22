package vn.neo.smsvietlott.common.utils

import android.app.Application
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.CompositeDisposable


object AppInitializeManager {
    private const val VERSION_SCHEMA = 1L
    private const val KEY_CLEAR = "KEY_OLD_VERSION"

    var initialized = false

    private val disposableBag: CompositeDisposable = CompositeDisposable()

 /*   fun init(app: Application, callback: (() -> Unit)? = null) {
        if (initialized) {
            callback?.invoke()
            return
        }
        Observable.mergeArray(
            Observable.create<Void> { e -> AppInit(app).run(e) },
            Observable.create<Void> { e -> NotificationInit(app).run(e) }
        )
            .subscribe({
            }, {
                // on error
                initialized = false
                //postEvent(CompletedAppInitializeEvent(false))
            }, {
                // on completed
                initialized = true
                // postEvent(CompletedAppInitializeEvent(true))
                //Timber.e(AppUtils.apiBaseUrl())
                callback?.invoke()
            }).addToCompositedisposable(disposableBag)
    }*/

    interface Task {
        fun run(emitter: ObservableEmitter<Void>)
    }

    class AppInit(val app: Application) : Task {
        override fun run(emitter: ObservableEmitter<Void>) {
            AppUtils.setup(app)
            emitter.onComplete()
        }
    }

    class NotificationInit(val app: Application) : Task {
        override fun run(emitter: ObservableEmitter<Void>) {
            //NotificationUtils.createNotificationChannels(app)
            emitter.onComplete()
        }
    }
}