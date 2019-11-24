package com.g2pdev.simpletranslator.interactor.misc

import android.content.ClipData
import android.content.ClipboardManager
import io.reactivex.Completable

interface CopyTextToClipboard {
    fun exec(text: String): Completable
}

class CopyTextToClipboardImpl(
    private val clipboardManager: ClipboardManager
) : CopyTextToClipboard {

    override fun exec(text: String): Completable {
        return Completable.fromCallable {
            val clipData = ClipData.newPlainText("text", text)

            clipboardManager.setPrimaryClip(clipData)
        }
    }
}