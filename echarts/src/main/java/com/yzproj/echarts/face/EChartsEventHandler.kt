/**
 * Created by amoryin on 2020/2/11.
 */

package com.yzproj.echarts.face

import android.view.View

/**
 * delegate
 */
interface EChartsEventHandler {
    fun onHandlerResponseAction(view: View?, action:EChartsEventAction, data:String?)
    fun onHandlerResponseRemoveAction(view: View?,action:EChartsEventAction){}
}