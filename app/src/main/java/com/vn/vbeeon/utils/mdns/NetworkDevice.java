package com.vn.vbeeon.utils.mdns;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;

/* renamed from: com.google.android.tv.support.remote.mdns.NetworkDevice */
/* compiled from: Unknown */
public class NetworkDevice {
    private final String mInstanceName;
    private final Inet4Address mIpAddress;
    private final int mServicePort;
    private final String mServiceType;
    private final List<String> mTxtEntries;

    NetworkDevice(String str, Inet4Address inet4Address, String str2, int i) {
        this(str, inet4Address, str2, i, null);
    }

    NetworkDevice(String str, Inet4Address inet4Address, String str2, int i, List<String> list) {
        this.mServiceType = str;
        this.mIpAddress = inet4Address;
        this.mInstanceName = str2;
        this.mServicePort = i;
        this.mTxtEntries = list;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NetworkDevice)) {
            return false;
        }
        NetworkDevice networkDevice = (NetworkDevice) obj;
        if (this.mIpAddress != null && networkDevice.mIpAddress != null && !this.mIpAddress.equals(networkDevice.mIpAddress)) {
            return false;
        }
        if (this.mServiceType != null && networkDevice.mServiceType != null && !this.mServiceType.equals(networkDevice.mServiceType)) {
            return false;
        }
        if (this.mInstanceName != null && networkDevice.mInstanceName != null && !this.mInstanceName.equals(networkDevice.mInstanceName)) {
            return false;
        }
        if (this.mServicePort == networkDevice.mServicePort) {
            z = true;
        }
        return z;
    }

    public InetAddress getHost() {
        return this.mIpAddress;
    }

    public int getPort() {
        return this.mServicePort;
    }

    public String getServiceName() {
        return this.mInstanceName;
    }

    public String getServiceType() {
        return this.mServiceType;
    }

    public List<String> getTxtEntries() {
        return this.mTxtEntries;
    }

    public int hashCode() {
        int i = 0;
        if (this.mIpAddress != null) {
            i = this.mIpAddress.hashCode();
        }
        return i ^ this.mServicePort;
    }

    public String toString() {
        return String.format("\"%s\"", new Object[]{this.mInstanceName});
    }
}
