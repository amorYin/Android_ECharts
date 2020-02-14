/**
 * Created by amoryin on 2020/2/8.
 */

package com.yzproj.echarts.face

import android.graphics.Bitmap
import android.view.View
import android.webkit.*

interface EChartsWebClient {
    fun shouldOverrideUrlLoading(view: View?, webView: WebView?, request: WebResourceRequest?){}

    fun onPageStarted(view: View?, webView: WebView?, url: String?, favicon: Bitmap?){}

    fun onPageFinished(view: View?, webView: WebView?, url: String?){}

    fun onLoadResource(view: View?, webView: WebView?, url: String?){}

    fun onReceivedError(view: View?, webView: WebView?, request: WebResourceRequest?, error: WebResourceError?){}

    fun onJsAlert(view: View?, webView: WebView?, url: String?, message: String?, result: JsResult?){}

    fun onJsConfirm(view: View?, webView: WebView?, url: String?, message: String?, result: JsResult?){}

    fun onJsPrompt(view: View?, webView: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?){}

    fun onProgressChanged(view: View?, webView: WebView?, newProgress: Int){}

    fun onReceivedTitle(view: View?, webView: WebView?, title: String?){}
}