    /**
     * 使用百度前度Echart框架http://echarts.baidu.com/、https://gitee.com/free/ECharts
     * Created by amoryin on 2020/2/9.
     */

    $(function() {
        init();
    });

    var myChart;

    function init() {
        getMyChart();
        showChartLoading();
        var echartJson = initChartOptions();
        loadEcharts(echartJson);
    }

    function initChartOptions() {
        //必须加JOSN.parse 转换数据类型
        var echartJson = Android.getChartOptions();
        return echartJson;
    }

    function getMyChart() {
        if(typeof myChart !== 'undefined') {return myChart;}
//        showDebugMessage("initEchartView");
        var myChartDoc = document.getElementById('myChart');
        /*
         *var height = document.documentElement.clientHeight;
         *var height = window.innerHeight
         */
        var height = window.innerHeight;
//        showDebugMessage("height" + height.toString());
        $(myChartDoc).css('height', height);
        myChart = echarts.init(myChartDoc);
        return myChart;
    }

    function showDebugMessage(msg){
        if (typeof Android !== 'undefined') {
           Android && Android.showMessage(msg);
        }
    }

    /**
	 * 构建动态图表
	 */
    function loadEcharts(echartJson) {
//        showDebugMessage("loadEcharts");
        //clear
        getMyChart().clear();
        //show
        var option = JSON.parse(echartJson);
        option = preTask(option);
        getMyChart().setOption(option);
        myChart.dispatchAction({
            type: 'takeGlobalCursor',
            key: 'dataZoomSelect',
            // 启动或关闭
            dataZoomSelectActive: true
        });
        hideChartLoading();
    }

    /*
     *刷新图表
     */
    function refreshEchartsWithOption(echartJson) {
        //refresh
        showChartLoading();
        getMyChart().clear();
        if(echartJson === 'null'){
            echartJson = initChartOptions();
        }
        var option = JSON.parse(echartJson);
        option = preTask(option);
        getMyChart().setOption(option, true,false,true);//刷新，带上第二个参数true
        hideChartLoading();
    }

    /*
     *  页面元素加载完毕
     */
    function onEchartViewLoaded() {
//        showDebugMessage("onEchartViewLoaded");
        if (typeof Android !== 'undefined') {
           Android && Android.chartViewLoaded();
        }
    }
    /*
     *  页面元素加载开始
     */
    function onEchartViewLoading() {
//        showDebugMessage("onEchartViewLoading");
        if (typeof Android !== 'undefined') {
           Android && Android.chartViewLoading();
        }
    }

    /*
     *添加图表事件响应监听
     */
    function addEchartActionHandler(eventName) {
//        showDebugMessage("addEchartActionHandler:" + eventName);
        getMyChart().on(eventName, onEchartViewAction);
    }
    function onEchartViewAction(action) {
//        showDebugMessage("onEchartViewAction:" + action.event);
//        console.log(parseToString(action));
        if (typeof Android !== 'undefined') {
            if (typeof action.event !== 'undefined') {
                Android && Android.onEChartActionEventResponse(action.event.type,parseToString(action.data));
            }else{
                Android && Android.onEChartActionEventResponse(action.type,parseToString(action));
            }
        }
    }

    /*
     *移除图表事件响应监听
     */
    function removeEchartActionHandler(eventName) {
//        showDebugMessage("removeEchartActionHandler:" + eventName);
        getMyChart().off(name, removeEchartViewAction);
    }
    function removeEchartViewAction(action) {
//        showDebugMessage("removeEchartViewAction:" + action.event.type);
        if (typeof Android !== 'undefined') {
            Android && Android.removeEChartActionEventResponse(action.event.type);
        }
    }

    function showChartLoading() {
        getMyChart().showLoading();
        onEchartViewLoading();
    }
    function hideChartLoading() {
        getMyChart().hideLoading();
        onEchartViewLoaded();
    }


    //util
    function preTask(obj) {
        var result;
        if(typeof(obj) == 'object') {
            if(obj instanceof Array) {
                result = new Array();
                for (var i = 0, len = obj.length; i < len ; i++) {
                     result.push(preTask(obj[i]));
                }
                return result;
            } else if(obj instanceof RegExp){
                return obj;
            } else {
                result = new Object();
                for (var prop in obj) {
                    result[prop] = preTask(obj[prop]);
                }
                return result;
            }
        } else if(typeof(obj) == 'string'){
            try {
                if(typeof(eval(obj)) == 'function'){
                    return eval(obj);
                } else if (typeof(eval(obj) == 'object') && (eval(obj) instanceof Array || eval(obj) instanceof CanvasGradient)) {
                    return eval(obj);
                }
            }catch(e) {
                return obj;
            }
            return obj;
        } else if(typeof(obj) == 'undefined'){
            showDebugMessage("数据格式错误");
            return obj;
        } else {
            return obj;
        }
    }

    //option 转 string
    function parseToString(o){
        var cache = [];
        var str = JSON.stringify(o, function(key, value) {
            if (typeof value === 'object' && value !== null) {
                if (cache.indexOf(value) !== -1) {
                    // 移除
                    return;
                }
                // 收集所有的值
                cache.push(value);
            }
            return value;
        });
        cache = null; // 清空变量，便于垃圾回收机制回收
        return str;
    }

