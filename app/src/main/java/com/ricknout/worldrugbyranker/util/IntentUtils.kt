package com.ricknout.worldrugbyranker.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object IntentUtils {

    fun launchCustomTab(context: Context, url: String) {
        val colorPrimary = context.getColorPrimary()
        CustomTabsIntent.Builder()
                .setToolbarColor(colorPrimary)
                .enableUrlBarHiding()
                .setShowTitle(true)
                .addDefaultShareMenuItem()
                .build()
                .launchUrl(context, Uri.parse(url))
    }
}
