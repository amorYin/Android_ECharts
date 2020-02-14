package com.yzproj.echarts.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.abel533.echarts.axis.CategoryAxis
import com.github.abel533.echarts.axis.ValueAxis
import com.github.abel533.echarts.code.Magic
import com.github.abel533.echarts.code.Tool
import com.github.abel533.echarts.code.Trigger
import com.github.abel533.echarts.feature.MagicType
import com.github.abel533.echarts.json.GsonOption
import com.github.abel533.echarts.series.Line
import com.yzproj.echarts.face.EChartsDataSource
import com.yzproj.echarts.face.EChartsEventAction
import com.yzproj.echarts.face.EChartsEventHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),EChartsDataSource, EChartsEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chartView.setDataSource(this)
        chartView.setDelegate(this)
    }

    override fun echartOptions(): GsonOption? {
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
        return null
    }

    override fun echartOptionsString(): String {
        return  "{\"title\":{\"text\":\"疫情地图\",\"subtext\":\"\",\"x\":\"center\"},\"tooltip\":{\"trigger\":\"item\"},\"legend\":{\"orient\":\"vertical\",\"x\":\"left\",\"data\":[\"确诊数\"]},\"dataRange\":{\"x\":\"left\",\"y\":\"bottom\",\"splitList\":[{\"start\":1000,\"label\":\">1000人\",\"color\":\"#934A34\"},{\"start\":100,\"end\":999,\"label\":\"100-1000人\",\"color\":\"#34528D\"},{\"start\":10,\"end\":99,\"label\":\"10-99人\",\"color\":\"#5474D1\"},{\"start\":1,\"end\":9,\"label\":\"1-9人\",\"color\":\"#899EDD\"},{\"end\":0,\"color\":\"#F3F3F3\"}],\"color\":[\"#34528D\",\"#5474D1\",\"#FFFFFF\"]},\"roamController\":{\"show\":true,\"width\":40,\"height\":60,\"x\":\"right\",\"mapTypeControl\":{\"china\":true}},\"series\":[{\"name\":\"确诊数\",\"type\":\"map\",\"mapType\":\"china\",\"roam\":false,\"itemStyle\":{\"normal\":{\"label\":{\"show\":true,\"textStyle\":{\"color\":\"rgb(249, 249, 249)\"}}},\"emphasis\":{\"label\":{\"show\":true}}},\"data\":[{\"name\":\"北京\",\"value\":352},{\"name\":\"天津\",\"value\":110},{\"name\":\"上海\",\"value\":311},{\"name\":\"重庆\",\"value\":509},{\"name\":\"河北\",\"value\":251},{\"name\":\"河南\",\"value\":1135},{\"name\":\"云南\",\"value\":154},{\"name\":\"辽宁\",\"value\":116},{\"name\":\"黑龙江\",\"value\":378},{\"name\":\"湖南\",\"value\":946},{\"name\":\"安徽\",\"value\":889},{\"name\":\"山东\",\"value\":497},{\"name\":\"新疆\",\"value\":59},{\"name\":\"江苏\",\"value\":543},{\"name\":\"浙江\",\"value\":1131},{\"name\":\"江西\",\"value\":884},{\"name\":\"湖北\",\"value\":33366},{\"name\":\"广西\",\"value\":222},{\"name\":\"甘肃\",\"value\":86},{\"name\":\"山西\",\"value\":124},{\"name\":\"内蒙古\",\"value\":60},{\"name\":\"陕西\",\"value\":225},{\"name\":\"吉林\",\"value\":83},{\"name\":\"福建\",\"value\":272},{\"name\":\"贵州\",\"value\":133},{\"name\":\"广东\",\"value\":1219},{\"name\":\"青海\",\"value\":18},{\"name\":\"西藏\",\"value\":1},{\"name\":\"四川\",\"value\":436},{\"name\":\"宁夏\",\"value\":587},{\"name\":\"海南\",\"value\":157},{\"name\":\"台湾\",\"value\":18},{\"name\":\"香港\",\"value\":50},{\"name\":\"澳门\",\"value\":10}]}]}";
//        return  "{\"title\":{\"text\":\"疫情地图\",\"subtext\":\"\",\"x\":\"center\"},\"tooltip\":{\"trigger\":\"item\"},\"legend\":{\"orient\":\"vertical\",\"x\":\"left\",\"data\":[\"确诊数\"]},\"dataRange\":{\"x\":\"left\",\"y\":\"bottom\",\"splitList\":[{\"start\":1500},{\"start\":900,\"end\":1500},{\"start\":310,\"end\":1000},{\"start\":200,\"end\":300},{\"start\":10,\"end\":200,\"label\":\"10 到 200（自定义label）\"},{\"start\":5,\"end\":5,\"label\":\"5（自定义特殊颜色）\",\"color\":\"black\"},{\"end\":10}],\"color\":[\"#E0022B\",\"#E09107\",\"#A3E00B\"]},\"toolbox\":{\"show\":true,\"orient\":\"vertical\",\"x\":\"right\",\"y\":\"center\",\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"roamController\":{\"show\":true,\"width\":40,\"height\":60,\"x\":\"right\",\"mapTypeControl\":{\"china\":true}},\"series\":[{\"name\":\"确诊数\",\"type\":\"map\",\"mapType\":\"china\",\"roam\":false,\"itemStyle\":{\"normal\":{\"label\":{\"show\":true,\"textStyle\":{\"color\":\"rgb(249, 249, 249)\"}}},\"emphasis\":{\"label\":{\"show\":true}}},\"data\":[{\"name\":\"北京\",\"value\":841},{\"name\":\"天津\",\"value\":281},{\"name\":\"上海\",\"value\":987},{\"name\":\"重庆\",\"value\":1725},{\"name\":\"河北\",\"value\":0},{\"name\":\"河南\",\"value\":196},{\"name\":\"云南\",\"value\":5},{\"name\":\"辽宁\",\"value\":305},{\"name\":\"黑龙江\",\"value\":108},{\"name\":\"湖南\",\"value\":200},{\"name\":\"安徽\",\"value\":1542},{\"name\":\"山东\",\"value\":1292},{\"name\":\"新疆\",\"value\":1004},{\"name\":\"江苏\",\"value\":785},{\"name\":\"浙江\",\"value\":355},{\"name\":\"江西\",\"value\":1490},{\"name\":\"湖北\",\"value\":1308},{\"name\":\"广西\",\"value\":390},{\"name\":\"甘肃\",\"value\":42},{\"name\":\"山西\",\"value\":1554},{\"name\":\"内蒙古\",\"value\":1367},{\"name\":\"陕西\",\"value\":1076},{\"name\":\"吉林\",\"value\":1650},{\"name\":\"福建\",\"value\":396},{\"name\":\"贵州\",\"value\":442},{\"name\":\"广东\",\"value\":371},{\"name\":\"青海\",\"value\":124},{\"name\":\"西藏\",\"value\":927},{\"name\":\"四川\",\"value\":1280},{\"name\":\"宁夏\",\"value\":234},{\"name\":\"海南\",\"value\":495},{\"name\":\"台湾\",\"value\":105},{\"name\":\"香港\",\"value\":1564},{\"name\":\"澳门\",\"value\":1796}]}]}";
//        return "{\"title\":{\"text\":\"疫情地图\",\"subtext\":\"\",\"x\":\"center\"},\"tooltip\":{\"trigger\":\"item\"},\"legend\":{\"orient\":\"vertical\",\"x\":\"left\",\"data\":[\"确诊数\"]},\"dataRange\":{\"x\":\"left\",\"y\":\"bottom\",\"splitList\":[{\"start\":1000,\"label\":\">1000人\",\"color\":\"#934A34\"},{\"start\":100,\"end\":999,\"label\":\"100-1000人\",\"color\":\"#34528D\"},{\"start\":10,\"end\":99,\"label\":\"10-99人\",\"color\":\"#5474D1\"},{\"start\":1,\"end\":9,\"label\":\"1-9人\",\"color\":\"#899EDD\"},{\"end\":0,\"color\":\"#F3F3F3\"}],\"color\":[\"#34528D\",\"#5474D1\",\"#FFFFFF\"]},\"roamController\":{\"show\":true,\"width\":40,\"height\":60,\"x\":\"right\",\"mapTypeControl\":{\"china\":true}},\"series\":[{\"name\":\"确诊数\",\"type\":\"map\",\"mapType\":\"china\",\"roam\":false,\"zoom\":1.2,\"itemStyle\":{\"normal\":{\"label\":{\"show\":true,\"fontSize\":\"10\",\"textStyle\":{\"color\":\"rgb(9, 9, 9)\"}}},\"emphasis\":{\"label\":{\"show\":true}}},\"data\":[{\"name\":\"北京\",\"value\":352},{\"name\":\"天津\",\"value\":110},{\"name\":\"上海\",\"value\":311},{\"name\":\"重庆\",\"value\":509},{\"name\":\"河北\",\"value\":251},{\"name\":\"河南\",\"value\":1135},{\"name\":\"云南\",\"value\":154},{\"name\":\"辽宁\",\"value\":116},{\"name\":\"黑龙江\",\"value\":378},{\"name\":\"湖南\",\"value\":946},{\"name\":\"安徽\",\"value\":889},{\"name\":\"山东\",\"value\":497},{\"name\":\"新疆\",\"value\":59},{\"name\":\"江苏\",\"value\":543},{\"name\":\"浙江\",\"value\":1131},{\"name\":\"江西\",\"value\":884},{\"name\":\"湖北\",\"value\":33366},{\"name\":\"广西\",\"value\":222},{\"name\":\"甘肃\",\"value\":86},{\"name\":\"山西\",\"value\":124},{\"name\":\"内蒙古\",\"value\":60},{\"name\":\"陕西\",\"value\":225},{\"name\":\"吉林\",\"value\":83},{\"name\":\"福建\",\"value\":272},{\"name\":\"贵州\",\"value\":133},{\"name\":\"广东\",\"value\":1219},{\"name\":\"青海\",\"value\":18},{\"name\":\"西藏\",\"value\":1},{\"name\":\"四川\",\"value\":436},{\"name\":\"宁夏\",\"value\":587},{\"name\":\"海南\",\"value\":157},{\"name\":\"台湾\",\"value\":18},{\"name\":\"香港\",\"value\":50},{\"name\":\"澳门\",\"value\":10}]}]}";
    }

    override fun removeEChartActionEvents(): Array<EChartsEventAction> {
        return arrayOf()
    }

    override fun addEChartActionEvents(): Array<EChartsEventAction> {
        return arrayOf(EChartsEventAction.Click,EChartsEventAction.DataRangeSelected,EChartsEventAction.LegendSelectChanged)
    }

    override fun onHandlerResponseAction(action: EChartsEventAction,data:String?) {
        Toast.makeText(baseContext,data,Toast.LENGTH_SHORT).show()
    }
}
