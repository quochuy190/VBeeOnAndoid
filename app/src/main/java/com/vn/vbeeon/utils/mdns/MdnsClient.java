package com.vn.vbeeon.utils.mdns;

import android.os.SystemClock;
import android.text.TextUtils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: com.google.android.tv.support.remote.mdns.MdnsClient */
/* compiled from: Unknown */
abstract class MdnsClient {
    private static final boolean DEBUG = false;
    private static final int FLAGS_QUERY = 0;
    private static final int FLAGS_RESPONSE = 33792;
    private static final int KNOWN_ANSWERS_SOFT_LIMIT = 32;
    public static final int MAX_SCAN_INTERVAL_MS = 15000;
    private static final String PROTO_TOKEN_TCP = "_tcp";
    private static final String PROTO_TOKEN_UDP = "_udp";
    private static final int QCLASS_INTERNET = 1;
    private static final int QCLASS_UNICAST = 32768;
    private static final String TAG = "AtvRemote.MdnsClient";
    private static final int TTL_MAX = 604800;
    private static final int TYPE_A = 1;
    private static final int TYPE_AAAA = 28;
    private static final int TYPE_PTR = 12;
    private static final int TYPE_SRV = 33;
    private static final int TYPE_TXT = 16;
    private final Set<SoftReference<Answer>> mKnownAnswers = new LinkedHashSet();
    private InetSocketAddress mMulticastAddress;
    private final NetworkInterface mNetworkInterface;
    private final byte[] mReceiveBuffer = new byte[65536];
    private Thread mReceiveThread;
    private Thread mSendThread;
    private final String mServiceName;
    private volatile boolean mShouldStop;
    private MulticastSocket mSocket;
    private String[] serviceWords;

    /* renamed from: com.google.android.tv.support.remote.mdns.MdnsClient$Answer */
    /* compiled from: Unknown */
    private static class Answer {
        private final byte[] mAnswer;
        private final long mInitialTimeMillis = SystemClock.elapsedRealtime();
        private final int mInitialTtlSeconds;

        public Answer(byte[] bArr, int i) {
            this.mAnswer = bArr;
            this.mInitialTtlSeconds = i;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Answer) {
                return this.mAnswer.equals(((Answer) obj).mAnswer);
            }
            return false;
        }

        public byte[] getAnswer() {
            return this.mAnswer;
        }

        public int hashCode() {
            if (this.mAnswer != null) {
                return this.mAnswer.hashCode();
            }
            return 0;
        }

        public boolean isExpired() {
            return ((double) ((SystemClock.elapsedRealtime() - this.mInitialTimeMillis) / 1000)) > ((double) this.mInitialTtlSeconds) / 2.0d;
        }
    }

    public MdnsClient(String str, NetworkInterface networkInterface) {
        this.mServiceName = str;
        this.mNetworkInterface = networkInterface;
        this.serviceWords = str.split("\\.");
        for (String str2 : this.serviceWords) {
        }
    }

    private DatagramPacket buildQueryPacket(String str, boolean z) {
        int i = 0;
        MdnsPacketWriter mdnsPacketWriter = new MdnsPacketWriter();
        removeExpiredAnswers();
        synchronized (this.mKnownAnswers) {
            mdnsPacketWriter.writeUInt16(0);
            mdnsPacketWriter.writeUInt16(0);
            mdnsPacketWriter.writeUInt16(1);
            mdnsPacketWriter.writeUInt16(this.mKnownAnswers.size());
            mdnsPacketWriter.writeUInt16(0);
            mdnsPacketWriter.writeUInt16(0);
            mdnsPacketWriter.writeLabels(str);
            mdnsPacketWriter.writeUInt8(0);
            mdnsPacketWriter.writeUInt16(12);
            if (z) {
                i = 32768;
            }
            mdnsPacketWriter.writeUInt16(i | 1);
            for (SoftReference softReference : this.mKnownAnswers) {
                Answer answer = (Answer) softReference.get();
                if (answer != null) {
                    mdnsPacketWriter.writeBytes(answer.getAnswer());
                }
            }
        }
        return mdnsPacketWriter.getPacket();
    }

    private void dumpData(byte[] bArr, int i, int i2) {
    }

    /* access modifiers changed from: private */
    public void receiveLoop() {
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[16];
        DatagramPacket datagramPacket = new DatagramPacket(this.mReceiveBuffer, this.mReceiveBuffer.length);
        while (!this.mShouldStop) {
            try {
                this.mSocket.receive(datagramPacket);
                MdnsPacketReader mdnsPacketReader = new MdnsPacketReader(datagramPacket);
                mdnsPacketReader.readUInt16();
                if (mdnsPacketReader.readUInt16() == FLAGS_RESPONSE) {
                    mdnsPacketReader.readUInt16();
                    int readUInt16 = mdnsPacketReader.readUInt16();
                    int readUInt162 = mdnsPacketReader.readUInt16();
                    int readUInt163 = mdnsPacketReader.readUInt16();
                    int position = mdnsPacketReader.getPosition();
                    HashMap hashMap = new HashMap();
                    HashMap hashMap2 = new HashMap();
                    HashMap<String, MdnsPacketWriter.MdnsResponse.Builder> hashMap3 = new HashMap();
                    HashMap hashMap4 = new HashMap();
                    for (int i = 0; i < readUInt16 + readUInt162 + readUInt163; i++) {
                        String[] readLabels = mdnsPacketReader.readLabels();
                        int readUInt164 = mdnsPacketReader.readUInt16();
                        mdnsPacketReader.readUInt16();
                        long readUInt32 = mdnsPacketReader.readUInt32();
                        int readUInt165 = mdnsPacketReader.readUInt16();
                        String join = TextUtils.join(".", readLabels);
                        if (i < readUInt16) {
                            if (!(readUInt32 <= 0)) {
                                if (!(readUInt32 >= 604800)) {
                                    byte[] copyOfRange = Arrays.copyOfRange(datagramPacket.getData(), position, position + readUInt165);
                                    synchronized (this.mKnownAnswers) {
                                        Set<SoftReference<Answer>> set = this.mKnownAnswers;
                                        Answer answer = new Answer(copyOfRange, (int) readUInt32);
                                        set.add(new SoftReference(answer));
                                    }
                                }
                            }
                        }
                        switch (readUInt164) {
                            case 1:
                                mdnsPacketReader.readBytes(bArr);
                                try {
                                    hashMap.put(join, (Inet4Address) InetAddress.getByAddress(bArr));
                                    break;
                                } catch (UnknownHostException e) {
                                    break;
                                }
                            case 12:
                                mdnsPacketReader.readLabels();
                                break;
                            case 16:
                                int position2 = mdnsPacketReader.getPosition() + readUInt165;
                                ArrayList arrayList = new ArrayList();
                                while (mdnsPacketReader.getPosition() < position2) {
                                    arrayList.add(mdnsPacketReader.readString());
                                }
                                hashMap4.put(join, arrayList);
                                break;
                            case 28:
                                mdnsPacketReader.readBytes(bArr2);
                                try {
                                    hashMap2.put(join, (Inet6Address) InetAddress.getByAddress(bArr2));
                                    break;
                                } catch (UnknownHostException e2) {
                                    break;
                                }
                            case 33:
                                MdnsPacketWriter.MdnsResponse.Builder builder = new MdnsPacketWriter.MdnsResponse.Builder();
                                builder.updateTimeToLive(readUInt32);
                                builder.setFqdn(join);
                                builder.setServicePriority(mdnsPacketReader.readUInt16());
                                builder.setServiceWeight(mdnsPacketReader.readUInt16());
                                builder.setServicePort(mdnsPacketReader.readUInt16());
                                builder.setServiceHost(TextUtils.join(".", mdnsPacketReader.readLabels()));
                                builder.setServiceInstanceName(readLabels[0]);
                                builder.setServiceName(readLabels[1]);
                                String str = readLabels[2];
                                if (str.equals(PROTO_TOKEN_TCP)) {
                                    builder.setServiceProtocol(1);
                                } else if (str.equals(PROTO_TOKEN_UDP)) {
                                    builder.setServiceProtocol(2);
                                }
                                if (readLabels.length != 4) {
                                    break;
                                } else {
                                    int i2 = 1;
                                    boolean z = true;
                                    String[] strArr = this.serviceWords;
                                    int length = strArr.length;
                                    int i3 = 0;
                                    while (z && i2 < 4) {
                                        if (i3 < length) {
                                            if (readLabels[i2].equalsIgnoreCase(strArr[i3])) {
                                                i2++;
                                                i3++;
                                            } else {
                                                z = false;
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                    if (!z) {
                                        break;
                                    } else {
                                        hashMap3.put(join, builder);
                                        break;
                                    }
                                }
                            default:
                                mdnsPacketReader.skip(readUInt165);
                                break;
                        }
                    }
                    for (Entry entry : hashMap3.entrySet()) {
                        String str2 = (String) entry.getKey();
                        MdnsPacketWriter.MdnsResponse.Builder builder2 = (MdnsPacketWriter.MdnsResponse.Builder) entry.getValue();
                        String serviceHost = builder2.build().getServiceHost();
                        Inet4Address inet4Address = (Inet4Address) hashMap.get(serviceHost);
                        if (inet4Address != null) {
                            builder2.addInet4Address(inet4Address);
                        }
                        Inet6Address inet6Address = (Inet6Address) hashMap2.get(serviceHost);
                        if (inet6Address != null) {
                            builder2.addInet6Address(inet6Address);
                        }
                        List<String> list = (List) hashMap4.get(str2);
                        if (list != null) {
                            for (String addTextString : list) {
                                builder2.addTextString(addTextString);
                            }
                        }
                        onResponseReceived(builder2.build());
                    }
                } else {
                    continue;
                }
            } catch (IOException e3) {
                if (this.mShouldStop) {
                }
            }
        }
    }

    private void removeExpiredAnswers() {
        synchronized (this.mKnownAnswers) {
            Iterator it = this.mKnownAnswers.iterator();
            int i = 32;
            while (it.hasNext()) {
                if (i <= 32) {
                    Answer answer = (Answer) ((SoftReference) it.next()).get();
                    if (answer != null) {
                        if (!answer.isExpired()) {
                        }
                    }
                    it.remove();
                } else {
                    it.remove();
                }
                i--;
            }
        }
    }

    /* access modifiers changed from: private */
    public void sendLoop() {
        boolean z = true;
        int i = 1000;
        while (!this.mShouldStop) {
            try {
                sendQuery(this.mServiceName, z);
                z = false;
            } catch (IOException e) {
            }
            try {
                Thread.sleep((long) i);
                if (i < 15000) {
                    i *= 2;
                }
            } catch (InterruptedException e2) {
            }
        }
    }

    private void sendQuery(String str, boolean z) throws IOException {
        this.mSocket.send(buildQueryPacket(str, z));
    }

    private void waitForThread(Thread thread) {
        while (true) {
            try {
                thread.interrupt();
                thread.join();
                break;
            } catch (InterruptedException e) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onResponseReceived(MdnsPacketWriter.MdnsResponse mdnsResponse);

    public synchronized void start() throws IOException {
        if (this.mSocket == null) {
            this.mShouldStop = false;
            this.mSocket = new MulticastSocket(MdnsConstants.MDNS_PORT);
            this.mSocket.setTimeToLive(1);
            if (this.mNetworkInterface != null) {
                this.mSocket.setNetworkInterface(this.mNetworkInterface);
            }
            this.mMulticastAddress = new InetSocketAddress(MdnsConstants.MDNS_ADDRESS, MdnsConstants.MDNS_PORT);
            this.mSocket.joinGroup(this.mMulticastAddress, this.mNetworkInterface);
            this.mReceiveThread = new Thread(new Runnable() {
                public void run() {
                    MdnsClient.this.receiveLoop();
                }
            });
            this.mReceiveThread.start();
            this.mSendThread = new Thread(new Runnable() {
                public void run() {
                    MdnsClient.this.sendLoop();
                }
            });
            this.mSendThread.start();
        }
    }

    public synchronized void stop() {
        if (this.mSocket != null) {
            this.mShouldStop = true;
            try {
                this.mSocket.leaveGroup(this.mMulticastAddress, this.mNetworkInterface);
            } catch (IOException e) {
            }
            this.mSocket.close();
            if (this.mReceiveThread != null) {
                waitForThread(this.mReceiveThread);
                this.mReceiveThread = null;
            }
            if (this.mSendThread != null) {
                waitForThread(this.mSendThread);
                this.mSendThread = null;
            }
            this.mSocket = null;
        }
    }
}
