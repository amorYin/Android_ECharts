/**
 * Created by amoryin on 2020/2/8.
 */
package com.yzproj.echarts.face

import android.view.View
import com.github.abel533.echarts.json.GsonOption

/**
 * dataSource
 */
interface EChartsDataSource {
    fun echartOptions(view: View?): GsonOption? {return null} //优先使用
    fun echartOptionsString(view: View?): String{ return GsonOption().toString() } //其次返回json使用
    fun removeEChartActionEvents(view: View?):Array<EChartsEventAction>{return arrayOf()}
    fun addEChartActionEvents(view: View?):Array<EChartsEventAction>{return arrayOf()}
}