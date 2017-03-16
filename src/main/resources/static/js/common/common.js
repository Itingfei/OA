/**
 * Created by yangjie on 2016/8/14.
 */
/**
 * 弹出确认框。
 *
 * @param msg 消息。
 * @param fun 确定后执行的方法。
 */
function confirmMsg(msg, yes, cancel)
{
    layer.confirm(msg, {
        icon:3,
        btn: ['确定','取消'] //按钮
    }, yes, cancel);
}
/**
 * 弹出消息框。
 *
 * @param msg 消息。
 */
function alertMsg(msg, yes)
{
    layer.alert(msg,{
            icon:0
    },
    yes);
}
/**
 *比较时间
 **/
function compareTime(startTime,endTime) {
    var syear =  startTime.substring(0,startTime.indexOf("年"));
    var smonth = startTime.substring(5,startTime.indexOf("月"));
    var sday = startTime.substring(8,startTime.indexOf("日"));
    var starttime = new Date(syear, smonth, sday);
    var starttimes = starttime.getTime();
    var eyear =  endTime.substring(0,endTime.indexOf("年"));
    var emonth = endTime.substring(5,endTime.indexOf("月"));
    var eday = endTime.substring(8,endTime.indexOf("日"));
    var lktime = new Date(eyear, emonth, eday);
    var lktimes = lktime.getTime();
    if (starttimes > lktimes) {
        alert('开始时间不能大于结束时间，请检查');
        return false;
    }
    else
        return true;
}
// function clickOpenModel(objId,url,title) {
//     if (objId.indexOf("#") == 0) {
//         objId = objId;
//     } else {
//         objId = "#" + objId;
//     }
//     $(objId).click(function () {
//         openLevelOneModal(url,"",title);
//     });
// }
