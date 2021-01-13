package com.vn.vbeeon.domain.model;

import com.ideabus.model.protocol.BPMProtocol;
import com.ideabus.model.protocol.BaseProtocol;
import com.ideabus.model.protocol.EBodyProtocol;
import com.ideabus.model.protocol.ThermoProtocol;
import com.ideabus.model.protocol.WBO3Protocol;
import com.ideabus.model.protocol.WBOProtocol;
import com.ideabus.model.protocol.WBPProtocol;

/**
 * Created by Ting on 16/4/20.
 */
public class Global {
    public static BaseProtocol protocol;
    public static WBPProtocol wbpProtocol;
    public static WBO3Protocol wbo3Protocol;
    public static WBOProtocol wboProtocol;
    public static ThermoProtocol thermoProtocol;
    public static EBodyProtocol eBodyProtocol;
    public static BPMProtocol bpmProtocol;
    public static String sdkid_BPM = "";
    public static String sdkid_WEI = "";
    public static String sdkid_BT = "";
    public static String sdkid_WBP = "";

}
