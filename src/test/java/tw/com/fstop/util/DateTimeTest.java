
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

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class DateTimeTest
{
    @Before    
    public void setup() 
    {
    }
    
    @After
    public void tearDown() 
    {
    }

    @Test
    public void testGetEpoch() throws ParseException
    {
        String tzID = DateTimeUtil.getCurrentTimeZoneID();
        assertNotNull(tzID);
        
        int tzOffset = DateTimeUtil.getCurrentTimeZoneOffset();
        System.out.println(tzOffset);
        assertNotNull(tzOffset);

        tzOffset = DateTimeUtil.getCurrentTimeZoneOffsetHour();
        System.out.println(tzOffset);
        assertThat(tzOffset).isEqualTo(8);

        tzOffset = DateTimeUtil.getLocalTimeZoneOffset("Asia/Taipei");
        System.out.println(tzOffset);
        assertNotNull(tzOffset);
        
        tzOffset = DateTimeUtil.getLocalTimeZoneOffsetHour("Asia/Taipei");
        System.out.println(tzOffset);
        assertThat(tzOffset).isEqualTo(8);
        
        int dlsOffset = DateTimeUtil.getCurrentDaylightSavingOffset();
        System.out.println(dlsOffset);
        assertNotNull(dlsOffset);
        
        dlsOffset = DateTimeUtil.getCurrentDaylightSavingOffsetHour();
        System.out.println(dlsOffset);
        assertNotNull(dlsOffset);

        dlsOffset = DateTimeUtil.getLocalDaylightSavingOffset("Asia/Taipei");
        System.out.println(dlsOffset);
        assertNotNull(dlsOffset);
        
        dlsOffset = DateTimeUtil.getLocalDaylightSavingOffsetHour("Asia/Taipei");
        System.out.println(dlsOffset);
        assertNotNull(dlsOffset);
        
        long t = DateTimeUtil.getEpochSecond(1970, 1, 1, 0, 0, 0);
        assertThat(t).isZero();

        t = DateTimeUtil.getEpochSecond("19700101000000");
        assertThat(t).isZero();
        
        t = DateTimeUtil.getCurrentEpoch();
        System.out.println(t);
        assertNotNull(t);
        
        long t1 = DateTimeUtil.getCurrentEpochSecond();
        assertNotNull(t1);
        
        long t2 = DateTimeUtil.getEpochSecond();
        assertNotNull(t2);
        
        assertThat(t1).isEqualTo(t2);
        
        t = DateTimeUtil.getNormalizedEpochSecond(3);
        System.out.println(t);
        assertThat(t % 3).isZero();
        
        String fmt = DateTimeUtil.epochSecondToDateString(0, "yyyy-MM-dd HH:mm:ss");
        System.out.println(fmt);
        assertThat(fmt).isEqualTo("1970-01-01 00:00:00");

        fmt = DateTimeUtil.epochSecondToCurrentDateString(0, "yyyy-MM-dd HH:mm:ss");
        System.out.println(fmt);
        assertThat(fmt).isEqualTo("1970-01-01 08:00:00");
        
        fmt = DateTimeUtil.getCurrentDateString("yyyy-MM-dd HH:mm:ss");
        System.out.println(fmt);
        assertNotNull(fmt);
        
        int y = DateTimeUtil.getCurrentYear();
        System.out.println(y);
        assertNotNull(y);
        
        int m = DateTimeUtil.getCurrentMonth();
        System.out.println(m);
        assertNotNull(m);

        int d = DateTimeUtil.getCurrentDay();
        System.out.println(d);
        assertNotNull(d);
        
        // Pacific/Tarawa +12
        int d1 = DateTimeUtil.getLocalDay("Pacific/Tarawa");
        System.out.println(d1);
        assertNotNull(d1);
        
        fmt = DateTimeUtil.epochToLocalDateString(1502114440291L, "yyyy-MM-dd HH:mm:ss", "Pacific/Tarawa");
        System.out.println(fmt);
        assertNotNull(fmt);

        fmt = DateTimeUtil.epochToLocalDateString(1502114440291L, "yyyy-MM-dd HH:mm:ss", "Asia/Taipei");
        System.out.println(fmt);
        assertNotNull(fmt);

        fmt = DateTimeUtil.epochToLocalDateString(1502114440291L, "yyyy-MM-dd HH:mm:ss", "UTC");
        System.out.println(fmt);
        assertNotNull(fmt);

        fmt = DateTimeUtil.epochSecondToLocalDateString(1502115795L, "yyyy-MM-dd HH:mm:ss", "Pacific/Tarawa");
        System.out.println(fmt);
        assertNotNull(fmt);

        fmt = DateTimeUtil.epochSecondToLocalDateString(1502115795L, "yyyy-MM-dd HH:mm:ss", "Asia/Taipei");
        System.out.println(fmt);
        assertNotNull(fmt);

        fmt = DateTimeUtil.epochSecondToLocalDateString(1502115795L, "yyyy-MM-dd HH:mm:ss", "UTC");
        System.out.println(fmt);
        assertNotNull(fmt);
        
        t = DateTimeUtil.dateStringToEpoch("2017-08-07 22:00:40", "yyyy-MM-dd HH:mm:ss", "UTC", "Asia/Taipei");
        System.out.println(t);
        assertThat(t).isEqualTo(1502114440000L);
        
        t = DateTimeUtil.dateStringToEpoch("2017-08-07 22:00:40", "yyyy-MM-dd HH:mm:ss", "Asia/Taipei", "UTC");
        System.out.println(t);
        assertThat(t).isEqualTo(1502143240000L);        

        t = DateTimeUtil.dateStringToEpoch("2017-08-07 22:00:40", "yyyy-MM-dd HH:mm:ss", "Asia/Taipei", "Asia/Taipei");
        System.out.println(t);
        assertThat(t).isEqualTo(1502114440000L);

        t = DateTimeUtil.dateStringToEpoch("2017-08-07 22:00:40", "yyyy-MM-dd HH:mm:ss", "UTC", "UTC");
        System.out.println(t);
        assertThat(t).isEqualTo(1502143240000L);        
        
        t = DateTimeUtil.dateStringToEpoch("2017-08-07 22:00:40", "yyyy-MM-dd HH:mm:ss", "Asia/Taipei");
        System.out.println(t);
        assertThat(t).isEqualTo(1502114440000L);

        t = DateTimeUtil.dateStringToEpoch("20170807", "yyyyMMdd", "Asia/Taipei");
        System.out.println(t);
        assertThat(t).isEqualTo(1502035200000L);
        
        t = DateTimeUtil.dateStringToEpoch("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss", "Asia/Taipei");
        System.out.println(t);
        assertThat(t).isEqualTo(0L); 

        t = DateTimeUtil.dateStringToEpochSecond("2017-08-07 22:00:40", "yyyy-MM-dd HH:mm:ss", "Asia/Taipei");
        System.out.println(t);
        assertThat(t).isEqualTo(1502114440L);

        t = DateTimeUtil.dateStringToEpochSecond("20170807", "yyyyMMdd", "Asia/Taipei");
        System.out.println(t);
        assertThat(t).isEqualTo(1502035200L);
        
        //2016 is leap year
        d = DateTimeUtil.daysBetween("20160228", "20160301", "yyyyMMdd", "Asia/Taipei", "Asia/Taipei");
        System.out.println(d);
        assertThat(d).isEqualTo(2);

        d = DateTimeUtil.daysBetween("20160228", "20160301", "yyyyMMdd");
        System.out.println(d);
        assertThat(d).isEqualTo(2);

        d = DateTimeUtil.daysBetween("2016-02-28", "2016-03-01", "yyyy-MM-dd");
        System.out.println(d);
        assertThat(d).isEqualTo(2);

        d = DateTimeUtil.daysBetween("20160228", "20170228", "yyyyMMdd");
        System.out.println(d);
        assertThat(d).isEqualTo(366);

        d = DateTimeUtil.daysBetween("20170807", "20160807", "yyyyMMdd");
        System.out.println(d);
        assertThat(d).isEqualTo(365);
        
        d = DateTimeUtil.daysBetween(1502035200000L, 1470499200000L);
        System.out.println(d);
        assertThat(d).isEqualTo(365);
        
    }
    
}
