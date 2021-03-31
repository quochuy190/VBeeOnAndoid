package com.vn.vbeeon.utils.discovery;

import android.os.Handler;

/* renamed from: com.google.android.tv.support.remote.discovery.DiscoveryAgent */
/* compiled from: Unknown */
abstract class DiscoveryAgent {

    /* renamed from: com.google.android.tv.support.remote.discovery.DiscoveryAgent$Listener */
    /* compiled from: Unknown */
    public static abstract class Listener {
        public abstract void onDeviceFound(DeviceInfo deviceInfo);

        public abstract void onDeviceLost(DeviceInfo deviceInfo);

        public abstract void onDiscoveryStarted();

        public abstract void onDiscoveryStopped();

        public abstract void onStartDiscoveryFailed(int i);
    }

    DiscoveryAgent() {
    }

    public void destroy() {
    }

    public abstract void startDiscovery(Listener listener, Handler handler);

    public abstract void stopDiscovery();
}
