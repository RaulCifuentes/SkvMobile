/*
 * Copyright (c) 2011 Joseph Fernandez <joefernandez.apps@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.metric.skava.uploader.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//As recommended by
//http://joesapps.blogspot.com/2011/02/managing-date-and-time-data-in-android.html

public class SkavaUploaderDateDataFormat {
	
	// cannot be instantiated
	private SkavaUploaderDateDataFormat(){}
	
    /**
     * <p>A date formatter for database fields.</p>
     */
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    

    public static long formatDateAsLong(Date date){
        return Long.parseLong(dateFormat.format(date));
    }

    public static Date getDateFromFormattedLong(long l){
        try {
            Date asDate = dateFormat.parse(String.valueOf(l));
            return asDate;

        } catch (ParseException e) {
            return null;
        }
    }

    public static long formatDateTimeAsLong(Calendar cal){
        return Long.parseLong(dateFormat.format(cal.getTime()));
    }

    public static Calendar getDateTimeFromFormattedLong(long l){
    	try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(String.valueOf(l)));
			return c;
			
		} catch (ParseException e) {
			return null;
		}
    }
    
    public static long getNowCalendarFormattedLong(){
    	return SkavaUploaderDateDataFormat.formatDateTimeAsLong(Calendar.getInstance());
    }

    public static long getNowDateFormattedLong(){
        return SkavaUploaderDateDataFormat.formatDateAsLong(new Date());
    }
}
