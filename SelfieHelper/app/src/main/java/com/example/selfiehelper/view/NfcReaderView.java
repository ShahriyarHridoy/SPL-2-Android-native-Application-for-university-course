package com.example.selfiehelper.view;

import net.grandcentrix.thirtyinch.TiView;
import net.grandcentrix.thirtyinch.callonmainthread.CallOnMainThread;

public interface NfcReaderView extends TiView {

    @CallOnMainThread
    void intentToPlayStreamActivity(String ipAddress);

    @CallOnMainThread
    void showNfcError();

    @CallOnMainThread
    void showNfcResult(String ipAddress);
}
