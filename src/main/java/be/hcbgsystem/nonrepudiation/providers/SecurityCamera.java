package be.hcbgsystem.nonrepudiation.providers;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidenceRecord;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamUtils;
import org.pf4j.Extension;

import java.awt.*;

@Extension
public class SecurityCamera implements NonRepudiationProvider {
    @Override
    public void collectEvidence(EvidenceProducedCallback callback) {
        try {
            Webcam cam = Webcam.getDefault();
            cam.setViewSize(new Dimension(640, 480));
            if (cam != null) {
                cam.open();

                if (cam.isOpen()) {
                    byte[] snapshot = WebcamUtils.getImageBytes(cam, "jpg");
                    NonRepudiationEvidenceRecord evidence = new NonRepudiationEvidenceRecord(getId(), snapshot, getDataExtension());
                    callback.onEvidenceCollected(evidence);
                }
                cam.close();

            } else {
                callback.onEvidenceCollectionFailed();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            callback.onEvidenceCollectionFailed();
        }
    }

    @Override
    public String getId() {
        return "security-camera-1";
    }

    @Override
    public String getDataExtension() {
        return "jpg";
    }
}
