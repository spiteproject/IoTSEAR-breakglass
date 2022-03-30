package be.hcbgsystem.nonrepudiation.providers;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidenceRecord;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.pf4j.Extension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Dependencies:
 * - eID middleware (https://eid.belgium.be/en)
 * - Python 3
 * - Python package "eidreader" (pip install eidreader)
 */

@Extension
public class ElectronicID implements NonRepudiationProvider  {
    private JSONObject getCardInfo() throws IOException, ParseException {
            StringBuilder cardData = new StringBuilder();
            Process p = Runtime.getRuntime().exec("python -m eidreader.main");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while (((line = reader.readLine()) != null)) {
                cardData.append(line);
            }

            return (JSONObject) new JSONParser().parse(cardData.toString());
    }
    @Override
    public void collectEvidence(EvidenceProducedCallback callback) {
        System.out.println("[EID READER] Please insert your eID card...");
        while (true) {
            try {
                JSONObject cardData = getCardInfo();
                if ((boolean) cardData.get("success")) {
                    callback.onEvidenceCollected(new NonRepudiationEvidenceRecord(
                            getId(),
                            cardData.toJSONString().getBytes(),
                            getDataExtension()
                    ));
                    break;
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                callback.onEvidenceCollectionFailed();
                break;
            }
        }
    }

    @Override
    public String getId() {
        return "eid-reader-1";
    }

    @Override
    public String getDataExtension() {
        return "txt";
    }

    public static void main(String[] args) {
        ElectronicID id = new ElectronicID();
        id.collectEvidence(new EvidenceProducedCallback() {
            @Override
            public void onEvidenceCollected(NonRepudiationEvidenceRecord evidence) {
                System.out.println("collected " + new String(evidence.getEvidence()));
            }

            @Override
            public void onEvidenceCollectionFailed() {
                System.out.println("Failed");
            }
        });
    }
}
