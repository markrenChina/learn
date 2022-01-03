package com.ccand99.i18n;

import java.text.MessageFormat;
import java.util.Date;

/**
 * {@link java.text.MessageFormat} 示例
 */
public class MessageFormatDemo {

    public static void main(String[] args) {
        int planet = 7;
        String event = "a disturbance in the Force";

        MessageFormat messageFormat =
                new MessageFormat("At {1,time,long} on {1,date,full}, there was {2} on planet {0,number,integer}.");

        String result = messageFormat.format(new Object[]{planet, new Date(), event});

        System.out.println(result);
    }
}
