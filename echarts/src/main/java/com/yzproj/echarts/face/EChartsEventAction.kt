/**
 * Created by amoryin on 2020/2/11.
 * 详情参考，百度ECharts API库https://www.echartsjs.com/zh/api.html#events
 */

package com.yzproj.echarts.face

enum class EChartsEventAction(action:String) {
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

    var ation:String? = null

    init {
        ation = action
    }

    companion object {
        internal fun convert(action: String):EChartsEventAction{
            if (action == EChartsEventAction.Resize.ation)                  return EChartsEventAction.Resize
            else if (action == EChartsEventAction.Click.ation)              return EChartsEventAction.Click
            else if (action == EChartsEventAction.DoubleClick.ation)        return EChartsEventAction.DoubleClick
            else if (action == EChartsEventAction.DataChanged.ation)        return EChartsEventAction.DataChanged
            else if (action == EChartsEventAction.DataZoom.ation)           return EChartsEventAction.DataZoom
            else if (action == EChartsEventAction.DataRangeSelected.ation)  return EChartsEventAction.DataRangeSelected
            else if (action == EChartsEventAction.LegendSelected.ation)     return EChartsEventAction.LegendSelected
            else if (action == EChartsEventAction.LegendSelectChanged.ation)return EChartsEventAction.LegendSelectChanged
            else if (action == EChartsEventAction.MapSelected.ation)        return EChartsEventAction.MapSelected
            else if (action == EChartsEventAction.PieSelected.ation)        return EChartsEventAction.PieSelected
            else if (action == EChartsEventAction.MagicTypeChange.ation)    return EChartsEventAction.MagicTypeChange
            else if (action == EChartsEventAction.DataViewChanged.ation)    return EChartsEventAction.DataViewChanged
            else if (action == EChartsEventAction.GeoSelectChanged.ation)   return EChartsEventAction.GeoSelectChanged
            else if (action == EChartsEventAction.TimelineChanged.ation)    return EChartsEventAction.TimelineChanged
            else return EChartsEventAction.None
        }
    }
}