package com.peony.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@UtilityClass
public class DateUtils {

    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String format(LocalDateTime localDateTime) {
        return format(localDateTime, DATE_FORMAT);
    }

    public String format(LocalDateTime localDateTime, String dateFormat) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public LocalDateTime parse(String dateString) {
        return parse(dateString, DATE_FORMAT);
    }

    public LocalDateTime parse(String dateString, String pattern) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将String类型转换成LocalDate
     */
    public LocalDate parseDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * 获得当天是当前季度的第几天
     */
    public long getQuarterDay(LocalDate date) {
        return Math.abs(ChronoUnit.DAYS.between(quarterStart(0), date));
    }

    /**
     * 获取某季度的开始日期
     * 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月
     *
     * @param offset 0本季度，1下个季度，-1上个季度，依次类推
     */
    public LocalDate quarterStart(int offset) {
        final LocalDate date = LocalDate.now().plusMonths(offset * 3);
        //当月
        int month = date.getMonth().getValue();
        int start;
        //第一季度
        if (month >= 2 && month <= 4) {
            start = 2;

        } else if (month >= 5 && month <= 7) {
            //第二季度
            start = 5;
        } else if (month >= 8 && month <= 10) {
            //第三季度
            start = 8;
        } else if (month >= 11) {
            //第四季度
            start = 11;
        } else {
            start = 11;
            month = 13;
        }
        return date.plusMonths(start - month).with(TemporalAdjusters.firstDayOfMonth());
    }

}
