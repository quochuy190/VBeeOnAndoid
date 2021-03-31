package com.vn.vbeeon.utils.mdns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.android.tv.support.remote.mdns.DeviceScanner */
/* compiled from: Unknown */
public abstract class DeviceScanner {
    private static final boolean DEBUG = false;
    public static final int SCAN_STARTED = 1;
    public static final int SCAN_STOPPED = 0;
    public static final int SCAN_SUSPENDED_NETWORK_ERROR = 2;
    private static final String TAG = "AtvRemote.DvcScanner";
    private ConnectivityChangeReceiver mConnectivityChangeReceiver;
    /* access modifiers changed from: private */
    public final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private String mLatestBssid;
    private final List<Listener> mListeners;
    /* access modifiers changed from: private */
    public boolean mScanActive;
    private final AtomicBoolean mScanErrorLatch;
    /* access modifiers changed from: private */
    public boolean mScanErrorState;
    private boolean mScanning;
    private volatile boolean mShouldStopScan;
    private int mState = 0;
    private final Handler mUiHandler;
    private final WifiManager mWifiManager;

    /* renamed from: com.google.android.tv.support.remote.mdns.DeviceScanner$ConnectivityChangeReceiver */
    /* compiled from: Unknown */
    private class ConnectivityChangeReceiver extends BroadcastReceiver {
        private ConnectivityChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            boolean z = false;
            NetworkInfo activeNetworkInfo = DeviceScanner.this.mConnectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                z = true;
            }
            boolean access$200 = DeviceScanner.this.updateBssid();
            if (!z) {
                DeviceScanner.this.clearDevices();
            }
            if (DeviceScanner.this.mScanActive && access$200) {
                DeviceScanner.this.stopScanInit();
            }
            if (z) {
                DeviceScanner.this.startScanInit();
            } else if (!DeviceScanner.this.mScanErrorState) {
                DeviceScanner.this.reportNetworkErrorAsync();
            }
        }
    }

    /* renamed from: com.google.android.tv.support.remote.mdns.DeviceScanner$Listener */
    /* compiled from: Unknown */
    public interface Listener {
        void onAllDevicesOffline();

        void onDeviceOffline(NetworkDevice networkDevice);

        void onDeviceOnline(NetworkDevice networkDevice);

        void onDeviceStateChanged(NetworkDevice networkDevice);

        void onScanStateChanged(int i);
    }

    protected DeviceScanner(Context context) {
        this.mContext = context;
        this.mUiHandler = new Handler(Looper.getMainLooper());
        this.mListeners = new ArrayList();
        this.mScanErrorLatch = new AtomicBoolean();
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    private void registerForNetworkConnectivityChanges() {
        if (this.mConnectivityChangeReceiver == null) {
            this.mConnectivityChangeReceiver = new ConnectivityChangeReceiver();
            this.mContext.registerReceiver(this.mConnectivityChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    private List<NetworkInterface> selectNetworkInterfaces() {
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return arrayList;
            }
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback() && !networkInterface.isPointToPoint() && networkInterface.supportsMulticast()) {
                    for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                        if (address.getAddress() instanceof Inet4Address) {
                            arrayList.add(networkInterface);
                        }
                    }
                }
            }
            return arrayList;
        } catch (IOException e) {
            Log.d(TAG, "Exception while selecting network interface", e);
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void startScanInit() {
        this.mScanErrorState = false;
        this.mShouldStopScan = false;
        if (updateBssid() || !this.mScanActive) {
            this.mScanActive = true;
            startScanInternal(selectNetworkInterfaces());
        }
    }

    /* access modifiers changed from: private */
    public void stopScanInit() {
        this.mShouldStopScan = true;
        this.mScanActive = false;
        stopScanInternal();
    }

    private void unregisterForNetworkConnectivityChanges() {
        if (this.mConnectivityChangeReceiver != null) {
            try {
                this.mContext.unregisterReceiver(this.mConnectivityChangeReceiver);
            } catch (IllegalArgumentException e) {
            }
            this.mConnectivityChangeReceiver = null;
        }
    }

    /* access modifiers changed from: private */
    public boolean updateBssid() {
        boolean z = false;
        String str = null;
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            str = connectionInfo.getBSSID();
        }
        if (this.mLatestBssid == null || str == null || !this.mLatestBssid.equals(str)) {
            z = true;
        }
        if (z) {
            clearDevices();
        }
        this.mLatestBssid = str;
        return z;
    }

    public final void addListener(Listener listener) throws IllegalArgumentException {
        if (listener != null) {
            synchronized (this.mListeners) {
                if (!this.mListeners.contains(listener)) {
                    this.mListeners.add(listener);
                } else {
                    throw new IllegalArgumentException("the same listener cannot be added twice");
                }
            }
            return;
        }
        throw new IllegalArgumentException("listener cannot be null");
    }

    public abstract void clearDevices();

    /* access modifiers changed from: protected */
    public final List<Listener> cloneListenerList() {
        ArrayList arrayList = null;
        synchronized (this.mListeners) {
            if (!this.mListeners.isEmpty()) {
                arrayList = new ArrayList(this.mListeners);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final Context getContext() {
        return this.mContext;
    }

    public abstract List<NetworkDevice> getDevices();

    /* access modifiers changed from: protected */
    public final Handler getUiHandler() {
        return this.mUiHandler;
    }

    public abstract void markDeviceInvalid(String str);

    /* access modifiers changed from: protected */
    public final void notifyAllDevicesOffline() {
        final List<Listener> cloneListenerList = cloneListenerList();
        if (cloneListenerList != null) {
            this.mUiHandler.post(new Runnable() {
                public void run() {
                    for (Listener onAllDevicesOffline : cloneListenerList) {
                        onAllDevicesOffline.onAllDevicesOffline();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyDeviceOffline(final NetworkDevice networkDevice) {
        final List<Listener> cloneListenerList = cloneListenerList();
        if (cloneListenerList != null) {
            this.mUiHandler.post(new Runnable() {
                public void run() {
                    for (Listener onDeviceOffline : cloneListenerList) {
                        onDeviceOffline.onDeviceOffline(networkDevice);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyDeviceOnline(final NetworkDevice networkDevice) {
        final List<Listener> cloneListenerList = cloneListenerList();
        if (cloneListenerList != null) {
            this.mUiHandler.post(new Runnable() {
                public void run() {
                    for (Listener onDeviceOnline : cloneListenerList) {
                        onDeviceOnline.onDeviceOnline(networkDevice);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyDeviceStateChanged(final NetworkDevice networkDevice) {
        final List<Listener> cloneListenerList = cloneListenerList();
        if (cloneListenerList != null) {
            this.mUiHandler.post(new Runnable() {
                public void run() {
                    for (Listener onDeviceStateChanged : cloneListenerList) {
                        onDeviceStateChanged.onDeviceStateChanged(networkDevice);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyStateChanged(final int i) {
        if (this.mState != i) {
            this.mState = i;
            final List<Listener> cloneListenerList = cloneListenerList();
            if (cloneListenerList != null) {
                this.mUiHandler.post(new Runnable() {
                    public void run() {
                        for (Listener onScanStateChanged : cloneListenerList) {
                            onScanStateChanged.onScanStateChanged(i);
                        }
                    }
                });
            }
        }
    }

    public final void removeAllListeners() {
        synchronized (this.mListeners) {
            this.mListeners.clear();
        }
    }

    public final void removeListener(Listener listener) throws IllegalArgumentException {
        if (listener != null) {
            synchronized (this.mListeners) {
                this.mListeners.remove(listener);
            }
            return;
        }
        throw new IllegalArgumentException("listener cannot be null");
    }

    /* access modifiers changed from: protected */
    public void repeatStartScanInternal() {
        startScanInternal(selectNetworkInterfaces());
    }

    /* access modifiers changed from: protected */
    public final void reportNetworkError() {
        if (!this.mScanErrorState) {
            this.mScanErrorState = true;
            clearDevices();
            notifyStateChanged(2);
        }
    }

    /* access modifiers changed from: protected */
    public final void reportNetworkErrorAsync() {
        if (!this.mScanErrorLatch.getAndSet(true)) {
            this.mUiHandler.post(new Runnable() {
                public void run() {
                    DeviceScanner.this.reportNetworkError();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final boolean shouldStopScan() {
        return this.mShouldStopScan;
    }

    public final void startScan() {
        if (!this.mScanning) {
            this.mScanning = true;
            notifyStateChanged(1);
            registerForNetworkConnectivityChanges();
            startScanInit();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void startScanInternal(List<NetworkInterface> list);

    public final void stopScan() {
        if (this.mScanning) {
            unregisterForNetworkConnectivityChanges();
            stopScanInit();
            this.mUiHandler.removeCallbacksAndMessages(null);
            this.mScanning = false;
            notifyStateChanged(0);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void stopScanInternal();
}
