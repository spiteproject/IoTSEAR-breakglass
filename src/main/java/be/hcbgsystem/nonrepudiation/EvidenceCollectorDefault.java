package be.hcbgsystem.nonrepudiation;

import be.hcbgsystem.core.models.nonrepudiation.NoSuchNonRepudiationProvider;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidence;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidenceRecord;
import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationRequirements;
import be.hcbgsystem.nonrepudiation.providers.EvidenceProducedCallback;
import be.hcbgsystem.nonrepudiation.providers.NonRepudiationProvider;
import be.hcbgsystem.nonrepudiation.resolution.NonRepudiationRequirementsResolver;

import java.util.ArrayList;

public class EvidenceCollectorDefault implements EvidenceCollector {
    private ArrayList<NonRepudiationProvider> providers;
    private NonRepudiationEvidence collectedEvidence;
    private ArrayList<Thread> collectionThreads;

    private NonRepudiationRequirements requirements;
    private NonRepudiationRequirementsResolver nonRepudiationRequirementsResolver;

    public EvidenceCollectorDefault(NonRepudiationRequirementsResolver nonRepudiationRequirementsResolver) {
        providers = new ArrayList<>();
        collectedEvidence = new NonRepudiationEvidence();
        collectionThreads = new ArrayList<>();
        this.nonRepudiationRequirementsResolver = nonRepudiationRequirementsResolver;
    }

    @Override
    public void registerNonRepudiationProvider(NonRepudiationProvider provider) {
        providers.add(provider);
    }

    @Override
    public void unregisterNonRepudiationProvider(NonRepudiationProvider provider) {
        providers.remove(provider);
    }

    @Override
    public void collectEvidence(NonRepudiationRequirements requirements, EvidenceCollectedCallback callback) {
        this.requirements = requirements;
        this.collectedEvidence.clear();

        for (String providerId : requirements.getProviders()) {
            // New thread
            NonRepudiationProvider provider = getProvider(providerId);
            if (provider != null ) {
                Thread collectionThread = new Thread(() -> provider.collectEvidence(new EvidenceProducedCallback() {
                    @Override
                    public void onEvidenceCollected(NonRepudiationEvidenceRecord evidence) {
                        //   if (toCollectFrom.contains(provider)) { // If evidence still required; else evidence might come in while the requirements are already satisfied.
                        newCollected(provider, evidence, callback);
                        //  }
                    }

                    @Override
                    public void onEvidenceCollectionFailed() {
                        collectionFailed(provider);
                    }
                }));

                collectionThreads.add(collectionThread);
                collectionThread.start();
            } else {
                try {
                    throw new NoSuchNonRepudiationProvider(providerId);
                } catch (NoSuchNonRepudiationProvider noSuchNonRepudiationProvider) {
                    noSuchNonRepudiationProvider.printStackTrace();
                }
            }

        }
    }

    private synchronized void collectionFailed(NonRepudiationProvider provider) {
        System.out.println("Collection failed " + provider.toString());
    }

    private synchronized void newCollected(NonRepudiationProvider provider, NonRepudiationEvidenceRecord evidence, EvidenceCollectedCallback callback) {
        collectedEvidence.addEvidence(evidence);

        System.out.println("[EC] Evidence collected: " + provider.getId());

        if (evidenceCollected()) {
            // All evidence collected
            for (Thread t : collectionThreads) {
                // TODO Stop collection of evidence.

            }
            callback.evidenceCollected(collectedEvidence);
        }
    }

    private boolean evidenceCollected() {
        return nonRepudiationRequirementsResolver.resolve(requirements, collectedEvidence);
    }

    private NonRepudiationProvider getProvider(String id) {
        for (NonRepudiationProvider provider : providers) {
            if (provider.getId().equals(id)) {
                return provider;
            }
        }
        return null;
    }


}
