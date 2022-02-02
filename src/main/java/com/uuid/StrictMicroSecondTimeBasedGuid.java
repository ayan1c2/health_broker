package com.uuid;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class StrictMicroSecondTimeBasedGuid
{
    private final static Logger logger = Logger
            .getLogger(StrictMicroSecondTimeBasedGuid.class);

    private static final long MICRO_IN_MILL = 1000;
    private static final long NANO_IN_MICRO = 1000;

    private static long baseNanoTime;
    private static long baseTimeInMicro;
    private static long lastGuid;

    static
    {
        /*
         * Nanosecond time's reference is not known, therfore following logic is
         * needed to calculate time in micro without knowing refrence point of
         * nano time*
         */
        baseNanoTime = System.nanoTime();
        baseTimeInMicro = System.currentTimeMillis() * MICRO_IN_MILL;
        lastGuid = baseTimeInMicro;
    }

    public static synchronized Long newGuid()
    {
        long newGuid;

        while ((newGuid = calNewTimeInMicro()) <= lastGuid)
        {
            /** we have to check for this log, we don't want to see log. */

            logger.debug("wait of 10-microsecond is introduced to get new guid");

            try
            {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e)
            {
                logger.error("Error", e);
            }
        }

        lastGuid = newGuid;
        return newGuid;
    }

    private static long calNewTimeInMicro()
    {
        return baseTimeInMicro + ((System.nanoTime() - baseNanoTime) / NANO_IN_MICRO);
    }
}
