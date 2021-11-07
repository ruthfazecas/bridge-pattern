package com.company;

import com.company.domain.Device;
import com.company.domain.Radio;
import com.company.domain.TV;

public class Main {

    public static void main(String[] args) {

        Device tv = new TV();
        Device radio = new Radio();

        Remote remote=new Remote(tv);

        remote.channelUp();
        remote.volumeUp();

    }
}
