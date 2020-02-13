# Android_ECharts
[![](https://jitpack.io/v/amorYin/Android_ECharts.svg)](https://jitpack.io/#amorYin/Android_ECharts)
<br>
Baidu ECharts in Android

## 集成指南
第一步. 在您的主项目`build.gradle`添加以下内容:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}Copy

第二步. 在 `dependency` 中添加以下依赖

	dependencies {
		implementation 'com.github.abel533:ECharts:3.0.06'
	        implementation 'com.github.amorYin:Android_ECharts:v0.0.3'
	}
## EChartView使用指南
在 使用的类的 `layout` 文件中引入
```
    <com.yzproj.echarts.client.EChartView
       	android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="56dp"/>
```
类中初始化chartView，必须实现`EChartsDataSource` 接口,可实现`EChartsEventHandler`、`EChartsWebClient`两个接口
```
	chartView = findViewById(R.id.chartView);
        chartView.setDataSource(this);//EChartsDataSource 必须实现
        chartView.setDelegate(this);//EChartsWebClient 页面加载回调 
```
---
### `EChartsDataSource`接口有四个方法
```
    fun echartOptions(): GsonOption? {return null} //返回一个[GsonOption](https://github.com/amorYin/ECharts)，优先使用，和第二个方法必须实现一个
    fun echartOptionsString(): String{ return GsonOption().toString() } //返回json字符串
    fun removeEChartActionEvents():Array<EChartsEventAction>{return arrayOf()} //删除事件集合
    fun addEChartActionEvents():Array<EChartsEventAction>{return arrayOf()} //添加事件集合
```
---
### `EChartsEventHandler`接口有四个方法
```
    fun onHandlerResponseAction(action:EChartsEventAction,data:String?)//如果实现了dataSource的添加事件方法，事件触发回调
```
### `EChartsEventAction`事件枚举,目前支持下列事件
```
    None                ("undefine"),//未定义
    Resize              ("resize"),//调整
    Click               ("click"),
    DoubleClick         ("dblclick"),
    DataChanged         ("datachanged"),
    DataZoom            ("datazoom"),
    DataRangeSelected   ("datarangeselected"),
    LegendSelected      ("legendselected"),
    LegendSelectChanged ("legendselectchanged"),
    MapSelected         ("mapselected"),
    PieSelected         ("pieselected"),
    MagicTypeChange     ("magictypechanged"),
    DataViewChanged     ("dataViewchanged"),
    GeoSelectChanged    ("geoselectchanged"),
    TimelineChanged     ("timelinechanged");
```
### `EChartsWebClient`是页面加载的web接口，如果想要控制可实现
```
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
```
## 写在最后
因为
    fun onPageFinished(view: WebView?, url: String?){}是
    fun onPageFinished(view: WebView?, url: String?){}
