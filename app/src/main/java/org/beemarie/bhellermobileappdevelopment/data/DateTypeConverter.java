package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {
    @TypeConverter
    public static Date longToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToLong(Date value) {
        return value == null ? null : value.getTime();
    }

}
