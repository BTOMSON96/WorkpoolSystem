package com.workpool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.workpool.entity.Group;
import com.workpool.entity.Resource;

public class ValidationUtil {


	public static boolean containsNumber(String name) {
		char[] ch = name.toCharArray();
		for (char c : ch) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSpecialCharacter(String name) {
		Pattern pattern = Pattern.compile("[^a-zA-Z]");
		Matcher matcher = pattern.matcher(name);
		boolean containsChar = matcher.find();
		if (containsChar) {
			return true;
		}
		return false;

	}
	
	public static boolean emailExist(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Resource> query = criteriaBuilder.createQuery(Resource.class);
		Root<Resource> root = query.from(Resource.class);

		// where clause
		query.where(criteriaBuilder.equal(root.get("email"), email));
		CriteriaQuery<Resource> all = query.select(root);
		TypedQuery<Resource> allQuery = session.createQuery(all);

		if (!allQuery.getResultList().isEmpty()) {

			return true;

		}
		return false;

	}

	public static boolean usernameExist(String username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Resource> query = criteriaBuilder.createQuery(Resource.class);
		Root<Resource> root = query.from(Resource.class);

		// where clause
		query.where(criteriaBuilder.equal(root.get("username"), username));
		CriteriaQuery<Resource> allUser = query.select(root);
		TypedQuery<Resource> allQuser = session.createQuery(allUser);

		if (!allQuser.getResultList().isEmpty()) {
			return true;

		}
		return false;

	}

	public static boolean groupNameExist(String name) {
		// create session
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		// check if group name already exists
		CriteriaQuery<Group> query = criteriaBuilder.createQuery(Group.class);
		Root<Group> root = query.from(Group.class);

		// where clause
		query.where(criteriaBuilder.equal(root.get("name"), name));
		CriteriaQuery<Group> all = query.select(root);
		TypedQuery<Group> allQuery = session.createQuery(all);

		if (!allQuery.getResultList().isEmpty()) {
			return true;
		}
		return false;
	}

}
