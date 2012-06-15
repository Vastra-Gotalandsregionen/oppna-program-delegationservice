/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package se.vgregion.delegation.ws;

import javax.jws.WebService;

import se.riv.authorization.delegation.savedelegations.v1.rivtabp21.SaveDelegationsResponderInterface;
import se.riv.authorization.delegation.savedelegationsresponder.v1.SaveDelegationsResponseType;
import se.riv.authorization.delegation.savedelegationsresponder.v1.SaveDelegationsType;
import se.riv.authorization.delegation.v1.ResultCodeEnum;
import se.vgregion.delegation.DelegationService;
import se.vgregion.delegation.domain.DelegationBlock;
import se.vgregion.delegation.util.DelegationUtil;
import se.vgregion.delegation.ws.util.DelegationServiceUtil;

/**
 * Implementation of service that adds or update a delegation.
 * @author Simon Göransson
 * @author Claes Lundahl
 */
@WebService(
        serviceName = "SaveDelegationsResponderService",
        endpointInterface = "se.riv.authorization.delegation.savedelegations.v1.rivtabp21.SaveDelegationsResponderInterface",
        portName = "SaveDelegationsResponderPort",
        targetNamespace = "urn:riv:authorization:delegation:SaveDelegations:1:rivtabp21",
        wsdlLocation = "schemas/interactions/SaveDelegationsInteraction/SaveDelegationsInteraction_1.0_RIVTABP21.wsdl")
public class SaveDelegationsResponderInterfaceImpl implements SaveDelegationsResponderInterface {

    private DelegationService delegationService;

    /**
     * Default constructor.
     */
    public SaveDelegationsResponderInterfaceImpl() {
        super();
    }

    /**
     * Constructor that allows passing a service object to the instance that will alow it to work with underlying 
     * resources, db etc.
     * @param delegationService the service instance to be used.
     */
    public SaveDelegationsResponderInterfaceImpl(DelegationService delegationService) {
        super();
        this.delegationService = delegationService;

    }

    /**
     * Saves a new post or updates an old one. A new is created if no delegation key is found (one is generated by 
     * the system and returned with the answer).
     * @param logicalAddress is not used currently.
     * @param parameters contains the delegation to save / create.
     * @return an object containing the freshly saved delegation and or an result code.
     */
    @Override
    public SaveDelegationsResponseType saveDelegations(String logicalAddress, SaveDelegationsType parameters) {

        SaveDelegationsResponseType result = new SaveDelegationsResponseType();

        DelegationBlock block = DelegationUtil.toDelegationBlock(parameters.getDelegationBlockType());
        DelegationBlock savedBlock = delegationService.save(block);
        result.setDelegations(DelegationServiceUtil.parseDelegations(savedBlock.getDelegations()));
        result.setResultCode(ResultCodeEnum.OK);

        return result;

    }

}
