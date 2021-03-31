package com.vn.vbeeon.utils.discovery;

import android.content.Context;
import android.os.Handler;

import com.vn.vbeeon.utils.mdns.DeviceScanner;
import com.vn.vbeeon.utils.mdns.MdnsDeviceScanner;
import com.vn.vbeeon.utils.mdns.NetworkDevice;


/* renamed from: com.google.android.tv.support.remote.discovery.LegacyNsdAgent */
/* compiled from: Unknown */
final class LegacyNsdAgent extends DiscoveryAgent {
    private static final boolean DEBUG = false;
    private static final String TAG = "AtvRemote.LegacyNsdAgnt";
    private final Context mContext;
    private LegacyListener mLocalListener;
    private final MdnsDeviceScanner mScanner;
    private final String mServiceType;

    /* renamed from: com.google.android.tv.support.remote.discovery.LegacyNsdAgent$LegacyListener */
    /* compiled from: Unknown */
    public class LegacyListener implements DeviceScanner.Listener {
        private final DiscoveryAgent.Listener mListener;
        private final MdnsDeviceScanner mScanner;

        public LegacyListener(DiscoveryAgent.Listener listener, MdnsDeviceScanner mdnsDeviceScanner) {
            this.mListener = listener;
            this.mScanner = mdnsDeviceScanner;
        }

        private boolean validDevice(NetworkDevice networkDevice) {
            return (networkDevice == null && (networkDevice.getServiceType() == null || networkDevice.getServiceName() == null || networkDevice.getHost() == null)) ? false : true;
        }

        public void onAllDevicesOffline() {
            for (NetworkDevice onDeviceOffline : this.mScanner.getDevices()) {
                onDeviceOffline(onDeviceOffline);
            }
        }

        public void onDeviceOffline(NetworkDevice networkDevice) {
            if (validDevice(networkDevice)) {
                this.mListener.onDeviceLost(LegacyNsdAgent.convert(networkDevice));
            }
        }

        public void onDeviceOnline(NetworkDevice networkDevice) {
            if (validDevice(networkDevice)) {
                this.mListener.onDeviceFound(LegacyNsdAgent.convert(networkDevice));
            }
        }

        public void onDeviceStateChanged(NetworkDevice networkDevice) {
            if (validDevice(networkDevice)) {
                this.mListener.onDeviceFound(LegacyNsdAgent.convert(networkDevice));
            }
        }

        public void onScanStateChanged(int i) {
            switch (i) {
                case 0:
                    this.mListener.onDiscoveryStopped();
                    return;
                case 1:
                    this.mListener.onDiscoveryStarted();
                    return;
                case 2:
                    LegacyNsdAgent.this.stopDiscovery();
                    return;
                default:
                    return;
            }
        }
    }

    LegacyNsdAgent(Context context, String str) {
        this.mContext = context;
        this.mServiceType = str + "local.";
        mScanner = new MdnsDeviceScanner(this.mContext, this.mServiceType);
    }

    /* access modifiers changed from: private */
    public static WifiDeviceInfo convert(NetworkDevice networkDevice) {
        return new WifiDeviceInfo(networkDevice.getHost(), networkDevice.getPort(), networkDevice.getServiceType(), networkDevice.getServiceName(), networkDevice.getTxtEntries());
    }

    public final void startDiscovery(DiscoveryAgent.Listener listener, Handler handler) {
        if (this.mLocalListener != null) {
            stopDiscovery();
        }
        this.mLocalListener = new LegacyListener(listener, this.mScanner);
        this.mScanner.addListener(this.mLocalListener);
        this.mScanner.startScan();
    }

    public final void stopDiscovery() {
        if (this.mLocalListener != null) {
            this.mScanner.stopScan();
            this.mScanner.removeListener(this.mLocalListener);
            this.mScanner.clearDevices();
            this.mLocalListener = null;
        }
    }
}
