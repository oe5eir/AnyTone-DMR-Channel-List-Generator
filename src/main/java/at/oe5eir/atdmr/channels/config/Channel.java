package at.oe5eir.atdmr.channels.config;

/*
 *  Copyright (C) 2023 OE5EIR @ https://www.oe5eir.at/
 *
 *  Licensed under the GNU General Public License v3.0 (the "License").
 *  You may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *      https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

public class Channel {
    private String channelName;
    private double receiveFrequency, transmitFrequency;
    private ChannelType channelType;
    private TransmitPower transmitPower = TransmitPower.High;
    private Bandwidth bandwidth = Bandwidth.K125;
    private Double ctcDecode;
    private Double ctcEncode;
    private String contactName;
    private ContactCallType contactCallType;
    private int contactId;
    private String radioId;
    private BusyLock busyLock;
    private SquelchMode squelchMode = SquelchMode.CARRIER;
    private OptionalSetting optionalSetting = OptionalSetting.OFF;
    private int dtmfId = 1;
    private int twoToneId = 1;
    private int fiveToneId = 1;
    private PttId pttId = PttId.OFF;
    private int colorCode;
    private int slot;
    private String scanList = "None"; // None
    private String receiveGroupList = "None"; // None
    private boolean pttProhibit = false; // Off, On
    private boolean reverse = false; // Off, On
    private boolean simplexTdma = false; // Off, On
    private boolean slotSuit = false; // Off, On
    private String aesDigitalEncryption = "Normal Encryption";
    private String digitalEncryption = "Off";
    private boolean callConfirmation = false; // Off, On
    private boolean talkAroundSimplex = false; // Off, On
    private boolean workAlone = false; // Off, On
    private double customCtcss = 251.1f;
    private int twoToneDecode = 0;
    private boolean ranging = false; // Off, On
    private boolean throughMode = false; // Off, On
    private boolean aprsRx = false; // Off, On
    private boolean analogAprsPttMode = false; // Off, On
    private boolean digitalAprsPttMode = false; // Off, On
    private boolean aprsReportType = false; // Off, On
    private int digitalAprsReportChannel = 1;
    private double correctFrequency = 0;
    private boolean smsConfirmation = false; // Off, On
    private boolean excludeChannelFromRoaming = false; // 0
    private int dmrMode; // 0, 1, 2, 3
    private boolean dataAckDisable = false; // 0, 1
    private int r5toneBot = 0;
    private int r5toneEot = 0;
    private int autoScan = 0;
    private int anaAprsMute = 0;
    private int sendTalkerAlias = 0;
    private int anaAprsTxPath = 0;

    private Channel(String channelName, double receiveFrequency, double transmitFrequency, String radioId) {
        this.channelName = channelName;
        this.receiveFrequency = receiveFrequency;
        this.transmitFrequency = transmitFrequency;
        this.radioId = radioId;
    }

    public Channel(String channelName, double receiveFrequency, double transmitFrequency, Double ctcDecode, Double ctcEncode, String radioId) {
        this(channelName, receiveFrequency, transmitFrequency, radioId);
        this.channelType = ChannelType.ANALOG;
        this.ctcDecode = ctcDecode;
        this.ctcEncode = ctcEncode;
        this.contactName = "All Call";
        this.contactCallType = ContactCallType.ALLCALL;
        this.contactId = 16777215;
        this.busyLock = BusyLock.CARRIER;
        this.colorCode = 1;
        this.slot = 1;
        this.dmrMode = 0;
    }

    public Channel(String channelName, double receiveFrequency, double transmitFrequency, int colorCode, int slot, String radioId) {
        this(channelName, receiveFrequency, transmitFrequency, radioId);
        this.channelType = ChannelType.DIGITAL;
        this.ctcDecode = null;
        this.ctcEncode = null;
        this.contactName = "232-Austria";
        this.contactCallType = ContactCallType.GROUPCALL;
        this.contactId = 232;
        this.busyLock = BusyLock.DIFFERENT_CC;
        this.colorCode = colorCode;
        this.slot = slot;
        this.dmrMode = 1;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String toString() {
        return String.format("\"%s\",\"%3.5f\",\"%3.5f\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%d\",\"%s\",\"%s\",\"%s\",\"%s\"," +
                "\"%d\",\"%d\",\"%d\",\"%s\",\"%d\",\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"," +
                "\"%.1f\",\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"," +
                "\"%s\",\"%s\",\"%f\",\"%s\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\"",
                channelName, receiveFrequency, transmitFrequency, channelType, transmitPower, bandwidth,
                ctcDecode == null ? "Off" : String.format("%.1f", ctcDecode),
                ctcEncode == null ? "Off" : String.format("%.1f", ctcEncode),
                contactName, contactCallType, contactId, radioId,
                busyLock, squelchMode, optionalSetting, /*br*/ dtmfId, twoToneId, fiveToneId, pttId,
                colorCode, slot, scanList, receiveGroupList, b(pttProhibit), b(reverse), b(simplexTdma), b(slotSuit),
                aesDigitalEncryption, digitalEncryption, b(callConfirmation), b(talkAroundSimplex), b(workAlone),/*br*/
                customCtcss, twoToneDecode, b(ranging), b(throughMode), b(aprsRx), b(analogAprsPttMode), b(digitalAprsPttMode),/*br*/
                b(aprsReportType), digitalAprsReportChannel, correctFrequency, b(smsConfirmation), bd(excludeChannelFromRoaming),
                dmrMode, bd(dataAckDisable), r5toneBot, r5toneEot, autoScan, anaAprsMute, sendTalkerAlias, bd(analogAprsPttMode));
    }

    private static String b(boolean b) {
        return b ? "On" : "Off";
    }

    private static int bd(boolean b) {
        return b ? 1 : 0;
    }
}
