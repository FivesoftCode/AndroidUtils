package com.fivesoft.javautils.measure;

import androidx.annotation.NonNull;

import java.util.Locale;

public class BitrateMeasure extends FrequencyMeasure {

    public BitrateMeasure(int bufferSize, long interval) {
        super(bufferSize, interval);
    }

    public void countBit(){
        count(1);
    }

    public void countByte(){
        count(8);
    }

    public void countBits(long bits){
        count(bits);
    }

    public void countBytes(long bytes){
        countBits(bytes * 8);
    }

    public double getBitrate(){
        return getFrequency();
    }

    /**
     * Gets the average bitrate over the last interval in a human readable format.<br>
     * Example: 123.45 kbps
     * @return The average bitrate, or -1 if no bits have been recorded.
     */
    @NonNull
    public String getReadableBitrate(){
        return toReadableBitrate(getBitrate());
    }

    @NonNull
    private static String toReadableBitrate(double bitrate){
        if(bitrate == -1) {
            return "N/A";
        }
        else {
            if(bitrate < 1000){
                return String.format(Locale.US, "%.2f bps", bitrate);
            } else if(bitrate < 1000000){
                return String.format(Locale.US, "%.2f kbps", bitrate / 1000);
            } else if(bitrate < 1000000000){
                return String.format(Locale.US, "%.2f Mbps", bitrate / 1000000);
            } else {
                return String.format(Locale.US, "%.2f Gbps", bitrate / 1000000000);
            }
        }
    }

}
