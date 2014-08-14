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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//As recommended by
//http://joesapps.blogspot.com/2011/02/managing-date-and-time-data-in-android.html

public class SkavaUploaderDateDisplayFormat {

	// cannot be instantiated
	private SkavaUploaderDateDisplayFormat(){}

	
	public static final String DATE_ONLY = "MMMM d, yyyy";
	public static final String DATE_TIME = "MMMM d, yyyy 'at' HH:mm";
	public static final String TIME_ONLY = "HH:mm:ss";

	
	public static String getFormattedDate(Calendar calendar){
		return getFormattedDate(DATE_ONLY, calendar.getTime());
	}

	public static String getFormattedDate(Date date){
		return getFormattedDate(DATE_ONLY, date);
	}
	
	public static String getFormattedDate(String format, Calendar calendar){
		return getFormattedDate(format, calendar.getTime());
	}
	
	public static String getFormattedDate(String format, Date d){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(d);
	}
	
}
