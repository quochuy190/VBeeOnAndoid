package com.vn.vbeeon.utils.discovery;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;

import androidx.collection.ArrayMap;

import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.tv.support.remote.discovery.Discoverer */
/* compiled from: Unknown */
public final class Discoverer {
    private static final boolean DEBUG = false;
    private static final int DEVICE_FOUND = 1;
    private static final int DEVICE_LOST = 2;
    private static final int DEVICE_NO_OP = 0;
    private static final int DEVICE_REPLACE = 3;
    private static final boolean ENABLE_BLUETOOTH_CLASSIC_DISCOVERY = true;
    private static final String FORCE_LEGACY_PROP = "prop.android.tv.force_legacy_discoverer";
    private static final String FORCE_SYSTEM_PROP = "prop.android.tv.force_system_discoverer";
    private static final String SERVICE_TYPE = "_androidtvremote._tcp.";
    private static final String TAG = "AtvRemote.Discoverer";
    private final ArrayMap<String, DeviceInfo> mBluetoothDevices = new ArrayMap<>();
    private final Context mContext;
    private final List<DiscoveryAgent> mDiscoveryAgents = new ArrayList();
    private final ArrayMap<String, WifiDeviceInfo> mIpToWifiDevices = new ArrayMap<>();
    private DiscoveryAgent.Listener mLocalListener;
    private final ArrayMap<String, DeviceInfo> mWifiDevices = new ArrayMap<>();

    /* renamed from: com.google.android.tv.support.remote.discovery.Discoverer$DiscoveryListener */
    /* compiled from: Unknown */
    public static abstract class DiscoveryListener {
        public abstract void onDeviceFound(DeviceInfo deviceInfo);

        public abstract void onDeviceLost(DeviceInfo deviceInfo);

        public abstract void onDeviceReplace(DeviceInfo deviceInfo, DeviceInfo deviceInfo2);

        public abstract void onDiscoveryStarted();

        public void onDiscoveryStopped() {
        }

        public abstract void onStartDiscoveryFailed(int i);
    }

    public Discoverer(Context context) {
        this.mContext = context;
        this.mDiscoveryAgents.addAll(getAgents(this.mContext));
    }

    static List<DiscoveryAgent> getAgents(Context context) {
        ArrayList arrayList = new ArrayList();
        boolean booleanValue = Boolean.valueOf(System.getProperty(FORCE_LEGACY_PROP, "false")).booleanValue();
        boolean booleanValue2 = Boolean.valueOf(System.getProperty(FORCE_SYSTEM_PROP, "false")).booleanValue();
        if (booleanValue && booleanValue2) {
            throw new IllegalStateException("You cannot force both Legacy and System Resolvers");
        }
        if (booleanValue) {
            arrayList.add(new LegacyNsdAgent(context, SERVICE_TYPE));
        } else if (!booleanValue2) {
            arrayList.add(new LegacyNsdAgent(context, SERVICE_TYPE));
        } else {
            arrayList.add(new SystemNsdAgent(context, SERVICE_TYPE));
        }
        if (VERSION.SDK_INT >= 15 && context.checkCallingOrSelfPermission("android.permission.BLUETOOTH_ADMIN") == PackageManager.PERMISSION_GRANTED) {
            arrayList.add(new BluetoothAgent(context));
            if (VERSION.SDK_INT >= 21) {
                arrayList.add(new BluetoothLeAgent());
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public WifiDeviceInfo replaceOldWifiWithNew(DeviceInfo deviceInfo) {
        WifiDeviceInfo wifiDeviceInfo = (WifiDeviceInfo) deviceInfo;
        return (WifiDeviceInfo) this.mIpToWifiDevices.put(wifiDeviceInfo.getUri().getAuthority(), wifiDeviceInfo);
    }

    public void destroy() {
        for (DiscoveryAgent destroy : this.mDiscoveryAgents) {
            destroy.destroy();
        }
    }

    /* access modifiers changed from: protected */
    public int onDevice(DeviceInfo deviceInfo, boolean z) {
        if (deviceInfo instanceof WifiDeviceInfo) {
            WifiDeviceInfo wifiDeviceInfo = (WifiDeviceInfo) deviceInfo;
            String txtEntry = wifiDeviceInfo.getTxtEntry("bt");
            if (!TextUtils.isEmpty(txtEntry)) {
                if (this.mBluetoothDevices.containsKey(txtEntry)) {
                    ((DeviceInfo) this.mBluetoothDevices.get(txtEntry)).setOtherDeviceInfo(deviceInfo);
                    return 0;
                } else if (!z) {
                    this.mWifiDevices.remove(txtEntry);
                } else {
                    this.mWifiDevices.put(txtEntry, deviceInfo);
                }
            }
            String authority = wifiDeviceInfo.getUri().getAuthority();
            WifiDeviceInfo wifiDeviceInfo2 = (WifiDeviceInfo) this.mIpToWifiDevices.get(authority);
            if (wifiDeviceInfo2 != null) {
                boolean equals = wifiDeviceInfo2.equals(deviceInfo);
                if (z && equals) {
                    return 0;
                }
                if (z && !equals) {
                    return 3;
                }
                if (!z && equals) {
                    this.mIpToWifiDevices.remove(authority);
                }
            } else if (z) {
                this.mIpToWifiDevices.put(authority, wifiDeviceInfo);
            }
        } else if (deviceInfo instanceof BluetoothDeviceInfo) {
            String address = ((BluetoothDeviceInfo) deviceInfo).getAddress();
            if (this.mWifiDevices.containsKey(address)) {
                ((DeviceInfo) this.mWifiDevices.get(address)).setOtherDeviceInfo(deviceInfo);
                return 0;
            } else if (!z) {
                this.mBluetoothDevices.remove(address);
            } else {
                this.mBluetoothDevices.put(address, deviceInfo);
            }
        }
        return !z ? 2 : 1;
    }

    public void startDiscovery(final DiscoveryListener discoveryListener, final Handler handler) {
        if (this.mLocalListener != null) {
            stopDiscovery();
        }
        this.mLocalListener = new DiscoveryAgent.Listener() {
            private int mStartedCount = 0;
            private final Object mStartedCountLock = new Object();

            public void onDeviceFound(final DeviceInfo deviceInfo) {
                int onDevice = Discoverer.this.onDevice(deviceInfo, true);
                if (onDevice == 1) {
                    handler.post(new Runnable() {
                        public void run() {
                            discoveryListener.onDeviceFound(deviceInfo);
                        }
                    });
                } else if (onDevice == 3) {
                    handler.post(new Runnable() {
                        public void run() {
                            discoveryListener.onDeviceReplace(Discoverer.this.replaceOldWifiWithNew(deviceInfo), deviceInfo);
                        }
                    });
                }
            }

            public void onDeviceLost(final DeviceInfo deviceInfo) {
                if (Discoverer.this.onDevice(deviceInfo, false) == 2) {
                    handler.post(new Runnable() {
                        public void run() {
                            discoveryListener.onDeviceLost(deviceInfo);
                        }
                    });
                }
            }

            public void onDiscoveryStarted() {
                synchronized (this.mStartedCountLock) {
                    int i = this.mStartedCount + 1;
                    this.mStartedCount = i;
                    if (1 == i) {
                        handler.post(new Runnable() {
                            public void run() {
                                discoveryListener.onDiscoveryStarted();
                            }
                        });
                    }
                }
            }

            public void onDiscoveryStopped() {
                synchronized (this.mStartedCountLock) {
                    int i = this.mStartedCount - 1;
                    this.mStartedCount = i;
                    if (i == 0) {
                        handler.post(new Runnable() {
                            public void run() {
                                discoveryListener.onDiscoveryStopped();
                            }
                        });
                    }
                }
            }

            public void onStartDiscoveryFailed(final int i) {
                handler.post(new Runnable() {
                    public void run() {
                        discoveryListener.onStartDiscoveryFailed(i);
                    }
                });
            }
        };
        for (DiscoveryAgent startDiscovery : this.mDiscoveryAgents) {
            startDiscovery.startDiscovery(this.mLocalListener, handler);
        }
    }

    public void stopDiscovery() {
        if (this.mLocalListener != null) {
            for (DiscoveryAgent stopDiscovery : this.mDiscoveryAgents) {
                stopDiscovery.stopDiscovery();
            }
            this.mLocalListener = null;
        }
        this.mWifiDevices.clear();
        this.mBluetoothDevices.clear();
        this.mIpToWifiDevices.clear();
    }
}
