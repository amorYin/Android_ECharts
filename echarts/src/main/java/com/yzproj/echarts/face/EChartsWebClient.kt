/**
 * Created by amoryin on 2020/2/8.
 */

package com.yzproj.echarts.face

import android.graphics.Bitmap
import android.webkit.*

interface EChartsWebClient {
    fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?){}

    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?){}

    fun onPageFinished(view: WebView?, url: String?){}

    fun onLoadResource(view: WebView?, url: String?){}

    fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?){}

    fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?){}

    fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?){}

    fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?){}

    fun onProgressChanged(view: WebView?, newProgress: Int){}

    fun onReceivedTitle(view: WebView?, title: String?){}
}