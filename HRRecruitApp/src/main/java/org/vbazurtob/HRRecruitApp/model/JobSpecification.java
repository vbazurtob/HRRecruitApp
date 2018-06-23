package org.vbazurtob.HRRecruitApp.model;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.springframework.data.jpa.domain.Specification;

public class JobSpecification implements Specification<Job> {

	private final Job criteriaFilterObj;
	
	public JobSpecification(Job c) {
		criteriaFilterObj = c;
	}

	@Override
	public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder cb) {


		ArrayList<Predicate> predicates = new ArrayList<>();
		
		Predicate predicateReturn = cb.conjunction();
		
		Predicate titleP, statusP, jobTypeP, returnP;
		if( criteriaFilterObj.getTitle() != null  ) {
			
			System.out.println("Root " + root.get(Job_.title )  );;
			
			titleP =  cb.like(root.get(Job_.title ) , criteriaFilterObj.getTitle());
//			predicates.add(titleP);
		
			predicateReturn = cb.or(predicateReturn, titleP);
		}
		
		if( criteriaFilterObj.getJobType() != null  ) {			
			jobTypeP = cb.equal(root.get(Job_.jobType) , criteriaFilterObj.getJobType());
//			predicates.add(jobTypeP);
			
			predicateReturn = cb.or(predicateReturn, jobTypeP);
		}
		
		if(criteriaFilterObj.getStatus() != null) {
			String status;
			if( criteriaFilterObj.equals("Open") ) {
				status = "O";
			}else if(criteriaFilterObj.equals("C")) {
				status = "C";
			}else {
				status = "A";
			}
			
			if( !status.equals("A")) {
				statusP = cb.equal(root.get(Job_.status), status);
//				predicates.add(statusP);
				predicateReturn = cb.or(predicateReturn, statusP);
			}
			
		}
		
		System.out.println("Predicate : " + predicateReturn.toString());
		
		return predicateReturn;
		
//		return query.where( predicates.toArray(new Predicate[predicates.size()] )   );
	}

}
