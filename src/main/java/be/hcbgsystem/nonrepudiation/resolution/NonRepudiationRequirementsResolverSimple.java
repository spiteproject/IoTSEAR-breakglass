package be.hcbgsystem.nonrepudiation.resolution;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidence;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidenceRecord;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationRequirements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NonRepudiationRequirementsResolverSimple implements NonRepudiationRequirementsResolver {
    @Override
    public boolean resolve(NonRepudiationRequirements requirements, NonRepudiationEvidence collected) {
        ArrayList<String> ids = requirements.getProviders();
        List<String> collectedIds = collected.getEvidence().stream().map(NonRepudiationEvidenceRecord::getProvider).collect(Collectors.toList());

        collectedIds.forEach((id) -> ids.removeAll(Collections.singletonList(id)));

        return ids.isEmpty();
    }
}
