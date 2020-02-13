# Android_ECharts
[![](https://jitpack.io/v/amorYin/Android_ECharts.svg)](https://jitpack.io/#amorYin/Android_ECharts)
<br>
Baidu ECharts in Android
####
使用指南
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
