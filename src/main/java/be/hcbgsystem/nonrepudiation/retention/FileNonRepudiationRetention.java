package be.hcbgsystem.nonrepudiation.retention;

import be.hcbgsystem.core.models.nonrepudiation.FileNonRepudiationEvidence;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidence;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidenceRecord;
import be.hcbgsystem.core.models.nonrepudiation.RetainedNonRepudiationEvidence;

import java.io.*;

public class FileNonRepudiationRetention implements EvidenceRetention {
    @Override
    public RetainedNonRepudiationEvidence retainEvidence(NonRepudiationEvidence evidence) {
        FileNonRepudiationEvidence retained = new FileNonRepudiationEvidence();

        File dir = new File("evidence/" + evidence.getUid());
        dir.mkdirs();

        for (NonRepudiationEvidenceRecord e : evidence.getEvidence()) {
            String loc = "evidence/" + evidence.getUid() + "/" + e.getProvider() + "." + e.getExtension();
            File evidenceFile = new File(loc);
            try {
                evidenceFile.createNewFile();
                OutputStream outputStream = new FileOutputStream(evidenceFile);
                outputStream.write(e.getEvidence());
                outputStream.close();

                retained.addEvidence(e.getProvider(), loc);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return retained;
    }
}
