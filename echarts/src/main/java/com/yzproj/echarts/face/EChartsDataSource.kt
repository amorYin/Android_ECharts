/**
 * Created by amoryin on 2020/2/8.
 */
package com.yzproj.echarts.face

import com.github.abel533.echarts.json.GsonOption

/**
 * dataSource
 */
interface EChartsDataSource {
    fun echartOptions(): GsonOption? {return null} //优先使用
    fun echartOptionsString(): String{ return GsonOption().toString() } //其次返回json使用
    fun removeEChartActionEvents():Array<EChartsEventAction>{return arrayOf()}
    fun addEChartActionEvents():Array<EChartsEventAction>{return arrayOf()}
}