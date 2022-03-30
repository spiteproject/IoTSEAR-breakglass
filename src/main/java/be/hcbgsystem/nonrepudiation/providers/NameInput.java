package be.hcbgsystem.nonrepudiation.providers;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidenceRecord;
import org.pf4j.Extension;

import java.util.Scanner;

@Extension
public class NameInput implements NonRepudiationProvider {
    @Override
    public void collectEvidence(EvidenceProducedCallback callback) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        NonRepudiationEvidenceRecord evidence = new NonRepudiationEvidenceRecord(getId(), name.getBytes(), getDataExtension());
        callback.onEvidenceCollected(evidence);
    }

    @Override
    public String getId() {
        return "name-input-1";
    }

    @Override
    public String getDataExtension() {
        return "txt";
    }


}
