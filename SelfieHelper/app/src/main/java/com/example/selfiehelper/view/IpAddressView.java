package com.example.selfiehelper.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface IpAddressView extends TiView {

    @CallOnMainThread
    void setIpAddress(String ipAddress);
}
