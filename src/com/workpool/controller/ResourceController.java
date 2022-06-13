package com.workpool.controller;

import java.security.NoSuchAlgorithmException;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import org.hibernate.*;

import org.hibernate.query.Query;

import com.workpool.entity.*;
import com.workpool.util.HibernateUtil;
import com.workpool.util.ValidationUtil;

public class ResourceController extends AbstractController {

	@SuppressWarnings({ "static-access" })
	public ArrayList<String> validateResource(Resource resource) {

		ArrayList<String> errorMessageList = new ArrayList<>();

		// validate resource id
		/*
		 * Resource resourceId = (Resource) getObjectById(Resource.class,
		 * resource.getId(), session);
		 * 
		 * if(resourceId==null) { errorMessageList.add("Resource id does not exist");
		 * 
		 * }
		 * 
		 * 
		 */

		// Validating Name
		if (resource.getFirstname() != null) {
			String name = resource.getFirstname();

			if (name == null || name.trim().isEmpty()) {
				errorMessageList.add("no name specified");
				System.out.println("no name specified");
			}
			if (name.trim().length() > 30) {

				errorMessageList.add("Name too long");

			}

			if (ValidationUtil.containsNumber(name) == true) {
				errorMessageList.add("firstname must not contain number/s");

			}

			if (ValidationUtil.isSpecialCharacter(name)) {
				errorMessageList.add("firstname must not contain special characters");

			}
		}

		// validate surname
		if (resource.getLastname() != null) {
			String surname = resource.getLastname();

			if (surname == null || surname.trim().isEmpty()) {
				errorMessageList.add("no lastname specified");

			}
			if (surname.trim().length() > 30) {

				errorMessageList.add("Surname too long");

			}
			if (ValidationUtil.containsNumber(surname) == true) {
				errorMessageList.add("lastname must not contain number/s");
			}

			if (ValidationUtil.isSpecialCharacter(surname)) {
				errorMessageList.add("lastname must not contain special characters");

			}
		}
		// validate DOB
		if (resource.getDob() != null) {
			Calendar today = Calendar.getInstance();
			Calendar future = Calendar.getInstance();
			Calendar calendar = resource.getDob();

			future.set(Calendar.YEAR, 2022);

			if (calendar.after(future)) {
				errorMessageList.add("DOB can not be a future date");

			}

			// get only year, month and day of month from today's date and DOB,, to check if
			// they are equal
			if (calendar.get(calendar.YEAR) == today.get(today.YEAR)
					&& calendar.get(calendar.MONTH) == today.get(today.MONTH)
					&& calendar.get(calendar.DAY_OF_MONTH) == today.get(today.DAY_OF_MONTH)) {
				errorMessageList.add("DOB can not be a today's date");

			}
		}
		// Validating address
		if (resource.getAddress() != null) {
			String address = resource.getAddress();

			if (address == null || address.trim().isEmpty()) {

				errorMessageList.add("address can't be null");

			}
		}

		// Validate email
		if (resource.getEmail() != null) {
			String emailPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
			String email = resource.getEmail();

			if (email == null || email.matches(emailPattern) == false) {

				errorMessageList.add("Invalid email address");

			}

			if (ValidationUtil.emailExist(email) == true) {

				errorMessageList.add("Email already exist");

			}
		}

		// validate username
		if (resource.getUsername() != null) {
			String userName = resource.getUsername();
			if (userName == null || userName.trim().isEmpty()) {

				errorMessageList.add("no username specified");

			}

			if (ValidationUtil.usernameExist(userName) == true) {
				errorMessageList.add("username already exist");

			}
		}

		// Validating Password
		if (resource.getPassword() != null) {
			String password = resource.getPassword();

			if (password == null || password.trim().isEmpty()) {

				errorMessageList.add("no password specified");

			}

			if (password.trim().length() > 13) {
				errorMessageList.add("password must not exceed 13 characters");

			}
			String sPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

			if (password.matches(sPattern) == false) {
				errorMessageList.add(
						"password needs to include both lower and uppercase characters, atleast one number, one special character and must be atleast 8 characters long");

			}
		}
		return errorMessageList;
	}

	public Resource createResource(Resource tempResource) throws NoSuchAlgorithmException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Resource resource = new Resource();
		try {

			resource.setFirstname(tempResource.getFirstname());
			resource.setLastname(tempResource.getLastname());
			resource.setDob(tempResource.getDob());
			resource.setAddress(tempResource.getAddress());
			resource.setEmail(tempResource.getEmail());
			resource.setUsername(tempResource.getUsername());
			resource.setPassword(encryptPassword(tempResource.getPassword()));
			resource.setManager(tempResource.getManager());
			resource.setType(tempResource.getType());
			resource.setisActive(tempResource.getisActive());
            resource.setisAdmin(tempResource.getisAdmin());
            
			session.save(resource);
			transaction.commit();
			System.out.println("INFO: Resource created");

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return resource;
	}

	public List<Resource> getAllResource() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Resource> criteriaQuery = criteriaBuilder.createQuery(Resource.class);
		Root<Resource> root = criteriaQuery.from(Resource.class);

		// select all
		CriteriaQuery<Resource> all = criteriaQuery.select(root);
		TypedQuery<Resource> allQuery = session.createQuery(all);

		return allQuery.getResultList();
	}

	public Resource getResourceById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Resource resource = (Resource) getObjectById(Resource.class, id, session);
		// System.out.println(resource.toString());
		
		//session.close();
		return resource;
	}

	public List<Resource> getResourcesByName(String name) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Resource> query = criteriaBuilder.createQuery(Resource.class);
		Root<Resource> root = query.from(Resource.class);
		// where clause (restrict rows returned)
		query.where(criteriaBuilder.or(criteriaBuilder.like(root.get("name").as(String.class), name.trim() + "%"),
				criteriaBuilder.like(root.get("lastName").as(String.class), name.trim() + "%"))

		);

		// select all
		CriteriaQuery<Resource> all = query.select(root);
		TypedQuery<Resource> allQuery = session.createQuery(all);

		return allQuery.getResultList();

	}

	public Resource updateResource(Resource resourceToUpdate, Session session) {

		try {

			Transaction transaction = session.beginTransaction();

			session.update(resourceToUpdate);
			transaction.commit();
			session.close();

			System.out.println("INFO: Resource updated");
			return resourceToUpdate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void deleteResource(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaDelete<Resource> delete = criteriaBuilder.createCriteriaDelete(Resource.class);

		// delete from
		Root<Resource> root = delete.from(Resource.class);

		// where clause
		delete.where(criteriaBuilder.equal(root.get("id"), id));

		// execute delete
		session.createQuery(delete).executeUpdate();
		transaction.commit();
		System.out.println("INFO: Resource deleted");

	}

	/*
	 * -----------------------------HQL Queries------------------------
	 */

	@SuppressWarnings("rawtypes")
	public List<Resource> getByUsernameAndPassword(String username, String password) throws NoSuchAlgorithmException {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Query query = session.createQuery("FROM Resource where username = :username AND password = :password");
		query.setParameter("username", username);
		query.setParameter("password", encryptPassword(password));

		@SuppressWarnings("unchecked")
		List<Resource> results = query.list();
		transaction.commit();
		return results;

	}
	
	@SuppressWarnings("rawtypes")
	public List<Resource> resourcesManaged(Resource resource) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();

		Query query = session.createQuery("FROM Resource where manager = :resource ");
		query.setParameter("resource", resource.getManager());
		@SuppressWarnings("unchecked")
		List<Resource> results = query.list();
		transaction.commit();
		return results;

	}

	@SuppressWarnings("rawtypes")
	public List<Resource> activeResources() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();

		Query query = session.createQuery("FROM Resource where active = 1");
		@SuppressWarnings("unchecked")
		List<Resource> results = query.list();
		transaction.commit();
		return results;

	}

	@SuppressWarnings("rawtypes")
	public List<Resource> inactiveResources() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();

		Query query = session.createQuery("FROM Resource where active = 0");
		@SuppressWarnings("unchecked")
		List<Resource> results = query.list();
		transaction.commit();
		return results;

	}
}
