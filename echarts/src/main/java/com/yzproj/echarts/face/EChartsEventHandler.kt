/**
 * Created by amoryin on 2020/2/11.
 */

package com.yzproj.echarts.face

/**
 * delegate
 */
interface EChartsEventHandler {
    fun onHandlerResponseAction(action:EChartsEventAction,data:String?)
}