package at.oe5eir.atdmr.channels;

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

import at.oe5eir.atdmr.channels.config.Channel;
import org.apache.commons.text.CaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Generator for Austria Channel List for AnyTone DMR Radios
 * Parameter: Output File
 */
public final class Main {
    private static String identity;

    public static void main(String[] args) throws IOException {
        long time = System.currentTimeMillis();

        if (args.length != 2) {
            System.out.println("Parameter 1: output filename");
            System.out.println("Parameter 2: your identity string");
            return;
        }

        identity = args[1].trim();

        System.out.println("Getting data from OEVSV Repeater API...");

        JSONArray fm70cm = getJsonData("https://repeater.oevsv.at/api/trx?status=eq.active&type_of_station=eq.repeater_voice&fm=eq.true&band=eq.70cm");
        JSONArray fm2m = getJsonData("https://repeater.oevsv.at/api/trx?status=eq.active&type_of_station=eq.repeater_voice&fm=eq.true&band=eq.2m");
        JSONArray dmr70cm = getJsonData("https://repeater.oevsv.at/api/trx?status=eq.active&type_of_station=eq.repeater_voice&dmr=eq.true&band=eq.70cm");
        JSONArray dmr2m = getJsonData("https://repeater.oevsv.at/api/trx?status=eq.active&type_of_station=eq.repeater_voice&dmr=eq.true&band=eq.2m");

        System.out.println("Processing dataset...");

        List<Channel> fm70cmList = convertDataFM(fm70cm);
        List<Channel> fm2mList = convertDataFM(fm2m);
        List<Channel> dmr70cmList = convertDataDMR(dmr70cm);
        List<Channel> dmr2mList = convertDataDMR(dmr2m);

        Channel vfoA = new Channel("Channel VFO A", 433.5, 433.5, null, null, identity);
        Channel vfoB = new Channel("Channel VFO A", 144.5, 144.5, null, null, identity);

        System.out.println("Writing output file...");

        Locale.setDefault(Locale.US);

        File csvFile = new File(args[0]);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("\"No.\",\"Channel Name\",\"Receive Frequency\",\"Transmit Frequency\",\"Channel Type\",\"Transmit Power\",\"Band Width\",\"CTCSS/DCS Decode\",\"CTCSS/DCS Encode\",\"Contact\",\"Contact Call Type\",\"Contact TG/DMR ID\",\"Radio ID\",\"Busy Lock/TX Permit\",\"Squelch Mode\",\"Optional Signal\",\"DTMF ID\",\"2Tone ID\",\"5Tone ID\",\"PTT ID\",\"Color Code\",\"Slot\",\"Scan List\",\"Receive Group List\",\"PTT Prohibit\",\"Reverse\",\"Simplex TDMA\",\"Slot Suit\",\"AES Digital Encryption\",\"Digital Encryption\",\"Call Confirmation\",\"Talk Around(Simplex)\",\"Work Alone\",\"Custom CTCSS\",\"2TONE Decode\",\"Ranging\",\"Through Mode\",\"APRS RX\",\"Analog APRS PTT Mode\",\"Digital APRS PTT Mode\",\"APRS Report Type\",\"Digital APRS Report Channel\",\"Correct Frequency[Hz]\",\"SMS Confirmation\",\"Exclude channel from roaming\",\"DMR MODE\",\"DataACK Disable\",\"R5toneBot\",\"R5ToneEot\",\"Auto Scan\",\"Ana Aprs Mute\",\"Send Talker Alias\",\"AnaAprsTxPath\"\r\n");

            int i = 1;

            for (Channel c : fm70cmList) {
                writer.write("\"" + i++ + "\"," + c + "\r\n");
            }

            for (Channel c : fm2mList) {
                writer.write("\"" + i++ + "\"," + c + "\r\n");
            }

            for (Channel c : dmr70cmList) {
                writer.write("\"" + i++ + "\"," + c + "\r\n");
            }

            for (Channel c : dmr2mList) {
                writer.write("\"" + i++ + "\"," + c + "\r\n");
            }

            writer.write("\"4001\"," + vfoA + "\r\n");
            writer.write("\"4002\"," + vfoB + "\r\n");
        }

        System.out.println("Done!");
        System.out.println("List Location: " + csvFile.getAbsoluteFile());

        System.out.println(String.format("Completed in %.1f seconds.", (System.currentTimeMillis() - time) / 1000.0f));
    }

    private static JSONArray getJsonData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setInstanceFollowRedirects(false);

        int status = con.getResponseCode();
        if (status != 200)
            System.out.println("Status: " + status);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        return new JSONArray(content.toString());
    }

    private static String getCity(String location) throws IOException {
        URL url = new URL("https://repeater.oevsv.at/api/site?site_name=eq." + location.replace(" ", "%20"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(3000);
        con.setReadTimeout(3000);
        con.setInstanceFollowRedirects(false);

        int status = con.getResponseCode();
        if (status != 200)
            System.out.println("Status: " + status);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        return ((JSONObject) new JSONArray(content.toString()).get(0)).getString("city");
    }

    private static List<Channel> convertDataFM(JSONArray data) throws IOException {
        List<Channel> list = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            String callsign = (String) ((JSONObject) data.get(i)).get("callsign");
            String location = (String) ((JSONObject) data.get(i)).get("site_name");
            String city = getCity(location);
            Object txo = ((JSONObject) data.get(i)).get("frequency_tx");
            double tx = fixBullshitToDouble(txo);
            Object rxo = ((JSONObject) data.get(i)).get("frequency_rx");
            double rx = fixBullshitToDouble(rxo);
            Object ctcssTxObj = ((JSONObject) data.get(i)).get("ctcss_tx");
            Double ctcssTx = fixBullshitToDouble(ctcssTxObj);
            Object ctcssRxObj = ((JSONObject) data.get(i)).get("ctcss_rx");
            Double ctcssRx = fixBullshitToDouble(ctcssRxObj);

            list.add(new Channel(callsign + " " + fixString(city,9), rx, tx, ctcssRx, ctcssTx, identity));
        }

        list.sort(new ChannelComparator());
        return list;
    }

    private static List<Channel> convertDataDMR(JSONArray data) throws IOException {
        List<Channel> list = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            String callsign = (String) ((JSONObject) data.get(i)).get("callsign");
            String location = (String) ((JSONObject) data.get(i)).get("site_name");
            String city = getCity(location);
            Object txo = ((JSONObject) data.get(i)).get("frequency_tx");
            double tx = fixBullshitToDouble(txo);
            Object rxo = ((JSONObject) data.get(i)).get("frequency_rx");
            double rx = fixBullshitToDouble(rxo);

            int cc;
            try {
                cc = (int) ((JSONObject) data.get(i)).get("cc");
            } catch (ClassCastException ex) {
                cc = 1;
            }

            list.add(new Channel(callsign + "/1 " + fixString(city,7), rx, tx, cc, 1, identity));
            list.add(new Channel(callsign + "/2 " + fixString(city,7), rx, tx, cc, 2, identity));
        }

        list.sort(new ChannelComparator());
        return list;
    }

    private static String fixString(String s, int len) {
        String str = new String(s);
        str = str.replaceAll("\\d","");
        str = str.replace("ß","ss");
        str = str.replace("Ä","Ae");
        str = str.replace("Ö","Oe");
        str = str.replace("Ü","Ue");
        str = str.replace("ä","ae");
        str = str.replace("ö","oe");
        str = str.replace("ü","ue");
        str = CaseUtils.toCamelCase(str, true, ' ', '-', '.');
        str = str.trim();

        if (str.length() > len)
            return str.substring(0, len - 1) + ".";
        else
            return str;
    }

    private static Double fixBullshitToDouble(Object obj) {
        if (obj instanceof Double)
            return (Double) obj;
        else if (obj instanceof Integer)
            return ((Integer) obj).doubleValue();
        else if (obj instanceof BigDecimal)
            return ((BigDecimal) obj).doubleValue();
        else
            return null;
    }
}
