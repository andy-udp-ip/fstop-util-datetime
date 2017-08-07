
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
import static org.junit.Assert.assertTrue;

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
    public void testGetEpoch()
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
        
        long t = DateTimeUtil.getUtcEpochSecond(1970, 1, 1, 0, 0, 0);
        assertThat(t).isZero();

        t = DateTimeUtil.getCurrentEpoch();
        System.out.println(t);
        assertNotNull(t);
        
        t = DateTimeUtil.getCurrentEpochSecond();
        System.out.println(t);
        assertNotNull(t);
        
        
    }
    
}
