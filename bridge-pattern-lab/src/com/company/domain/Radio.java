package com.company.domain;

public class Radio implements Device {

    private int frequency;
    private int volume;
    private boolean enabled;

    public Radio() {
        frequency =0;
        volume=0;
        enabled=false;
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
        return frequency;
    }

    @Override
    public void setChannel(int channel) {
        frequency = channel;
    }
}
