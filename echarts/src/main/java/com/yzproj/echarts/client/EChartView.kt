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
import com.github.abel533.echarts.json.GsonOption
import com.yzproj.echarts.R
import com.yzproj.echarts.face.EChartsDataSource
import com.yzproj.echarts.face.EChartsEventAction
import com.yzproj.echarts.face.EChartsEventHandler
import com.yzproj.echarts.face.EChartsWebClient

class EChartView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(mContext, attrs, defStyle), EChartsWebClient,EChartsDataSource,EChartsEventHandler{

    internal var mWebView: EChartWebView? = null
    internal var mProgressBar: ProgressBar? = null
    internal var client: EChartsWebClient? = null
    private var delegate: EChartsEventHandler? = null
    private var dataSource: EChartsDataSource? = null

    internal var url: String? = null
    private var showProgressBar:Boolean = true

    init {
        initView(mContext)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.chart_view, this)
        mWebView = view.findViewById(R.id.web_view) as EChartWebView
        mProgressBar = view.findViewById(R.id.progress_bar) as ProgressBar
        mWebView!!.setClient(this)
        mWebView!!.setDelegate(this)
        mWebView!!.setDataSource(this)
        setType(-1);
    }

    //测试使用
    private fun setType(type: Int) {
        mWebView!!.setType(type)
    }
    fun isShowProgressBar(show:Boolean){
        showProgressBar = show
    }
    //  webview事件代理
    fun setClient(data: EChartsWebClient?){
        client = data
    }
    //数据源代理
    fun setDataSource(data: EChartsDataSource) {
//        mWebView!!.setDataSource(data)
        dataSource = data
    }
    // 自定义事件代理
    fun setDelegate(data: EChartsEventHandler) {
//        mWebView!!.setDelegate(data)
        delegate = data
    }

    fun loadUrl(url: String?) {
        if (url == null) return
        initWebview(url)
    }

    private fun initWebview(url: String) {

        mWebView!!.loadUrl(url)

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

    override fun shouldOverrideUrlLoading(view: View?,webView: WebView?, request: WebResourceRequest?) {
        client?.shouldOverrideUrlLoading(view,webView,request)
    }

    override fun onPageStarted(view: View?,webView: WebView?, url: String?, favicon: Bitmap?){
        if(showProgressBar)mProgressBar!!.visibility = View.VISIBLE
        client?.onPageStarted(view,webView,url,favicon)
    }

    override fun onPageFinished(view: View?,webView: WebView?, url: String?){
        if(showProgressBar)mProgressBar!!.visibility = View.GONE
        client?.onPageFinished(view,webView,url)
    }

    override fun onLoadResource(view: View?,webView: WebView?, url: String?){
        client?.onLoadResource(this,webView,url)
    }

    override fun onReceivedError(view: View?,webView: WebView?, request: WebResourceRequest?, error: WebResourceError?){
        client?.onReceivedError(this,webView,request,error)
    }

    override fun onJsAlert(view: View?,webView: WebView?, url: String?, message: String?, result: JsResult?){
        client?.onJsAlert(this,webView,url,message,result)
    }

    override fun onJsConfirm(view: View?,webView: WebView?, url: String?, message: String?, result: JsResult?){
        client?.onJsConfirm(this,webView,url,message,result)
    }

    override fun onJsPrompt(view: View?,webView: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?){
        client?.onJsPrompt(this,webView,url,message,defaultValue,result)
    }

    override fun onProgressChanged(view: View?,webView: WebView?, newProgress: Int){
        if(showProgressBar)mProgressBar!!.progress = newProgress
        client?.onProgressChanged(this,webView,newProgress)
    }

    override fun onReceivedTitle(view: View?,webView: WebView?, title: String?){
        client?.onReceivedTitle(this,webView,title)
    }

    override fun onHandlerResponseAction(view: View?, action: EChartsEventAction, data: String?) {
        delegate?.onHandlerResponseAction(this,action,data)
    }

    override fun onHandlerResponseRemoveAction(view: View?, action: EChartsEventAction) {
        delegate?.onHandlerResponseRemoveAction(this,action)
    }

    override fun echartOptions(view: View?): GsonOption? {
        return dataSource?.echartOptions(this)
    }
    override fun echartOptionsString(view: View?): String{
        val result: String? = dataSource?.echartOptionsString(this)
        if (result == null) return GsonOption().toString()
        return  result
    }
    override fun removeEChartActionEvents(view: View?):Array<EChartsEventAction>{
        val result: Array<EChartsEventAction>? = dataSource?.removeEChartActionEvents(this)
        if (result ==  null) return arrayOf()
        return result
    }
    override fun addEChartActionEvents(view: View?):Array<EChartsEventAction>{
        val result: Array<EChartsEventAction>? = dataSource?.addEChartActionEvents(this)
        if (result ==  null) return arrayOf()
        return result
    }

    fun canBack(): Boolean {
        if (mWebView!!.canGoBack()) {
            mWebView!!.goBack()
            return false
        }
        return true
    }
}