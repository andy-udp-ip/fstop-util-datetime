
/*
 * Copyright (c) 2017, FSTOP, Inc. All Rights Reserved.
 *
 * You may not use this file except in compliance with the License.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tw.com.fstop.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * <pre>
 * Class for process Date and Time, based on epoch calculation.
 * To avoid class naming collision with other third party class, this class is renamed from DateTime to DateTimeUtil. 
 *
 * 23:59 is the last minute of the day and 00:00 is the first minute of the next day. 
 * Thus, 23:59 on Dec 31, 1999 &lt; 00:00 on Jan 1, 2000 &lt; 00:01 on Jan 1, 2000.
 * Midnight also belongs to "am", and noon belongs to "pm", 
 * so on the same day, 12:00 am (midnight) &lt; 12:01 am, and 12:00 pm (noon) &lt; 12:01 pm
 *
 * 時間
 *  GMT: 格林威治標準時間（Greenwich Mean Time） 
 *  UTC: 世界協調時間（Coordinated Universal Time） , 1972 年 UTC 採用了閏秒（leap second）修正
 *  EPOCH: UTC 時間 1970 年 1 月 1 日 00:00:00
 * 
 * 時區
 *   UTC 偏移（offset）
 *   經度每 15 度偏移一小時
 *    
 * 年曆
 *  儒略曆（Julian calendar） 
 *  格里高利曆（Gregorian calendar） 
 *  
 * 時間日期表示方法的標準 ISO8601 
 *   yyyy-mm-ddTHH:MM:SS.SSS
 * 
 * 日光節約時間（Daylight saving time），也稱為夏季時間（Summer time），基本上就是在實施的第一天，讓白天的時間增加一小時，而結束後再調整一小時回來。 
 *
 * Java 中 Calendar 的預設實作類別  GregorianCalendar 實際上是儒略曆與格里高利曆的混合。
 * 改曆時間可以使用 GregorianCalendar 的 setGregorianChange 來修改，設為 Date(Long.MAX_VALUE) 就是純儒略曆，
 * 設為 Date(Long.MIN_VALUE)，就是純綷的格里高利曆。
 * 
 * ISO8601 的定義中，19 世紀是指 1900 至 1999 年（包含該年）
 * 格里高利曆的 19 世紀是指 1801 年至 1900 年（包含該年）
 * 
 * Note:
 *   DateFormat implementation is not thread safe!
 * 
 * </pre>
 * 
 * @since 1.0
 */
public class DateTimeUtil
{
    
    static final String TIMEZONE_ID_UTC = "UTC";
    static int ONE_HOUR_SECONDS = 3600;
    static int ONE_SECOND_MILLIS = 1000;
    
    
    /**
     * Get current system timezone id.
     * For example : Asia/Taipei
     * 
     * @return      timezone id
     */
    public static String getCurrentTimeZoneID()
    {
        return TimeZone.getDefault().getID();        
    }
    
    /**
     * Get current system timezone offset value.
     * @return      timezone offset value in milliseconds
     */
    public static int getCurrentTimeZoneOffset()
    {
        Calendar calendar = Calendar.getInstance();
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        return zoneOffset;
    }
    
    /**
     * Get current system timezone offset value in hour.
     * @return      timezone offset value in hour
     */
    public static int getCurrentTimeZoneOffsetHour()
    {
        //milliseconds -> seconds -> hour
        return getCurrentTimeZoneOffset() / ONE_SECOND_MILLIS / ONE_HOUR_SECONDS;
    }
        
    /**
     * Get current EPOCH milliseconds.
     * Here epoch is always UTC based, no need to convert.
     * 
     * @return      epoch millisecond
     */
    public static long getCurrentEpoch()
    {
        return System.currentTimeMillis();
    }
    
    /**
     * Get current EPOCH seconds.
     * Here epoch is always UTC based, no need to convert.
     * 
     * @return      current epoch second
     */
    public static long getCurrentEpochSecond()
    {
        return (getCurrentEpoch()/ONE_SECOND_MILLIS);
    }

    /**
     * Get current EPOCH seconds.
     * Value returned by this function should be the same with getCurrentEpochSecond().
     * 
     * @return      current epoch second
     */
    public static long getEpochSecond()
    {
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_ID_UTC);
        Calendar calendar = Calendar.getInstance(timeZone);
        long secondsSinceEpoch = calendar.getTimeInMillis()/ONE_SECOND_MILLIS;
        return secondsSinceEpoch;
    }          
    
    /**
     * Get specified datetime string.
     * 
     * @param dtFormat      datetime format
     * @return              formatted datetime string
     */
    public static String getCurrentDateString(String dtFormat)
    {
        DateFormat format = new SimpleDateFormat(dtFormat);
        Calendar calendar = Calendar.getInstance();       
        String formatted = format.format(calendar.getTime());        
        return formatted;
    }
    
    
    /**
     * Get current year.
     * @return      year
     */
    public static int getCurrentYear()
    {
        Calendar  calendar = null;
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);     
    }
    
    /**
     * Get current month.
     * @return      month
     */
    public static int getCurrentMonth()
    {
        Calendar  calendar = null;
        calendar = Calendar.getInstance();
        return 1 + calendar.get(Calendar.MONTH);
    }

    /**
     * Get current day of month.
     * @return      day of month
     */
    public static int getCurrentDay()
    {
        Calendar  calendar = null;
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get local day of month.
     * @param timezoneID timezone id
     * @return      day of month
     */
    public static int getLocalDay(String timezoneID)
    {
        TimeZone timeZone = TimeZone.getTimeZone(timezoneID);
        Calendar  calendar = null;
        calendar = Calendar.getInstance(timeZone);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Get current system timezone offset value.
     * @param timezoneID  timezone id
     * @return      timezone offset value in milliseconds
     */
    public static int getLocalTimeZoneOffset(String timezoneID)
    {
        TimeZone timeZone = TimeZone.getTimeZone(timezoneID);
        Calendar calendar = Calendar.getInstance(timeZone);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        return zoneOffset;
    }

    /**
     * Input timezone id and returns timezone offset value in hour.
     * 
     * @param timezoneID    timezone id
     * @return              timezone offset value in hour
     */
    public static int getLocalTimeZoneOffsetHour(String timezoneID)
    {
        //milliseconds -> seconds -> hour
        return getLocalTimeZoneOffset(timezoneID) / ONE_SECOND_MILLIS / ONE_HOUR_SECONDS;
    }
    
    /**
     * Get current system daylight saving offset value.
     * @return      daylight saving offset value
     */
    public static int getCurrentDaylightSavingOffset()
    {
        Calendar calendar = Calendar.getInstance();
        int dlsOffset = calendar.get(Calendar.DST_OFFSET);
        return dlsOffset;
    }
    
    /**
     * Get current system daylight saving offset value in hour.
     * @return      daylight saving offset value in hour
     */
    public static int getCurrentDaylightSavingOffsetHour()
    {
        //milliseconds -> seconds -> hour
        return getCurrentDaylightSavingOffset() / ONE_SECOND_MILLIS / ONE_HOUR_SECONDS;
    }

    /**
     * Input timezone id and returns daylight saving offset value.
     * @param timezoneID    timezone id
     * @return              daylight saving offset value in millisecond
     */
    public static int getLocalDaylightSavingOffset(String timezoneID)
    {
        TimeZone timeZone = TimeZone.getTimeZone(timezoneID);
        Calendar calendar = Calendar.getInstance(timeZone);
        int dlsOffset = calendar.get(Calendar.DST_OFFSET);
        return dlsOffset;
    }
    
    /**
     * Input timezone id and returns daylight saving offset value.
     * @param timezoneID    timezone id
     * @return              daylight saving offset value in hour
     */
    public static int getLocalDaylightSavingOffsetHour(String timezoneID)
    {
      //milliseconds -> seconds -> hour
        return getLocalDaylightSavingOffset(timezoneID) / ONE_SECOND_MILLIS / ONE_HOUR_SECONDS;
    }
    
    
    /**
     * Input year, month, day, hour, minute, second and return UTC epoch seconds.
     * 
     * @param year      year
     * @param month     month value from 1 to 12
     * @param day       day of month
     * @param hour      hour in 24 hours
     * @param minute    minute
     * @param second    second
     * @return          epoch seconds
     */
    public static long getEpochSecond(int year, int month, int day, int hour, int minute, int second)
    {
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_ID_UTC);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.clear();   //clears unused field values
        int m = month - 1;  //adjust month value from one base to zero base
        calendar.set(year, m, day, hour, minute , second);
        long secondsSinceEpoch = calendar.getTimeInMillis()/ONE_SECOND_MILLIS;
        return secondsSinceEpoch;
    }          

    /**
     * Input datetime string and return UTC epoch seconds.
     * 
     * @param yyyyMMddhhmiss    date string in yyyyMMddhhmiss formate
     * @return          epoch seconds
     */
    public static long getEpochSecond(String yyyyMMddhhmiss)
    {
        long ret = 0;
        int yyyy = Integer.parseInt(yyyyMMddhhmiss.substring(0, 4));
        int mm = Integer.parseInt(yyyyMMddhhmiss.substring(4, 6));
        int dd = Integer.parseInt(yyyyMMddhhmiss.substring(6, 8));
        int hh = Integer.parseInt(yyyyMMddhhmiss.substring(8, 10));
        int mi = Integer.parseInt(yyyyMMddhhmiss.substring(10, 12));
        int ss = Integer.parseInt(yyyyMMddhhmiss.substring(12, 14));
        ret = getEpochSecond(yyyy, mm, dd, hh, mi, ss);       
        return ret;
    }
    
    /**
     * Normalize UTC epoch second by input value.
     * For example : 
     *   Input a normalize unit 5 then this function will return epoch second which can be divide by 5. 
     *   And returned value is less then current epoch second.
     * 
     * @param unit         normalize unit
     * @return             normalized epoch second
     */
    public static long getNormalizedEpochSecond(int unit)
    {
        long second = getEpochSecond();
        long remider = second % unit;
        second = second - remider;
        return second;
    }

    /**
     * Convert epoch time to string.
     * 
     * @param epoch                 epoch in millisecond
     * @param dtFormat              datetime format
     * @param dateTimeZone          time zone of input epoch
     * @param formateTimeZone       time zone of result datetime string, if null then use current timezone to format
     * @return                      formatted datetime string
     */
    static String epochToDateString(long epoch, String dtFormat, String dtTimeZoneID, String fmtTimeZoneID)
    {
        TimeZone dtTimeZone = TimeZone.getTimeZone(dtTimeZoneID);
        DateFormat format = new SimpleDateFormat(dtFormat);

        if (fmtTimeZoneID != null && !fmtTimeZoneID.isEmpty())
        {
            TimeZone fmtTimeZone = TimeZone.getTimeZone(fmtTimeZoneID);
            format.setTimeZone(fmtTimeZone);            
        }
        
        Calendar calendar = Calendar.getInstance(dtTimeZone);
        calendar.setTimeInMillis(epoch);        
        String formatted = format.format(calendar.getTime());        
        return formatted;
    }

    
    /**
     * Convert epoch to specified datetime string.
     * 
     * @param epoch             epoch time
     * @param dtFormat          datetime format
     * @return                  formatted datetime string
     */
    public static String epochToDateString(long epoch, String dtFormat)
    {
        return epochToDateString(epoch, dtFormat, TIMEZONE_ID_UTC, TIMEZONE_ID_UTC);
    }
    
    /**
     * Convert epoch second to specified datetime string.
     * 
     * @param epochSecond       epoch second
     * @param dtFormat          datetime format 
     * @return                  formatted datetime string
     */
    public static String epochSecondToDateString(long epochSecond, String dtFormat)
    {
        long epoch = epochSecond * ONE_SECOND_MILLIS;
        return epochToDateString(epoch, dtFormat);
    }

    /**
     * Convert utc epoch to current datetime string by specified format.
     * 
     * @param epoch         epoch in milliseconds
     * @param dtFormat      datetime format
     * @return              formatted datetime string
     */
    public static String epochToCurrentDateString(long epoch, String dtFormat)
    {
        return epochToDateString(epoch, dtFormat, TIMEZONE_ID_UTC, null);
    }

    /**
     * Convert utc epoch second to current datetime string by specified format.
     * 
     * @param epochSecond       epoch second
     * @param dtFormat          datetime format
     * @return                  formatted datetime string
     */
    public static String epochSecondToCurrentDateString(long epochSecond, String dtFormat)
    {
        long epoch = epochSecond * ONE_SECOND_MILLIS;
        return epochToCurrentDateString(epoch, dtFormat);
    }
    
    /**
     * Convert utc epoch to local datetime string by specified format.
     * 
     * @param epoch             epoch in millisecond
     * @param dtFormat          datetime format
     * @param timezoneID        timezone id
     * @return                  formatted datetime string
     */
    public static String epochToLocalDateString(long epoch, String dtFormat, String timezoneID)
    {
        return epochToDateString(epoch, dtFormat, timezoneID, timezoneID);
    }

    /**
     * Convert utc epoch to local datetime string by specified format.
     * 
     * @param epochSecond           epoch in second
     * @param dtFormat              datetime format
     * @param timezoneID            timezone id
     * @return                      formatted datetime string
     */
    public static String epochSecondToLocalDateString(long epochSecond, String dtFormat, String timezoneID)
    {
        long epoch = epochSecond * ONE_SECOND_MILLIS;
        return epochToDateString(epoch, dtFormat, timezoneID, timezoneID);
    }
    
    /**
     * Convert date string to epoch.
     * 
     * @param date                  date string to convert
     * @param dtFormat              format of date string
     * @param dtTimeZoneID          timezone of date
     * @param fmtTimeZoneID         timezone of date format 
     * @return                      epoch millisecond
     * @throws ParseException       date string parse error
     */
    public static long dateStringToEpoch(String date, String dtFormat, String dtTimeZoneID, String fmtTimeZoneID) throws ParseException
    {
        TimeZone dtTimeZone = TimeZone.getTimeZone(dtTimeZoneID);
        Calendar calendar = Calendar.getInstance(dtTimeZone);
        
        SimpleDateFormat sdf = new SimpleDateFormat(dtFormat);
        
        if (fmtTimeZoneID != null && !fmtTimeZoneID.isEmpty())
        {
            TimeZone fmtTimeZone = TimeZone.getTimeZone(fmtTimeZoneID);        
            sdf.setTimeZone(fmtTimeZone);                    
        }
        
        calendar.setTime(sdf.parse(date));
        return calendar.getTimeInMillis();
    }
    
    /**
     * Convert date string to epoch based on utc time.
     * 
     * @param date              date string to convert
     * @param dtFormat          format of date string
     * @param fmtTimeZoneID     timezone of date format
     * @return                  epoch millisecond
     * @throws ParseException   date string parse error
     */
    public static long dateStringToEpoch(String date, String dtFormat, String fmtTimeZoneID) throws ParseException
    {
        return dateStringToEpoch(date, dtFormat, TIMEZONE_ID_UTC, fmtTimeZoneID);
    }
    
    /**
     * Convert date string to epoch based on utc time.
     * 
     * @param date                  date string to convert
     * @param dtFormat              format of date string
     * @param fmtTimeZoneID         timezone of date format
     * @return                      epoch second
     * @throws ParseException       date string parse error
     */
    public static long dateStringToEpochSecond(String date, String dtFormat, String fmtTimeZoneID) throws ParseException
    {
        long epoch = dateStringToEpoch(date, dtFormat, fmtTimeZoneID);
        return epoch / ONE_SECOND_MILLIS;
    }
    
    /**
     * Calculate days between two dates.
     * Input sequence of the two dates is not important. 
     * 
     * @param day1      day to calculate
     * @param day2      day to calculate
     * @return          days between two dates
     */
    public static int daysBetween(Calendar day1, Calendar day2)
    {
        Calendar dayOne = (Calendar) day1.clone(), dayTwo = (Calendar) day2.clone();

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR))
        {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        }
        else
        {
            // if day2 > day1 then swap them
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR))
            {
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR))
            {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays;
        }
    }
    
    /**
     * Calculate days between two dates.
     * 
     * @param day1              day to calculate
     * @param day2              day to calculate
     * @param dtFormat          datetime format
     * @param fmtTimeZoneID1    timezone id of day1
     * @param fmtTimeZoneID2    timezone id of day2
     * @return                  days between two dates
     * @throws ParseException   date string parse error
     */
    static int daysBetween(String day1, String day2, String dtFormat, String fmtTimeZoneID1, String fmtTimeZoneID2) throws ParseException
    {
        long epoch1 = dateStringToEpoch(day1, dtFormat, fmtTimeZoneID1);
        long epoch2 = dateStringToEpoch(day2, dtFormat, fmtTimeZoneID2);
        TimeZone dtTimeZone = TimeZone.getTimeZone(TIMEZONE_ID_UTC);
        Calendar calendar1 = Calendar.getInstance(dtTimeZone);
        calendar1.setTimeInMillis(epoch1);        
        Calendar calendar2 = Calendar.getInstance(dtTimeZone);
        calendar2.setTimeInMillis(epoch2);   
        return daysBetween(calendar1, calendar2);        
    }

    /**
     * Calculate days between two dates of current timezone.
     * 
     * @param day1              day to calculate
     * @param day2              day to calculate
     * @param dtFormat          datetime format
     * @return                  days between two dates
     * @throws ParseException   date string parse error
     */
    public static int daysBetween(String day1, String day2, String dtFormat) throws ParseException
    {
        String timezoneID = getCurrentTimeZoneID();
        long epoch1 = dateStringToEpoch(day1, dtFormat, timezoneID);
        long epoch2 = dateStringToEpoch(day2, dtFormat, timezoneID);
        TimeZone dtTimeZone = TimeZone.getTimeZone(TIMEZONE_ID_UTC);
        Calendar calendar1 = Calendar.getInstance(dtTimeZone);
        calendar1.setTimeInMillis(epoch1);        
        Calendar calendar2 = Calendar.getInstance(dtTimeZone);
        calendar2.setTimeInMillis(epoch2);   
        return daysBetween(calendar1, calendar2);        
    }

    /**
     * Calculate days between two epoch dates.
     * 
     * @param epoch1            epoch to calculate
     * @param epoch2            epoch to calculate
     * @return                  days between two dates
     * @throws ParseException   date string parse error
     */
    public static int daysBetween(long epoch1, long epoch2) throws ParseException
    {
        TimeZone dtTimeZone = TimeZone.getTimeZone(TIMEZONE_ID_UTC);
        Calendar calendar1 = Calendar.getInstance(dtTimeZone);
        calendar1.setTimeInMillis(epoch1);        
        Calendar calendar2 = Calendar.getInstance(dtTimeZone);
        calendar2.setTimeInMillis(epoch2);   
        return daysBetween(calendar1, calendar2);        
    }
    
    /**
     * Calculate days between two epoch dates.
     * 
     * @param epochSecond1      epoch seconds
     * @param epochSecond2      epoch seconds
     * @return                  days between two dates
     * @throws ParseException   date string parse error
     */
    public static int daysBetweenEpochSecond(long epochSecond1, long epochSecond2) throws ParseException
    {
        long epoch1 = epochSecond1 * ONE_SECOND_MILLIS;
        long epoch2 = epochSecond2 * ONE_SECOND_MILLIS;
        return daysBetween(epoch1, epoch2);        
    }
    
}
