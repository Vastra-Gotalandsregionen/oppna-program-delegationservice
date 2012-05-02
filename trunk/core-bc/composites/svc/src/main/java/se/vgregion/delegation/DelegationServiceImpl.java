package se.vgregion.delegation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.vgregion.delegation.domain.Delegation;
import se.vgregion.delegation.domain.DelegationBlock;
import se.vgregion.delegation.domain.DelegationStatus;
import se.vgregion.delegation.persistence.DelegationBlockRepository;
import se.vgregion.delegation.persistence.DelegationKeySequenceRepository;
import se.vgregion.delegation.persistence.DelegationRepository;

/**
 * @author Simon Göransson
 * @author Claes Lundahl
 * 
 */
public class DelegationServiceImpl implements DelegationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DelegationServiceImpl.class);

    @Autowired
    private DelegationBlockRepository delegationBlockRepository;

    @Autowired(required = false)
    private DelegationRepository delegationRepository;

    @Autowired(required = false)
    private DelegationKeySequenceRepository delegationKeySequenceRepository;

    @Override
    public List<Delegation> getActiveDelegations(String delegatedFor) {
        return delegationRepository.getActiveDelegations(delegatedFor);
    }

    @Override
    public List<Delegation> getInActiveDelegations(String delegatedFor) {
        return delegationRepository.getInActiveDelegations(delegatedFor);
    }

    @Override
    public List<Delegation> getDelegations(String delegatedFor) {
        return delegationRepository.getDelegations(delegatedFor);
    }

    @Override
    public List<Delegation> getDelegationsByRole(String delegatedTo, String role) {
        return delegationRepository.getDelegationsByRole(delegatedTo, role);
    }

    @Override
    public Delegation getDelegation(Long delegationId) {
        return delegationRepository.getDelegation(delegationId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DelegationBlock save(DelegationBlock delegationBlock) {
        delegationBlock.setId(null);

        if (validateSigning(delegationBlock)) {
            List<Delegation> old = new ArrayList<Delegation>();

            for (Delegation delegation : new HashSet<Delegation>(delegationBlock.getDelegations())) {

                delegation.setStatus(DelegationStatus.ACTIVE);

                if (delegation.getDelegationKey() == null || delegation.getDelegationKey() == null) {
                    delegation.setDelegationKey(delegationKeySequenceRepository.nextSequenceNumber());
                } else {
                    Delegation fresh = new Delegation();
                    new BeanMap(fresh).putAllWriteable(new BeanMap(delegation));
                    fresh.setId(null);

                    old.add(delegation);
                    delegationBlock.getDelegations().remove(delegation);
                    delegationBlock.getDelegations().add(fresh);
                }
            }

            for (Delegation delegation : old) {
                Delegation stored = delegationRepository.find(delegation.getId());
                if (stored != null) {
                    stored.setStatus(DelegationStatus.DELETED);
                    delegationRepository.store(stored);
                }
            }

            DelegationBlock result = delegationBlockRepository.store(delegationBlock);

            // delegationBlockRepository.flush();

            return result;
        } else {
            throw new NotValidChecksumException();
        }
    }

    @Override
    public boolean hasDelegations(String delegatedFor, String delegatedTo, String role) {
        return delegationRepository.hasDelegations(delegatedFor, delegatedTo, role);
    }

    /**
     * Validate that signToken is signed by signer.
     * 
     * @param delegation
     * @return
     */
    private boolean validateSigning(DelegationBlock delegation) {
        // TODO check if the signtoken is valid using the signservice.
        if (delegation.getSignToken() != null) {
            return true;
        }
        return false;
    }

}