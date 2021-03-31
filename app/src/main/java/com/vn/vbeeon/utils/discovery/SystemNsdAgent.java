package com.vn.vbeeon.utils.discovery;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdServiceInfo;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.google.android.tv.support.remote.discovery.SystemNsdAgent */
/* compiled from: Unknown */
final class SystemNsdAgent extends DiscoveryAgent {
    private static final boolean DEBUG = false;
    private static final String TAG = SystemNsdAgent.class.getSimpleName();
    /* access modifiers changed from: private */
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private NsdDiscoveryListener mLocalListener;
    private final NsdManager mNsdManager;
    private final String mServiceType;

    /* renamed from: com.google.android.tv.support.remote.discovery.SystemNsdAgent$NsdDiscoveryListener */
    /* compiled from: Unknown */
    private class NsdDiscoveryListener implements DiscoveryListener {
        /* access modifiers changed from: private */
        public final Listener mListener;

        private NsdDiscoveryListener(Listener listener) {
            this.mListener = listener;
        }

        public void onDiscoveryStarted(String str) {
            this.mListener.onDiscoveryStarted();
        }

        public void onDiscoveryStopped(String str) {
            this.mListener.onDiscoveryStopped();
        }

        public void onServiceFound(final NsdServiceInfo nsdServiceInfo) {
            SystemNsdAgent.this.mExecutorService.submit(new Runnable() {
                public void run() {
                    SystemNsdAgent.this.resolveService(nsdServiceInfo, new ResolveListener() {
                        public void onResolveFailed(DeviceInfo deviceInfo, int i) {
                        }

                        public void onServiceResolved(DeviceInfo deviceInfo) {
                            NsdDiscoveryListener.this.mListener.onDeviceFound(deviceInfo);
                        }
                    });
                }
            });
        }

        public void onServiceLost(final NsdServiceInfo nsdServiceInfo) {
            SystemNsdAgent.this.mExecutorService.submit(new Runnable() {
                public void run() {
                    SystemNsdAgent.this.resolveService(nsdServiceInfo, new ResolveListener() {
                        public void onResolveFailed(DeviceInfo deviceInfo, int i) {
                        }

                        public void onServiceResolved(DeviceInfo deviceInfo) {
                            NsdDiscoveryListener.this.mListener.onDeviceLost(deviceInfo);
                        }
                    });
                }
            });
        }

        public void onStartDiscoveryFailed(String str, int i) {
            this.mListener.onStartDiscoveryFailed(i);
        }

        public void onStopDiscoveryFailed(String str, int i) {
        }
    }

    /* renamed from: com.google.android.tv.support.remote.discovery.SystemNsdAgent$NsdResolveListener */
    /* compiled from: Unknown */
    private class NsdResolveListener implements NsdManager.ResolveListener {
        private final ResolveListener mListener;

        private NsdResolveListener(ResolveListener resolveListener) {
            this.mListener = resolveListener;
        }

        public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
            this.mListener.onResolveFailed(null, i);
        }

        public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
            this.mListener.onServiceResolved(SystemNsdAgent.this.convert(nsdServiceInfo));
        }
    }

    /* renamed from: com.google.android.tv.support.remote.discovery.SystemNsdAgent$ResolveListener */
    /* compiled from: Unknown */
    private interface ResolveListener {
        void onResolveFailed(DeviceInfo deviceInfo, int i);

        void onServiceResolved(DeviceInfo deviceInfo);
    }

    public SystemNsdAgent(Context context, String str) {
        this.mServiceType = str;
        this.mNsdManager = (NsdManager) context.getSystemService("servicediscovery");
    }

    /* access modifiers changed from: private */
    public WifiDeviceInfo convert(NsdServiceInfo nsdServiceInfo) {
        return new WifiDeviceInfo(nsdServiceInfo.getHost(), nsdServiceInfo.getPort(), nsdServiceInfo.getServiceType(), nsdServiceInfo.getServiceName());
    }

    /* access modifiers changed from: 0000 */
    public final void resolveService(NsdServiceInfo nsdServiceInfo, ResolveListener resolveListener) {
        this.mNsdManager.resolveService(nsdServiceInfo, new NsdResolveListener(resolveListener));
    }

    public void startDiscovery(Listener listener, Handler handler) {
        if (this.mLocalListener != null) {
            stopDiscovery();
        }
        this.mLocalListener = new NsdDiscoveryListener(listener);
        this.mNsdManager.discoverServices(this.mServiceType, 1, this.mLocalListener);
    }

    public void stopDiscovery() {
        if (this.mLocalListener != null) {
            this.mNsdManager.stopServiceDiscovery(this.mLocalListener);
            this.mLocalListener = null;
        }
    }
}
