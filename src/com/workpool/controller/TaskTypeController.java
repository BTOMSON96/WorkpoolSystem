package com.workpool.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.workpool.entity.TaskType;
import com.workpool.util.HibernateUtil;
import com.workpool.util.ValidationUtil;

public class TaskTypeController extends AbstractController {

	public ArrayList<String> validateTaskType(TaskType type) {

		ArrayList<String> errorMessageList = new ArrayList<>();
		// create session
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		/*
		 * //validating task type id TaskType typeId = (TaskType)
		 * getObjectById(TaskType.class, type.getId(), session);
		 * 
		 * if(typeId==null) { errorMessageList.add("Task type id does not exist");
		 * System.out.println("Task Type id does not exist"); }
		 * 
		 */

		// Validating Name
		String name = type.getName();

		if (name == null || name.trim().isEmpty()) {
			errorMessageList.add("no task type name specified");
		}

		if (ValidationUtil.containsNumber(name)) {
			errorMessageList.add("Task type name must not contain number/s");
		}

		// check if task type name already exists
		CriteriaQuery<TaskType> query = criteriaBuilder.createQuery(TaskType.class);
		Root<TaskType> root = query.from(TaskType.class);

		// where clause
		query.where(criteriaBuilder.equal(root.get("name"), name));
		CriteriaQuery<TaskType> all = query.select(root);
		TypedQuery<TaskType> allQuery = session.createQuery(all);

		if (!allQuery.getResultList().isEmpty()) {

			errorMessageList.add("Task  classification name already exist");
			System.out.println("Task  classification name already exist");
		}

		return errorMessageList;
	}

	public TaskType createTaskType(TaskType tempType) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		TaskType type = new TaskType();
		try {

			type.setName(tempType.getName());

			session.save(type);
			transaction.commit();
			System.out.println("Task type created");

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return type;
	}

	public List<TaskType> getAllTaskType() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<TaskType> criteriaQuery = criteriaBuilder.createQuery(TaskType.class);
		Root<TaskType> root = criteriaQuery.from(TaskType.class);

		// select all
		CriteriaQuery<TaskType> all = criteriaQuery.select(root);
		TypedQuery<TaskType> allQuery = session.createQuery(all);

		return allQuery.getResultList();
	}

	public TaskType getTaskTypeById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		TaskType type = (TaskType) getObjectById(TaskType.class, id, session);
		return type;
	}

	public List<TaskType> getTaskTypeByName(String name) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<TaskType> query = criteriaBuilder.createQuery(TaskType.class);
		Root<TaskType> root = query.from(TaskType.class);
		// where clause (restrict rows returned)
		query.where(criteriaBuilder.like(root.get("name").as(String.class), name.trim() + "%"));

		// select all
		CriteriaQuery<TaskType> all = query.select(root);
		TypedQuery<TaskType> allQuery = session.createQuery(all);

		return allQuery.getResultList();

	}

	public TaskType updateTaskType(TaskType typeToUpdate, Session session) {

		try {

			Transaction transaction = session.beginTransaction();
			session.update(typeToUpdate);
			transaction.commit();

			session.close();

			System.out.println("TaskType  updated");
			return typeToUpdate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void deleteTaskType(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaDelete<TaskType> delete = criteriaBuilder.createCriteriaDelete(TaskType.class);

		// delete from
		Root<TaskType> root = delete.from(TaskType.class);

		// where clause
		delete.where(criteriaBuilder.equal(root.get("id"), id));

		// execute delete
		session.createQuery(delete).executeUpdate();
		transaction.commit();
		System.out.println("Task type deleted");

	}

}
