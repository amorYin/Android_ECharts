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
类中初始化chartView，实现`EChartsDataSource` ,`EChartsEventHandler`两个接口
```
	chartView = findViewById(R.id.chartView);
        chartView.setDataSource(this);//EChartsDataSource 必须实现
        chartView.setDelegate(this);//EChartsEventHandler 事件管理器，如果需要对事件进行处理需实现
```
---
### `EChartsDataSource`接口有四个方法
```
    fun echartOptions(): GsonOption? {return null} //返回一个[GsonOption](https://github.com/amorYin/ECharts)，优先使用，和第二个方法必须实现一个
    fun echartOptionsString(): String{ return GsonOption().toString() } //返回json字符串
    fun removeEChartActionEvents():Array<EChartsEventAction>{return arrayOf()} 
    fun addEChartActionEvents():Array<EChartsEventAction>{return arrayOf()}
```
---
### `EChartsEventHandler`接口有四个方法
```
    fun onHandlerResponseAction(action:EChartsEventAction,data:String?)//事件返回
```

