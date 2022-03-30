package be.hcbgsystem.nonrepudiation.resolution;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidence;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationRequirements;

public interface NonRepudiationRequirementsResolver {
    boolean resolve(NonRepudiationRequirements requirements, NonRepudiationEvidence collected);
}
