package com.company.domain;

public class TV implements Device {

    private boolean enabled;
    private int volume;
    private int channel;

    public TV() {
        enabled=false;
        volume=0;
        channel=0;

    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;

    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int percent) {
        volume = percent;
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
     this.channel= channel;
    }


}
