/**
 * Created by amoryin on 2020/2/8.
 */

package com.yzproj.echarts.client

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.github.abel533.echarts.json.GsonOption
import com.yzproj.echarts.face.EChartsDataSource
import com.yzproj.echarts.face.EChartsEventAction
import com.yzproj.echarts.face.EChartsEventHandler
import com.yzproj.echarts.face.EChartsWebClient

class EChartWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : WebView(context, attrs, defStyleAttr) {
    init {
        init();
    }

    @SuppressLint("JavascriptInterface")
    private fun init(){
        setLayerType(View.LAYER_TYPE_SOFTWARE,null)

        val webSettings = settings;
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportZoom(true)
        webSettings.displayZoomControls = true

        addJavascriptInterface(EChartInterface(context),"Android")
    }

    fun setType(type: Int) {
        var index = type
        if(type < 0 || type > 2) {
            loadContent("file:///android_asset/echart/biz/echart.html")
        }else{
            loadContent("file:///android_asset/echart/biz/echart-$index.html")
        }
    }
    /**
     *
     */
    private var chartLoaded:Boolean = false
    /**
     * dataSource
     */
    private var dataSource: EChartsDataSource? = null

    fun setDataSource(data:EChartsDataSource){
        dataSource = data
        reload()
    }

    /**
     * delegate
     */
    private var delegate: EChartsEventHandler? = null

    fun setDelegate(data: EChartsEventHandler){
        delegate = data
    }

    /**
     * refresh
     */
    fun refreshEchartsWithOption(option: GsonOption){
        val optionString = option.toString()
        val call = "javascript:refreshEchartsWithOption('$optionString')"
        loadUrl(call)
    }

    /**
     * interface
     */
    internal inner class EChartInterface(var context: Context){
        val chartOptions: String?
            @JavascriptInterface
            get() {
                if (dataSource!=null){
                    val option = dataSource!!.echartOptions()
                    if (option == null) return  dataSource!!.echartOptionsString()
                    return option.toString()
                }
                return null
            }

        @JavascriptInterface
        fun showMessage(msg:String?):Boolean{
            if (msg == null || msg.isEmpty()) return false
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
            return true
        }

        @JavascriptInterface
        fun chartViewLoading(){
            chartLoaded = false;
        }

        @JavascriptInterface
        fun chartViewLoaded(){
            chartLoaded = true;
        }

        @JavascriptInterface
        fun removeEChartActionEventResponse(param: String){

        }

        @JavascriptInterface
        fun onEChartActionEventResponse(action: String,data:String?){
            if (delegate != null){
                delegate!!.onHandlerResponseAction(EChartsEventAction.convert(action),data)
            }
        }
    }

    /**
     * webClient
     */

    private var client: EChartsWebClient? = null

    fun setClient(data:EChartsWebClient?){
        client = data
    }

    /**
     * reset
     */
    fun reloadActions(){
        if (dataSource!=null){
            var removeOptions = dataSource!!.removeEChartActionEvents()
            if (!removeOptions.isNullOrEmpty()){
                for (e:EChartsEventAction in removeOptions.iterator()){
                    val action = e.ation
                    val call = "javascript:removeEchartActionHandler('" + action + "')"
                    loadUrl(call)
                }
            }

            var addOptions = dataSource!!.addEChartActionEvents()
            if (!addOptions.isNullOrEmpty()){
                for (e:EChartsEventAction in addOptions.iterator()){
                    val action = e.ation
                    val call = "javascript:addEchartActionHandler('" + action + "')"
                    loadUrl(call)
                }
            }
        }
    }

    fun showLoading(){
        val call = "javascript:showChartLoading()"
        loadUrl(call)
    }

    fun hideLoading(){
        val call = "javascript:hideChartLoading()"
        loadUrl(call)
    }

    /**
     * load
     */
    fun loadContent(url:String?){
        loadUrl(url)
        // 设置WebViewClient
        this.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(url)
                client?.shouldOverrideUrlLoading(view,request)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                client?.onPageStarted(view,url,favicon)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                client?.onPageFinished(view,url)
                if(chartLoaded)reloadActions()
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                client?.onLoadResource(view,url)
                super.onLoadResource(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
//				view.loadData(errorHtml, "text/html; charset=UTF-8", null);
                hideLoading()
                client?.onReceivedError(view,request,error)
                super.onReceivedError(view, request, error)
            }
        })

        // 设置WebChromeClient
        this.setWebChromeClient(object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                client?.onJsAlert(view,url,message,result)
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                client?.onJsConfirm(view,url,message,result)
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                client?.onJsPrompt(view,url,message,defaultValue,result)
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                client?.onProgressChanged(view,newProgress)
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                client?.onReceivedTitle(view,title)
                super.onReceivedTitle(view, title)
            }
        })
    }
}