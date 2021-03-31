package com.vn.vbeeon.utils.mdns;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/* renamed from: com.google.android.tv.support.remote.mdns.MdnsDeviceScanner */
/* compiled from: Unknown */
public class MdnsDeviceScanner extends DeviceScanner {
    private static final boolean DEBUG = false;
    private static final int RECORD_EXPIRATION_INTERVAL_MS = 30000;
    private static final int RECORD_EXPIRATION_INTERVAL_MS_NOISE = 15000;
    private static final int REFRESH_INTERVAL_MS = 5000;
    private static final int REPEAT_SCAN = 1;
    private static final int REPEAT_SCAN_DELAY = 1000;
    private static final String TAG = "AtvRemote.MdnsDvcScnner";
    private final Map<String, DeviceRecord> mDeviceRecords = new HashMap();
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    if (MdnsDeviceScanner.this.mRefreshThread != null) {
                        MdnsDeviceScanner.this.stopScanInternal();
                    }
                    MdnsDeviceScanner.this.repeatStartScanInternal();
                    return;
                default:
                    return;
            }
        }
    };
    private final List<MdnsClient> mMdnsClients = new ArrayList();
    /* access modifiers changed from: private */
    public Thread mRefreshThread;
    private final String mRemoteServiceType;
    private boolean mShouldStop;

    /* renamed from: com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord */
    /* compiled from: Unknown */
    private class DeviceRecord {
        NetworkDevice device;
        boolean invalid;
        long lastUpdateTimestamp = SystemClock.elapsedRealtime();
        final long mExpirationInterval;
        long timeToLive;

        DeviceRecord(NetworkDevice networkDevice, long j) {
            this.device = networkDevice;
            this.timeToLive = j;
            this.mExpirationInterval = ((long) (new Random().nextDouble() * 15000.0d)) + Math.min(1000 * j, 30000);
        }

        /* access modifiers changed from: 0000 */
        public boolean isExpired(long j) {
            return !(((j - this.lastUpdateTimestamp) > this.mExpirationInterval ? 1 : ((j - this.lastUpdateTimestamp) == this.mExpirationInterval ? 0 : -1)) < 0);
        }
    }

    public MdnsDeviceScanner(Context context, String str) {
        super(context);
        this.mRemoteServiceType = str;
    }

    private void logResponse(MdnsPacketWriter.MdnsResponse mdnsResponse) {
    }

    private void postRepeatScan() {
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004c, code lost:
        if (r1 != null) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004e, code lost:
        if (r0 != null) goto L_0x007a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0050, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0076, code lost:
        notifyDeviceOffline(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007a, code lost:
        notifyDeviceOnline(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processResponse(MdnsPacketWriter.MdnsResponse r10) {
        /*
            r9 = this;
            r6 = 0
            java.lang.String r7 = r10.getServiceInstanceName()
            java.util.Map<java.lang.String, com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord> r8 = r9.mDeviceRecords
            monitor-enter(r8)
            java.util.List r0 = r10.getInet4Addresses()     // Catch:{ all -> 0x0073 }
            if (r0 != 0) goto L_0x0010
        L_0x000e:
            monitor-exit(r8)     // Catch:{ all -> 0x0073 }
            return
        L_0x0010:
            boolean r1 = r0.isEmpty()     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x000e
            r1 = 0
            java.lang.Object r2 = r0.get(r1)     // Catch:{ all -> 0x0073 }
            java.net.Inet4Address r2 = (java.net.Inet4Address) r2     // Catch:{ all -> 0x0073 }
            com.google.android.tv.support.remote.mdns.NetworkDevice r0 = new com.google.android.tv.support.remote.mdns.NetworkDevice     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = r10.getServiceName()     // Catch:{ all -> 0x0073 }
            java.lang.String r3 = r10.getServiceInstanceName()     // Catch:{ all -> 0x0073 }
            int r4 = r10.getServicePort()     // Catch:{ all -> 0x0073 }
            java.util.List r5 = r10.getTextStrings()     // Catch:{ all -> 0x0073 }
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0073 }
            java.util.Map<java.lang.String, com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord> r1 = r9.mDeviceRecords     // Catch:{ all -> 0x0073 }
            java.lang.Object r1 = r1.get(r7)     // Catch:{ all -> 0x0073 }
            com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord r1 = (com.google.android.p004tv.support.remote.mdns.MdnsDeviceScanner.DeviceRecord) r1     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0051
            r1 = r6
        L_0x003d:
            java.util.Map<java.lang.String, com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord> r2 = r9.mDeviceRecords     // Catch:{ all -> 0x0073 }
            com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord r3 = new com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord     // Catch:{ all -> 0x0073 }
            long r4 = r10.getTimeToLive()     // Catch:{ all -> 0x0073 }
            r3.<init>(r0, r4)     // Catch:{ all -> 0x0073 }
            r2.put(r7, r3)     // Catch:{ all -> 0x0073 }
            monitor-exit(r8)     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0076
        L_0x004e:
            if (r0 != 0) goto L_0x007a
        L_0x0050:
            return
        L_0x0051:
            com.google.android.tv.support.remote.mdns.NetworkDevice r2 = r1.device     // Catch:{ all -> 0x0073 }
            boolean r2 = r0.equals(r2)     // Catch:{ all -> 0x0073 }
            if (r2 != 0) goto L_0x0063
            com.google.android.tv.support.remote.mdns.NetworkDevice r1 = r1.device     // Catch:{ all -> 0x0073 }
            java.util.Map<java.lang.String, com.google.android.tv.support.remote.mdns.MdnsDeviceScanner$DeviceRecord> r2 = r9.mDeviceRecords     // Catch:{ all -> 0x0073 }
            r2.remove(r7)     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x003d
            goto L_0x003d
        L_0x0063:
            boolean r2 = r1.invalid     // Catch:{ all -> 0x0073 }
            if (r2 == 0) goto L_0x006c
        L_0x0067:
            r9.notifyDeviceStateChanged(r0)     // Catch:{ all -> 0x0073 }
            monitor-exit(r8)     // Catch:{ all -> 0x0073 }
            return
        L_0x006c:
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0073 }
            r1.lastUpdateTimestamp = r2     // Catch:{ all -> 0x0073 }
            goto L_0x0067
        L_0x0073:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0073 }
            throw r0
        L_0x0076:
            r9.notifyDeviceOffline(r1)
            goto L_0x004e
        L_0x007a:
            r9.notifyDeviceOnline(r0)
            goto L_0x0050
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.p004tv.support.remote.mdns.MdnsDeviceScanner.processResponse(com.google.android.tv.support.remote.mdns.MdnsResponse):void");
    }

    /* access modifiers changed from: private */
    public void refreshLoop() {
        while (!this.mShouldStop) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                if (this.mShouldStop) {
                    return;
                }
            }
            synchronized (this.mDeviceRecords) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator it = this.mDeviceRecords.entrySet().iterator();
                while (it.hasNext()) {
                    DeviceRecord deviceRecord = (DeviceRecord) ((Entry) it.next()).getValue();
                    if (deviceRecord.isExpired(elapsedRealtime)) {
                        final NetworkDevice networkDevice = deviceRecord.device;
                        getUiHandler().post(new Runnable() {
                            public void run() {
                                MdnsDeviceScanner.this.notifyDeviceOffline(networkDevice);
                            }
                        });
                        it.remove();
                        postRepeatScan();
                    }
                }
            }
        }
    }

    public void clearDevices() {
        boolean z = false;
        synchronized (this.mDeviceRecords) {
            if (!this.mDeviceRecords.isEmpty()) {
                this.mDeviceRecords.clear();
                z = true;
            }
        }
        if (z) {
            notifyAllDevicesOffline();
        }
    }

    public List<NetworkDevice> getDevices() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mDeviceRecords) {
            for (DeviceRecord deviceRecord : this.mDeviceRecords.values()) {
                arrayList.add(deviceRecord.device);
            }
        }
        return arrayList;
    }

    public void markDeviceInvalid(String str) {
        NetworkDevice networkDevice;
        synchronized (this.mDeviceRecords) {
            DeviceRecord deviceRecord = (DeviceRecord) this.mDeviceRecords.get(str);
            if (deviceRecord == null) {
                networkDevice = null;
            } else {
                deviceRecord.lastUpdateTimestamp = SystemClock.elapsedRealtime();
                deviceRecord.invalid = true;
                networkDevice = deviceRecord.device;
            }
        }
        if (networkDevice != null) {
            notifyDeviceOffline(networkDevice);
        }
    }

    /* access modifiers changed from: protected */
    public void startScanInternal(List<NetworkInterface> list) {
        if (!list.isEmpty()) {
            for (NetworkInterface networkInterface : list) {
                MdnsClient r2 = new MdnsClient(this.mRemoteServiceType, networkInterface) {
                    /* access modifiers changed from: protected */
                    public void onResponseReceived(MdnsPacketWriter.MdnsResponse mdnsResponse) {
                        MdnsDeviceScanner.this.processResponse(mdnsResponse);
                    }
                };
                try {
                    r2.start();
                    this.mMdnsClients.add(r2);
                } catch (IOException e) {
                    Log.w(TAG, "Couldn't start MDNS client for " + networkInterface);
                }
            }
            this.mShouldStop = false;
            this.mRefreshThread = new Thread(new Runnable() {
                public void run() {
                    MdnsDeviceScanner.this.refreshLoop();
                }
            });
            this.mRefreshThread.start();
        }
    }

    /* access modifiers changed from: protected */
    public void stopScanInternal() {
        if (!this.mMdnsClients.isEmpty()) {
            for (MdnsClient stop : this.mMdnsClients) {
                stop.stop();
            }
            this.mMdnsClients.clear();
        }
        this.mShouldStop = true;
        if (this.mRefreshThread != null) {
            while (true) {
                try {
                    this.mRefreshThread.interrupt();
                    this.mRefreshThread.join();
                    break;
                } catch (InterruptedException e) {
                }
            }
            this.mRefreshThread = null;
        }
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
    }
}
