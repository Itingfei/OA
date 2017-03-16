package com.dongao.oa.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;  
import java.text.DateFormat;
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;  
import java.util.HashMap;  
import java.util.Map;  
  
/** 
 * 取得标准格式日期和时间 
 * @author yangjie
 * @date 2013-2-25 上午09:31:25
 */
public class DateUtils {  
    
	/**缺省日期格式*/  
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";  
	  
	/**缺省时间格式*/  
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";  

	/**缺省月格式*/  
	public static final String DEFAULT_MONTH = "MONTH";  

	/**缺省年格式*/  
	public static final String DEFAULT_YEAR = "YEAR";  

	/**缺省日格式*/  
	public static final String DEFAULT_DATE = "DAY";  

	/**缺省小时格式*/  
	public static final String DEFAULT_HOUR = "HOUR";  
	  
	/**缺省分钟格式*/  
	public static final String DEFAULT_MINUTE = "MINUTE";  
	  
	/**缺省秒格式*/  
	public static final String DEFAULT_SECOND = "SECOND";  
	  
	/**缺省长日期格式*/  
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH-mm";  
	
	/**精确到分钟格式*/  
	public static final String DEFAULT_DATETIME_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";  

	/**缺省长日期格式,精确到秒*/  
	public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";  

	/**星期数组*/  
	public static final String[] WEEKS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};  

	/** 
	 * 取当前日期的字符串表示 
	 * @return 当前日期的字符串 ,如2010-05-28 
	 **/  
	public static String today() {  
		return today(DEFAULT_DATE_FORMAT);  
	}  

	/** 
	 * 根据输入的格式得到当前日期的字符串 
	 * @param strFormat 日期格式 
	 * @return 
	 */  
	public static String today(String strFormat) {  
		return toString(new Date(), strFormat);  
	}  

	/** 
	 * 取当前时间的字符串表示, 
	 * @return 当前时间,如:21:10:12 
	 **/  
	public static String currentTime() {  
		return currentTime(DEFAULT_TIME_FORMAT);  
	}  

	/** 
	 * 根据输入的格式获取时间的字符串表示 
	 * @param 输出格式,如'hh:mm:ss' 
	 * @return 当前时间,如:21:10:12 
	 **/  

	public static String currentTime(String strFormat) {  
		return toString(new Date(), strFormat);  
	}  

	  
	/** 
	 * 取得相对于当前时间增加天数/月数/年数后的日期 
	 * <br> 
	 * 欲取得当前日期5天前的日期,可做如下调用:<br> 
	 *        getAddDay("DATE", -5). 
	 * @param field,段,如"year","month","date",对大小写不敏感 
	 * @param amount,增加的数量(减少用负数表示),如5,-1 
	 * @return    格式化后的字符串 如"2010-05-28" 
	 * @throws ParseException  
	 **/  

	public static String getAddDay(String field, int amount) throws ParseException {  
		return getAddDay(field, amount, null);  
	}  

	/** 
	 * 取得相对于当前时间增加天数/月数/年数后的日期,按指定格式输出 
	 * 
	 * 欲取得当前日期5天前的日期,可做如下调用:<br> 
	 *        getAddDay("DATE", -5,'yyyy-mm-dd hh:mm'). 
	 * @param field,段,如"year","month","date",对大小写不敏感 
	 * @param amount,增加的数量(减少用负数表示),如5,-1 
	 * @param strFormat,输出格式,如"yyyy-mm-dd","yyyy-mm-dd hh:mm" 
	 * @return 格式化后的字符串 如"2010-05-28" 
	 * @throws ParseException  
	 **/  
	public static String getAddDay(String field, int amount, String strFormat) throws ParseException {  
		return getAddDay(null, field, amount, strFormat);  
	}  
	  
	/** 
	 * 功能：对于给定的时间增加天数/月数/年数后的日期,按指定格式输出 
	 *  
	 * @param date String 要改变的日期 
	 * @param field int 日期改变的字段，YEAR,MONTH,DAY 
	 * @param amount int 改变量 
	 * @param strFormat 日期返回格式 
	 * @return  
	 * @throws ParseException 
	 */  
	public static String getAddDay(String date, String field, int amount, String strFormat) throws ParseException{  
		if(strFormat == null){  
			strFormat = DEFAULT_DATETIME_FORMAT_SEC;  
		}  
		Calendar rightNow = Calendar.getInstance();  
		if(date != null && !"".equals(date.trim())){  
			rightNow.setTime(parseDate(date, strFormat));  
		}  
		if(field == null){  
			return toString(rightNow.getTime(), strFormat);  
		}  
		rightNow.add(getInterval(field), amount);  
		return  toString(rightNow.getTime(), strFormat);  
	}  

	/** 
	 * 获取时间间隔类型 
	 * @param field 时间间隔类型 
	 * @return 日历的时间间隔 
	 */  
	protected static int getInterval(String field){  
		String tmpField = field.toUpperCase();  
		if (tmpField.equals(DEFAULT_YEAR)){  
			return Calendar.YEAR;  
		}else if (tmpField.equals(DEFAULT_MONTH)){  
			return Calendar.MONTH;  
		}else if (tmpField.equals(DEFAULT_DATE)){  
			return Calendar.DATE;  
		}else if(DEFAULT_HOUR.equals(tmpField)){  
			return Calendar.HOUR;  
		}else if(DEFAULT_MINUTE.equals(tmpField)){  
			return Calendar.MINUTE;  
		}else{  
			return Calendar.SECOND;  
		}  
	 }  

	/** 
	 * 获取格式化对象 
	 * @param strFormat 格式化的格式 如"yyyy-MM-dd" 
	 * @return 格式化对象 
	 */  
	public static SimpleDateFormat getSimpleDateFormat(String strFormat){  
		if(strFormat != null && !"".equals(strFormat.trim())){  
			return new SimpleDateFormat(strFormat);  
		}else{  
			return new SimpleDateFormat();  
		}  
	}  
	  
	/** 
	 * 得到当前日期的星期数 
	 * @return 当前日期的星期的字符串 
	 * @throws ParseException 
	 */  
	public static String getWeekOfMonth() throws ParseException{  
		 return getWeekOfMonth(null, null);  
	}  
	  
	/** 
	 * 根据日期的到给定日期的在当月中的星期数 
	 * @param date 给定日期 
	 * @return 
	 * @throws ParseException 
	 */  
	public static String getWeekOfMonth(String date, String fromat) throws ParseException{  
		Calendar rightNow = Calendar.getInstance();  
		if(date != null && !"".equals(date.trim())){  
			rightNow.setTime(parseDate(date, fromat));  
		}  
		return WEEKS[rightNow.get(Calendar.WEEK_OF_MONTH)];  
	}  
	  
	 /** 
	  * 将java.util.date型按照指定格式转为字符串 
	  * @param date  源对象 
	  * @param format 想得到的格式字符串 
	  * @return 如：2010-05-28 
	  */  
	 public static String toString(Date date, String format) {  
		 return getSimpleDateFormat(format).format(date);  
	 }  

	 /** 
	  * 将java.util.date型按照缺省格式转为字符串 
	  * @param date 源对象 
	  * @return 如：2010-05-28 
	  */  
	 public static String toString(Date date) {  
		 return toString(date, DEFAULT_DATE_FORMAT);  
	 }  

	/** 
	 * 强制类型转换 从串到日期 
	 * @param sDate  源字符串，采用yyyy-MM-dd格式 
	 * @param sFormat ps 
	 * @return 得到的日期对象 
	 * @throws ParseException 
	 */  
	public static Date parseDate(String strDate, String format) throws ParseException {  
		return getSimpleDateFormat(format).parse(strDate);  
	}  

	/*** 
	 * 根据传入的毫秒数和格式，对日期进行格式化输出 
	 * @version 2011-7-12 
	 * @param object  
	 * @param format 
	 * @return 
	 */  
	public static String millisecondFormat(Long millisecond , String format) {  
		if(millisecond == null || millisecond <= 0){  
			throw new  IllegalArgumentException(String.format("传入的时间毫秒数[%s]不合法", "" + millisecond));  
		}  
		if(format == null || "".equals(format.trim())){  
			format = DEFAULT_DATE_FORMAT;    
		}  
		return toString(new Date(millisecond), format);  
	}  
	  
	/** 
	 * 强制类型转换 从串到时间戳 
	 * @param sDate 源串 
	 * @param sFormat 遵循格式 
	 * @return 取得的时间戳对象 
	 * @throws ParseException 
	 */  
	public static Timestamp parseTimestamp(String strDate, String format) throws ParseException {  
		Date utildate = getSimpleDateFormat(format).parse(strDate);  
		return new Timestamp(utildate.getTime());  
	} 
	  
	/**
	 * 字符串转日期  日期在转字符串
	 * @param strDate  原始字符串
	 * @param format1     时间格式1
	 * @param format2     时间格式2
	 * @return
	 * @throws ParseException
	 */
	public static String strToStr(String strDate, String format1, String format2) throws ParseException {  
		Date utildate = getSimpleDateFormat(format1).parse(strDate);  
		return toString(utildate, format2);  
	} 

	/** 
	 * getCurDate 取当前日期 
	 * @return java.util.Date型日期 
	 **/  
	public static Date getCurDate() {  
		return (new Date());  
	}  

	/** 
	 * getCurTimestamp 取当前时间戳 
	 * @return java.sql.Timestamp 
	 **/  
	public static Timestamp getCurTimestamp() {  
		return new Timestamp(new Date().getTime());  
	}  

	/** 
	 * getCurTimestamp 取遵循格式的当前时间 
	 * @param sFormat 遵循格式 
	 * @return java.sql.Timestamp 
	 **/  
	public static Date getCurDate(String format) throws Exception {  
		return getSimpleDateFormat(format).parse(toString(new Date(), format));  
	}  
	  
	  
	/** 
	 * Timestamp按照指定格式转为字符串 
	 * @param timestamp 源对象 
	 * @param format ps（如yyyy.mm.dd） 
	 * @return 如：2010-05-28 或2010-05-281 13:21 
	 */  
	public static String toString(Timestamp timestamp, String format) {  
		if (timestamp == null) {  
			return "";  
		}  
		return toString(new Date(timestamp.getTime()), format);  
	}  

	/** 
	 * Timestamp按照缺省格式转为字符串 
	 * @param ts 源对象 
	 * @return 如：2010-05-28 
	 */  
	public static String toString(Timestamp ts) {  
		return toString(ts, DEFAULT_DATE_FORMAT);  
	}  

	/** 
	 * Timestamp按照缺省格式转为字符串，可指定是否使用长格式 
	 * @param timestamp 欲转化之变量Timestamp 
	 * @param fullFormat 是否使用长格式 
	 * @return 如：2010-05-28 或2010-05-28 21:21 
	 */  
	public static String toString(Timestamp timestamp, boolean fullFormat) {  
		if (fullFormat) {  
			return toString(timestamp, DEFAULT_DATETIME_FORMAT_SEC);  
		}else {  
			return toString(timestamp, DEFAULT_DATE_FORMAT);  
		}  
	}  

	/** 
	 * 将sqldate型按照指定格式转为字符串 
	 * @param sqldate 源对象 
	 * @param sFormat ps 
	 * @return 如：2010-05-28 或2010-05-28 00:00 
	 */  
	public static String toString(java.sql.Date sqldate, String sFormat) {  
		if (sqldate == null) {  
			return "";  
		}  
		return toString(new Date(sqldate.getTime()), sFormat);   
	}  
	  
	/** 
	 * 将sqldate型按照缺省格式转为字符串 
	 * @param sqldate 源对象 
	 * @return 如：2010-05-28 
	 */  
	public static String toString(java.sql.Date sqldate) {  
		return toString(sqldate, DEFAULT_DATE_FORMAT);  
	}  
	  
	/** 
	 * 计算日期时间之间的差值， date1得时间必须大于date2的时间 
	 * @version 2011-7-12 
	 * @param date1  
	 * @param date2 
	 * @return {@link java.util.Map} Map的键分别为, day(天), hour(小时),minute(分钟)和second(秒)。 
	 */  
	public static Map<String, Long> timeDifference(final Date date1, final Date date2){  
		if(date1 == null || date2 == null){  
			throw new NullPointerException("date1 and date2 can't null");  
		}  
		long mim1 = date1.getTime();  
		long mim2 = date2.getTime();  
		if(mim1 < mim2){  
			throw new IllegalArgumentException(String.format("date1[%s] not be less than date2[%s].", mim1 + "", mim2 + ""));  
		}  
		long m = (mim1 - mim2 + 1) / 1000l;  
		long mday = 24 * 3600;  
		final Map<String, Long> map = new HashMap<String, Long>();  
		map.put("day", m / mday);  
		m = m % mday;  
		map.put("hour", (m) / 3600);  
		map.put("minute", (m % 3600) / 60);  
		map.put("second", (m % 3600 % 60));  
		return map;  
	}  
	
	/**
	 * 设置当前时间为当天的最初时间（即00时00分00秒）
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar setStartDay(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}
	/**
	 * 设置当前时间为当天的最后时间（即23时59分59秒）
	 * 
	 * @param cal
	 * @return
	 */
	public static Calendar setEndDay(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal;
	}
	  
	public static Map<String, Integer> compareTo(final Date date1, final Date date2){  
		if(date1 == null || date2 == null){  
			return null;  
		}  
		long time1 = date1.getTime();  
		long time2 = date2.getTime();  
		long time = Math.max(time1, time2) - Math.min(time1, time2);  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTimeInMillis(time);  
		Map<String, Integer> map = new HashMap<String, Integer>();  
		map.put("year", (calendar.get(Calendar.YEAR) - 1970) > 0? (calendar.get(Calendar.YEAR) - 1970): 0);  
		map.put("month", (calendar.get(Calendar.MONTH) - 1) > 0? (calendar.get(Calendar.MONTH) - 1): 0);  
		map.put("day", (calendar.get(Calendar.DAY_OF_MONTH) - 1) > 0? (calendar.get(Calendar.DAY_OF_MONTH) - 1): 0);  
		map.put("hour", (calendar.get(Calendar.HOUR_OF_DAY) - 8) > 0? (calendar.get(Calendar.HOUR_OF_DAY) - 8): 0);  
		map.put("minute", calendar.get(Calendar.MINUTE) > 0? calendar.get(Calendar.MINUTE): 0);  
		map.put("second", calendar.get(Calendar.SECOND) > 0? calendar.get(Calendar.SECOND): 0);  
		return map;  
	}  
    
	/** 
     * 返回当前日期时间字符串<br> 
     * 默认格式:yyyymmddhhmmss 
     *  
     * @return String 返回当前字符串型日期时间 
     */  
    public static BigDecimal getCurrentTimeAsNumber() {  
        String returnStr = null;  
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");  
        Date date = new Date();  
        returnStr = f.format(date);  
        return new BigDecimal(returnStr);  
    }  
    
    /**
     * 秒加减处理
     * @param time   时间
     * @param amount 加减时间
     * @return
     */
    public static Date dateFormat(Date time, int amount) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.SECOND, amount);//时间加减
			return calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
     * 时间间隔
     * @param l
     * @return
     */
    public static String getBetweenDate(long l){
    	try {  
        	long day=l/(24*60*60*1000); 
        	long hour=(l/(60*60*1000)-day*24); 
        	long min=((l/(60*1000))-day*24*60-hour*60);
        	long s=(l/1000-day*24*60*60-hour*60*60-min*60);
        	System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
        	String betweenDateString = "";
        	if(day!=0){
        		betweenDateString = ""+day+"天"+hour+"小时"+min+"分"+s+"秒";
        	}else{
        		if(hour!=0){ 
        			betweenDateString = ""+hour+"小时"+min+"分"+s+"秒";
        		}else{
        			 if(min!=0){
        				 betweenDateString = ""+min+"分"+s+"秒";
        			 }else{
        				 betweenDateString = ""+s+"秒";
        			 }
        		}
        	}
        	return betweenDateString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * 比较两个日期  返回0表示date1=date2,返回1表示date1<date2,返回-1表示date1>date2
     * @param date1  前时间
     * @param date2  后时间
     * @return
     */
    public static Integer dateCompare(Date date1, Date date2) {
    	if(null != date1 && null != date2) {
    		if(date2.equals(date1)){
    			return 0;
    		}else if(date2.after(date1)){
    			return 1;
    		}else if(date2.before(date1)){
    			return -1;
    		}
    	}
    	return null;
    }
    /**
     * 通过生日计算年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date birthday) {
    	Calendar cal = Calendar.getInstance();

    	if (cal.before(birthday)) {
    		throw new IllegalArgumentException(
    				"The birthDay is before Now.It's unbelievable!");
    	}

    	int yearNow = cal.get(Calendar.YEAR);
    	int monthNow = cal.get(Calendar.MONTH) + 1;
    	int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

    	cal.setTime(birthday);
    	int yearBirth = cal.get(Calendar.YEAR);
    	int monthBirth = cal.get(Calendar.MONTH) + 1;
    	int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

    	int age = yearNow - yearBirth;

    	if (monthNow <= monthBirth) {
    		if (monthNow == monthBirth) {
    			// monthNow==monthBirth 
    			if (dayOfMonthNow < dayOfMonthBirth) {
    				age--;
    			}
    		} else {
    			// monthNow>monthBirth 
    			age--;
    		}
    	}
    	return age;
    }
    

    /** 
     * 智能转换日期 
     * 
     * @param date 
     * @return 
     */  
    public static String smartFormat(Date date) {  
        String dateStr = null;  
        if (date == null) {  
            dateStr = "";  
        } else {  
            try {  
                dateStr = toString(date, DateUtils.DEFAULT_DATETIME_FORMAT_SEC);  
                //时分秒  
                if (dateStr.endsWith(" 00:00:00")) {  
                    dateStr = dateStr.substring(0, 10);  
                }  
                //时分  
                else if (dateStr.endsWith("00:00")) {  
                    dateStr = dateStr.substring(0, 16);  
                }  
                //秒  
                else if (dateStr.endsWith(":00")) {  
                    dateStr = dateStr.substring(0, 16);  
                }  
            } catch (Exception ex) {  
                throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);  
            }  
        }  
        return dateStr;  
    }  
  
    
    
    /** 
     * 智能转换日期 
     * 
     * @param text 
     * @return 
     */  
    public static Date smartFormat(String text) {  
        Date date = null;  
        try {  
            if (text == null || text.length() == 0) {  
                date = null;  
            } else if (text.length() == 10) {  
                date = parseDate(text, DateUtils.DEFAULT_DATE_FORMAT);  
            } else if (text.length() == 13) {  
                date = new Date(Long.parseLong(text));  
            } else if (text.length() == 16) {  
                date = parseDate(text, DateUtils.DEFAULT_DATETIME_FORMAT_MINUTE);  
            } else if (text.length() == 19) {  
                date = parseDate(text, DateUtils.DEFAULT_DATETIME_FORMAT_SEC);  
            } else {  
                throw new IllegalArgumentException("日期长度不符合要求!");  
            }  
        } catch (Exception e) {  
            throw new IllegalArgumentException("日期转换失败!");  
        }  
        return date;  
    }
    /**
     * 获取上一个工作日 yyyyMMdd
     * @param pDate
     * @return
     * @throws ParseException 
     */
    public static String getPreviousWorkingDay(String pDate) throws ParseException {
    	String preDay = pDate;
    	Date date = DateUtils.parseDate(pDate, DEFAULT_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	//int today = cal.getTime().getDay();//取得今天的星期值
    	int today = cal.get(Calendar.DAY_OF_WEEK);
    	if(today == cal.getFirstDayOfWeek()){//今天在这个星期中是否为第一天
    		cal.roll(Calendar.DAY_OF_YEAR, -3);
		}else{
			cal.roll(Calendar.DAY_OF_YEAR, -1);
		}
    	preDay = DateUtils.toString(cal.getTime(), DEFAULT_DATE_FORMAT);

    	return preDay;
    }
   
}  
