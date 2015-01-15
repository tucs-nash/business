package com.tucs.business.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.tucs.business.dao.interfaces.EnParticipantDao;
import com.tucs.core.model.entity.EnControl;
import com.tucs.core.model.entity.EnGroup;
import com.tucs.core.model.entity.EnParticipant;
import com.tucs.core.model.entity.EnUser;

@Repository
public class EnParticipantDaoImpl extends AbstractBaseDao<EnParticipant> implements EnParticipantDao {

	public EnParticipantDaoImpl() {
		super(EnParticipant.class);
	}

	@Override
	public List<EnParticipant> getParticipantsControl(String controlId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EnParticipant> criteriaQuery = criteriaBuilder.createQuery(EnParticipant.class);
		
		Root<EnParticipant> from = criteriaQuery.from(EnParticipant.class);
		Join<EnParticipant, EnGroup> joinGroup = from.join("group");
		
		criteriaQuery.where(criteriaBuilder.equal(joinGroup.get("control"), new EnControl(controlId))
				, criteriaBuilder.equal(joinGroup.get("deleted"), false)
				, criteriaBuilder.isNull(joinGroup.get("groupParent")));
		TypedQuery<EnParticipant> typedQuery = getEntityManager().createQuery(criteriaQuery);		
		
		return typedQuery.getResultList();
	}

	@Override
	public Boolean verifyParticipant(EnUser user, String groupId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        List<Predicate> where = new ArrayList<Predicate>();

        Root<EnParticipant> tableFrom = criteriaQuery.from(EnParticipant.class);

        where.add(criteriaBuilder.equal(tableFrom.get("group"), new EnGroup(groupId)));
        where.add(criteriaBuilder.equal(tableFrom.get("user"), user));
        
        criteriaQuery.select(criteriaBuilder.count(tableFrom));
        criteriaQuery.where(where.toArray(new Predicate[where.size()]));
        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        
        return typedQuery.getSingleResult() > 0;	
     }
	
}
