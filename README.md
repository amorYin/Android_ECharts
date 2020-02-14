# Android_ECharts
#### 当前版本[![](https://jitpack.io/v/amorYin/Android_ECharts.svg)](https://jitpack.io/#amorYin/Android_ECharts)
## Baidu ECharts in Android
## Demo截图

![图标效果](https://github.com/amorYin/Android_ECharts/blob/master/images/331581665532_.pic.jpg)![地图效果](https://github.com/amorYin/Android_ECharts/blob/master/images/341581665533_.pic.jpg)

![chart点击效果](https://github.com/amorYin/Android_ECharts/blob/master/images/331581665536_.pic.jpg)![两个chart效果](https://github.com/amorYin/Android_ECharts/blob/master/images/331581665535_.pic.jpg)

## 集成指南
第一步. 在您的主项目`build.gradle`添加以下内容:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

第二步. 在 `dependency` 中添加以下依赖

	dependencies {
		implementation 'com.github.abel533:ECharts:3.0.0.6'
	        implementation 'com.github.amorYin:Android_ECharts:v0.0.3'
	}
## EChartView使用指南
在 使用的Activity的 `layout` 文件中引入
```
    <com.yzproj.echarts.client.EChartView
       	android:id="@+id/chartView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginBottom="56dp"/>
```
Activity中初始化chartView，必须实现`EChartsDataSource` 接口,可实现`EChartsEventHandler`、`EChartsWebClient`两个接口
```
	chartView = findViewById(R.id.chartView);
        chartView.setDataSource(this);//EChartsDataSource 必须实现
        chartView.setDelegate(this);//EChartsWebClient 页面加载回调 
```
Activity例子
```
***
class MainActivity : AppCompatActivity(),EChartsDataSource, EChartsEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chartView.setType(8)
        chartView.setDataSource(this)
        chartView.setDelegate(this)
    }

    override fun echartOptions(view:View?): GsonOption? {
        val option = GsonOption()
        option.legend("高度(km)与气温(°C)变化关系")
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage)
        option.calculable(true)
        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C")

        val valueAxis = ValueAxis()
        valueAxis.axisLabel().formatter("{value} °C")
        option.xAxis(valueAxis)

        val categoryAxis = CategoryAxis()
        categoryAxis.axisLine().onZero(false)
        categoryAxis.axisLabel().formatter("{value} km")
        categoryAxis.boundaryGap(false)
        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80)
        categoryAxis.clickable = true
        option.yAxis(categoryAxis)

        val line = Line()
        line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)")
        option.series(line)
        return option
    }

    override fun echartOptionsString(view:View?): String {
        return  super.echartOptionsString(view)
    }

    override fun removeEChartActionEvents(view:View?): Array<EChartsEventAction> {
        return arrayOf()
    }

    override fun addEChartActionEvents(view:View?): Array<EChartsEventAction> {
        return arrayOf(EChartsEventAction.Click,EChartsEventAction.DataRangeSelected,EChartsEventAction.LegendSelectChanged)
    }

    override fun onHandlerResponseAction(view:View?,action: EChartsEventAction,data:String?) {
        Toast.makeText(baseContext,data,Toast.LENGTH_SHORT).show()
    }
}

```
---
### `EChartsDataSource`接口有四个方法
>[GsonOption](https://github.com/amorYin/ECharts)
```
    fun echartOptions(view: View?): GsonOption? {return null} //返回一个GsonOption，优先使用，和第二个方法必须实现一个
    fun echartOptionsString(view: View?): String{ return GsonOption().toString() } //返回json字符串
    fun removeEChartActionEvents(view: View?):Array<EChartsEventAction>{return arrayOf()} //删除事件集合
    fun addEChartActionEvents(view: View?):Array<EChartsEventAction>{return arrayOf()} //添加事件集合
```
### `EChartsEventHandler`接口有四个方法
```
    //如果实现了dataSource的添加事件方法的事件触发回调
    fun onHandlerResponseAction(view: View?,action:EChartsEventAction,data:String?)
    fun onHandlerResponseRemoveAction(view: View?,action:EChartsEventAction){}
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
```
## 写在最后
因为某个项目使用，所以功能是定制化的，如发现问题或者改善的意见，欢迎留言。

># 参考
>liuzh项目[ECharts](https://github.com/abel533/ECharts)<br>
>tomas项目[EChartsAndroid](https://github.com/kontafu/EChartsAndroid)
