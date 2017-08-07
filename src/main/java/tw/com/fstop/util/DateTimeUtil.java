
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Class for process Date and Time, based on epoch calculation.
 * In case to avoid class naming collision with other third party class, this class is rename from DateTime to DateTimeUtil. 
 *
 * 23:59 is the last minute of the day and 00:00 is the first minute of the next day. 
 * Thus, 23:59 on Dec 31, 1999 < 00:00 on Jan 1, 2000 < 00:01 on Jan 1, 2000.
 * Midnight also belongs to "am", and noon belongs to "pm", 
 * so on the same day, 12:00 am (midnight) < 12:01 am, and 12:00 pm (noon) < 12:01 pm
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
 * @since 1.0
 */
public class DateTimeUtil
{
    
    static String TIMEZONE_ID_UTC = "UTC";
    
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
        return getCurrentTimeZoneOffset() / 1000 / 3600;
    }
    
    /**
     * Convert current time to EPOCH without milliseconds.
     * @return      epoch millisecond
     */
    public static long getCurrentEpoch()
    {
        return System.currentTimeMillis();
    }
    
    /**
     * Convert current time to EPOCH without seconds.
     * Epoch is always UTC based, no need to convert.
     * 
     * @return      epoch second
     */
    public static long getCurrentEpochSecond()
    {
        return (getCurrentEpoch()/1000);
    }
        
    /**
     * Get current system timezone offset value.
     * @param tzID  timezone id
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
     * @param timezoneID
     * @return              timezone offset value in hour
     */
    public static int getLocalTimeZoneOffsetHour(String timezoneID)
    {
        //milliseconds -> seconds -> hour
        return getLocalTimeZoneOffset(timezoneID) / 1000 / 3600;
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
        return getCurrentDaylightSavingOffset() / 1000 / 3600;
    }

    /**
     * Input timezone id and returns daylight saving offset value.
     * @param timezoneID
     * @return              daylight saving offset value in millisecond
     */
    public static int getLocalDaylightSavingOffset(String timezoneID)
    {
        TimeZone timeZone = TimeZone.getTimeZone(timezoneID);
        Calendar calendar = Calendar.getInstance();
        int dlsOffset = calendar.get(Calendar.DST_OFFSET);
        return dlsOffset;
    }
    
    /**
     * Input timezone id and returns daylight saving offset value.
     * @param timezoneID
     * @return              daylight saving offset value in hour
     */
    public static int getLocalDaylightSavingOffsetHour(String timezoneID)
    {
      //milliseconds -> seconds -> hour
        return getLocalDaylightSavingOffset(timezoneID) / 1000 / 3600;
    }
    
    
    /**
     * Input year, month, day, hour, minute, second and return UTC epoch seconds.
     * 
     * @param year
     * @param month     Month value form 1 to 12
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return          epoch seconds
     */
    public static long getUtcEpochSecond(int year, int month, int day, int hour, int minute, int second)
    {
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_ID_UTC);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.clear();   //clears unused field values
        int m = month - 1;  //adjust month value from one base to zero base
        calendar.set(year, m, day, hour, minute , second);
        long secondsSinceEpoch = calendar.getTimeInMillis()/1000L;
        return secondsSinceEpoch;
    }          

}
