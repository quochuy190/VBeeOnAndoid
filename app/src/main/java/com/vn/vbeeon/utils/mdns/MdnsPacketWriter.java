package com.vn.vbeeon.utils.mdns;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.tv.support.remote.mdns.MdnsPacketWriter */
/* compiled from: Unknown */
class MdnsPacketWriter {
    private ByteArrayOutputStream mByteStream = new ByteArrayOutputStream(16384);
    private DataOutputStream mDataStream = new DataOutputStream(this.mByteStream);

    public DatagramPacket getPacket() {
        try {
            this.mDataStream.flush();
        } catch (IOException e) {
        }
        byte[] byteArray = this.mByteStream.toByteArray();
        return new DatagramPacket(byteArray, byteArray.length, MdnsConstants.MDNS_ADDRESS, MdnsConstants.MDNS_PORT);
    }

    public void writeBytes(byte[] bArr) {
        try {
            this.mDataStream.write(bArr, 0, bArr.length);
        } catch (IOException e) {
        }
    }

    public void writeLabels(String str) {
        for (String bytes : str.split("\\.")) {
            byte[] bytes2 = bytes.getBytes(MdnsConstants.UTF8_CHARSET);
            writeUInt8(bytes2.length);
            writeBytes(bytes2);
        }
    }

    public void writeUInt16(int i) {
        try {
            this.mDataStream.writeShort(1048575 & i);
        } catch (IOException e) {
        }
    }

    public void writeUInt8(int i) {
        try {
            this.mDataStream.writeByte(i & 255);
        } catch (IOException e) {
        }
    }

    /* renamed from: com.google.android.tv.support.remote.mdns.MdnsResponse */
    /* compiled from: Unknown */
    static final class MdnsResponse {
        public static final int PROTO_NONE = 0;
        public static final int PROTO_TCP = 1;
        public static final int PROTO_UDP = 2;
        private String mFqdn;
        private List<Inet4Address> mInet4Addresses;
        private List<Inet6Address> mInet6Addresses;
        private String mServiceHost;
        private String mServiceInstanceName;
        private String mServiceName;
        private int mServicePort;
        private int mServicePriority;
        private int mServiceProtocol;
        private int mServiceWeight;
        private List<String> mTextStrings;
        private long mTimeToLive;

        /* renamed from: com.google.android.tv.support.remote.mdns.MdnsResponse$Builder */
        /* compiled from: Unknown */
        public static class Builder {
            private MdnsResponse mResponse = new MdnsResponse();

            public Builder addInet4Address(Inet4Address inet4Address) {
                this.mResponse.addInet4Address(inet4Address);
                return this;
            }

            public Builder addInet6Address(Inet6Address inet6Address) {
                this.mResponse.addInet6Address(inet6Address);
                return this;
            }

            public Builder addTextString(String str) {
                this.mResponse.addTextString(str);
                return this;
            }

            public MdnsResponse build() {
                return this.mResponse;
            }

            public Builder setFqdn(String str) {
                this.mResponse.setFqdn(str);
                return this;
            }

            public Builder setServiceHost(String str) {
                this.mResponse.setServiceHost(str);
                return this;
            }

            public Builder setServiceInstanceName(String str) {
                this.mResponse.setServiceInstanceName(str);
                return this;
            }

            public Builder setServiceName(String str) {
                this.mResponse.setServiceName(str);
                return this;
            }

            public Builder setServicePort(int i) {
                this.mResponse.setServicePort(i);
                return this;
            }

            public Builder setServicePriority(int i) {
                this.mResponse.setServicePriority(i);
                return this;
            }

            public Builder setServiceProtocol(int i) {
                this.mResponse.setServiceProtocol(i);
                return this;
            }

            public Builder setServiceWeight(int i) {
                this.mResponse.setServiceWeight(i);
                return this;
            }

            public Builder updateTimeToLive(long j) {
                this.mResponse.updateTimeToLive(j);
                return this;
            }
        }

        private MdnsResponse() {
            this.mServiceProtocol = 0;
            this.mTimeToLive = -1;
        }

        /* access modifiers changed from: private */
        public void addInet4Address(Inet4Address inet4Address) {
            if (this.mInet4Addresses == null) {
                this.mInet4Addresses = new ArrayList();
            }
            this.mInet4Addresses.add(inet4Address);
        }

        /* access modifiers changed from: private */
        public void addInet6Address(Inet6Address inet6Address) {
            if (this.mInet6Addresses == null) {
                this.mInet6Addresses = new ArrayList();
            }
            this.mInet6Addresses.add(inet6Address);
        }

        /* access modifiers changed from: private */
        public void setFqdn(String str) {
            this.mFqdn = str;
        }

        /* access modifiers changed from: private */
        public void setServiceHost(String str) {
            this.mServiceHost = str;
        }

        /* access modifiers changed from: private */
        public void setServiceInstanceName(String str) {
            this.mServiceInstanceName = str;
        }

        /* access modifiers changed from: private */
        public void setServiceName(String str) {
            this.mServiceName = str;
        }

        /* access modifiers changed from: private */
        public void setServicePort(int i) {
            this.mServicePort = i;
        }

        /* access modifiers changed from: private */
        public void setServicePriority(int i) {
            this.mServicePriority = i;
        }

        /* access modifiers changed from: private */
        public void setServiceProtocol(int i) {
            this.mServiceProtocol = i;
        }

        /* access modifiers changed from: private */
        public void setServiceWeight(int i) {
            this.mServiceWeight = i;
        }

        public void addTextString(String str) {
            if (this.mTextStrings == null) {
                this.mTextStrings = new ArrayList();
            }
            this.mTextStrings.add(str);
        }

        public String getFqdn() {
            return this.mFqdn;
        }

        public List<Inet4Address> getInet4Addresses() {
            return this.mInet4Addresses;
        }

        public List<Inet6Address> getInet6Addresses() {
            return this.mInet6Addresses;
        }

        public String getServiceHost() {
            return this.mServiceHost;
        }

        public String getServiceInstanceName() {
            return this.mServiceInstanceName;
        }

        public String getServiceName() {
            return this.mServiceName;
        }

        public int getServicePort() {
            return this.mServicePort;
        }

        public int getServicePriority() {
            return this.mServicePriority;
        }

        public int getServiceProtocol() {
            return this.mServiceProtocol;
        }

        public int getServiceWeight() {
            return this.mServiceWeight;
        }

        public List<String> getTextStrings() {
            return this.mTextStrings;
        }

        public long getTimeToLive() {
            return this.mTimeToLive;
        }

        /* access modifiers changed from: 0000 */
        public void updateTimeToLive(long j) {
            boolean z = true;
            if (!(this.mTimeToLive < 0)) {
                if (j < this.mTimeToLive) {
                    z = false;
                }
                if (z) {
                    return;
                }
            }
            this.mTimeToLive = j;
        }
    }
}
