/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package se.vgregion.delegation.ws;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.riv.authorization.delegation.savedelegations.v1.rivtabp21.SaveDelegationsResponderInterface;
import se.riv.authorization.delegation.savedelegationsresponder.v1.SaveDelegationsResponseType;
import se.riv.authorization.delegation.savedelegationsresponder.v1.SaveDelegationsType;
import se.vgregion.delegation.DelegationService;
import se.vgregion.delegation.domain.DelegationBlock;
import se.vgregion.delegation.util.DelegationUtil;
import se.vgregion.delegation.ws.util.DelegationServiceUtil;
import se.vgregion.ticket.TicketManager;

/**
 * This class was generated by Apache CXF 2.2.2 Wed Apr 18 14:42:43 CEST 2012 Generated source version: 2.2.2
 * 
 */

@WebService(
        serviceName = "SaveDelegationsResponderService",
        endpointInterface = "se.riv.authorization.delegation.savedelegations.v1.rivtabp21.SaveDelegationsResponderInterface",
        portName = "SaveDelegationsResponderPort",
        targetNamespace = "urn:riv:authorization:delegation:SaveDelegations:1:rivtabp21",
        wsdlLocation = "schemas/interactions/SaveDelegationsInteraction/SaveDelegationsInteraction_1.0_RIVTABP21.wsdl")
public class SaveDelegationsResponderInterfaceImpl implements SaveDelegationsResponderInterface {

    DelegationService delegationService;

    public SaveDelegationsResponderInterfaceImpl() {
        super();
    }

    public SaveDelegationsResponderInterfaceImpl(DelegationService delegationService) {
        super();
        this.delegationService = delegationService;

    }

    static private final Logger logger = LoggerFactory.getLogger(SaveDelegationsResponderInterfaceImpl.class);

    @Override
    public SaveDelegationsResponseType saveDelegations(String logicalAddress, SaveDelegationsType parameters) {

        SaveDelegationsResponseType result = new SaveDelegationsResponseType();

        DelegationBlock block = DelegationUtil.toDelegationBlock(parameters.getDelegationBlockType());
        DelegationBlock savedBlock = delegationService.save(block);
        result.setDelegations(DelegationServiceUtil.parseDelegations(savedBlock.getDelegations()));

        return result;

    }

}
