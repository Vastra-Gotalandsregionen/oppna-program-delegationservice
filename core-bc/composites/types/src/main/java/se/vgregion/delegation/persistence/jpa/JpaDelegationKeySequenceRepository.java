/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.vgregion.delegation.persistence.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.delegation.domain.DelegationKeySequence;
import se.vgregion.delegation.persistence.DelegationKeySequenceRepository;

/**
 * 
 * @author Simon Göransson
 * @author Claes Lundahl
 */
@Repository
public class JpaDelegationKeySequenceRepository extends DefaultJpaRepository<DelegationKeySequence, Long>
        implements DelegationKeySequenceRepository {

    @Override
    @Transactional
    public Long nextSequenceNumber() {
        DelegationKeySequence dks = new DelegationKeySequence();
        dks = store(dks);
        remove(dks);
        return dks.getId();
    }

}
