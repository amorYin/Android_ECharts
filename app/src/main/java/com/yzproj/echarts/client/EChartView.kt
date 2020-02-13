/**
 * Created by amoryin on 2020/2/8.
 */

package com.yzproj.echarts.client

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.yzproj.echarts.R
import com.yzproj.echarts.face.EChartsDataSource
import com.yzproj.echarts.face.EChartsEventHandler
import com.yzproj.echarts.face.EChartsWebClient

class EChartView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(mContext, attrs, defStyle), EChartsWebClient{

    internal var mWebView: EChartWebView? = null
    internal var mProgressBar: ProgressBar? = null
    internal var client: EChartsWebClient? = null

    fun setClient(data: EChartsWebClient?){
        client = data
    }

    var url: String? = null

    init {
        initView(mContext)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.chart_view, this)
        mWebView = view.findViewById(R.id.web_view) as EChartWebView
        mProgressBar = view.findViewById(R.id.progress_bar) as ProgressBar
        mWebView!!.setClient(this)
    }


    fun setType(type: Int) {
        mWebView!!.setType(type)
    }

    fun setDataSource(data: EChartsDataSource) {
        mWebView!!.setDataSource(data)
    }

    fun setDelegate(data: EChartsEventHandler) {
        mWebView!!.setDelegate(data)
    }

    fun loadUrl(url: String?) {
        if (url == null) return
        initWebview(url)
    }

    private fun initWebview(url: String) {

        mWebView!!.loadContent(url)

        mWebView!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 表示按返回键
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView!!.canGoBack()) {
                        mWebView!!.goBack() // 后退
                        return true // 已处理
                    }
                }
                return false
            }
        })
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) {
        client?.shouldOverrideUrlLoading(view,request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?){
        mProgressBar!!.visibility = View.VISIBLE
        client?.onPageStarted(view,url,favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?){
        mProgressBar!!.visibility = View.GONE
        client?.onPageFinished(view,url)
    }

    override fun onLoadResource(view: WebView?, url: String?){
        client?.onLoadResource(view,url)
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?){
        client?.onReceivedError(view,request,error)
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?){
        client?.onJsAlert(view,url,message,result)
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?){
        client?.onJsConfirm(view,url,message,result)
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?){
        client?.onJsPrompt(view,url,message,defaultValue,result)
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int){
        mProgressBar!!.progress = newProgress
        client?.onProgressChanged(view,newProgress)
    }

    override fun onReceivedTitle(view: WebView?, title: String?){
        client?.onReceivedTitle(view,title)
    }

    fun canBack(): Boolean {
        if (mWebView!!.canGoBack()) {
            mWebView!!.goBack()
            return false
        }
        return true
    }
}