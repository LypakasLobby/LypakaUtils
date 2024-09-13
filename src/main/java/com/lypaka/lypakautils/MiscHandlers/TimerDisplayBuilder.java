package com.lypaka.lypakautils.MiscHandlers;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimerDisplayBuilder {

    public static String makeTimeReadable (LocalDateTime expireTime) {

        LocalDateTime time = LocalDateTime.now();

        if (!expireTime.isAfter(time)) return "Expired";

        Duration duration = Duration.between(time, expireTime);
        return printSeconds(duration.getSeconds());

    }

    private static String printSeconds (long seconds) {

        StringBuilder timeString = new StringBuilder();
        if (timeString.length() != 0 || seconds >= 86400) timeString.append(seconds / 86400).append(" days, ");
        if (timeString.length() != 0 || seconds >= 3600) timeString.append(seconds % 86400 / 3600).append(" hours, ");
        if (timeString.length() != 0 || seconds >= 60) timeString.append(seconds % 3600 / 60).append(" minutes, ");
        timeString.append(seconds % 60).append(" seconds");
        return timeString.toString();

    }

}
