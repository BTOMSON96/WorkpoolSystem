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
import org.hibernate.query.Query;

import com.workpool.entity.EntryFolder;
import com.workpool.entity.Participant;
import com.workpool.entity.Resource;
import com.workpool.entity.Task;
import com.workpool.entity.TaskType;
import com.workpool.util.HibernateUtil;

public class TaskController extends AbstractController {

	public ArrayList<String> validateTask(Task task) {

		ArrayList<String> errorMessageList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();

		/*
		 * 
		 * //validate task id Task taskId = (Task) getObjectById(Task.class,
		 * task.getId(), session);
		 * 
		 * if(taskId ==null) { errorMessageList.add("Task id does not exist");
		 * System.out.println("Task id does not exist"); }
		 * 
		 */

		// Validating title
		String name = task.getTitle();

		if (name == null || name.trim().isEmpty()) {
			errorMessageList.add("no title specified");
			System.out.println("no title specified");
		}

		// validate owner
		if (task.getOwner_id() != null) {

			Resource owner = (Resource) getObjectById(Resource.class, task.getOwner_id().getId(), session);
			if (owner == null) {
				errorMessageList.add("Owner id does not exist on Resource table");
				System.out.println("Owner id does not exist on Resource table");
			}
		} else {
			errorMessageList.add("Owner id does not exist on Resource table");
			System.out.println("Owner id does not exist on Resource table");
		}

		// validating assigned to
		if (task.getAssigned_to_id() != null) {

			Participant assigned = (Participant) getObjectById(Participant.class, task.getAssigned_to_id().getId(), session);
			if (assigned == null) {
				errorMessageList.add("Assigned to id does not exist on Resource table");
				System.out.println("Assigned to id does not exist on Resource table");
			}
		} else {
			errorMessageList.add("Assigned to id does not exist on Resource table");
			System.out.println("Assigned to id does not exist on Resource table");
		}

		// Validating task type
		if (task.getTask_type_id() != null) {
			TaskType type = (TaskType) getObjectById(TaskType.class, task.getTask_type_id().getId(), session);

			if (type == null) {
				errorMessageList.add("Task type id does not exist");
				System.out.println("Task Type id does not exist");
			}
		} else {
			errorMessageList.add("Task type id does not exist");
			System.out.println("Task Type id does not exist");
		}

		// validate entry folder id
		if (task.getEntry_id() != null) {
			EntryFolder entry = (EntryFolder) getObjectById(EntryFolder.class, task.getEntry_id().getId(), session);

			if (entry == null) {
				errorMessageList.add("Entry folder id does not exist");
				System.out.println("Entry folder id does not exist");
			}
		} else {
			errorMessageList.add("Entry folder id does not exist");
			System.out.println("Entry folder id does not exist");
		}

		return errorMessageList;

	}

	public Task createTask(Task tempTask) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Task task = new Task();
		try {
			task.setTitle(tempTask.getTitle());
			task.setDescription(tempTask.getDescription());
			task.setDate_created(tempTask.getDate_created());
			task.setDate_completed(tempTask.getDate_completed());
			task.setDate_due(tempTask.getDate_due());
			task.setDate_next(tempTask.getDate_next());
			task.setOwner_id(tempTask.getOwner_id());
	
			task.setAssigned_to_id(tempTask.getAssigned_to_id());
			System.out.println("assigned to " +tempTask.getAssigned_to_id());
			task.setTask_type_id(tempTask.getTask_type_id());
			task.setEntry_id(tempTask.getEntry_id());
			task.setStatus(tempTask.getStatus());
			task.setPriority(tempTask.getPriority());

			session.save(task);
			transaction.commit();
			System.out.println("Task created");

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return task;
	}

	public List<Task> getAllTasks() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
		Root<Task> root = criteriaQuery.from(Task.class);

		// select all
		CriteriaQuery<Task> all = criteriaQuery.select(root);
		TypedQuery<Task> allQuery = session.createQuery(all);

		return allQuery.getResultList();
	}

	public Task getTaskById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Task task = (Task) getObjectById(Task.class, id, session);

		return task;
	}

	public List<Task> getTaskByTitle(String title) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Task> query = criteriaBuilder.createQuery(Task.class);
		Root<Task> root = query.from(Task.class);
		// where clause (restrict rows returned)
		query.where(criteriaBuilder.like(root.get("title").as(String.class), title.trim() + "%"));

		// select all
		CriteriaQuery<Task> all = query.select(root);
		TypedQuery<Task> allQuery = session.createQuery(all);

		return allQuery.getResultList();

	}

	public Task updateTask(Task taskToUpdate, Session session) {
		try {

			Transaction transaction = session.beginTransaction();

			session.update(taskToUpdate);
			transaction.commit();
			session.close();

			System.out.println("Task updated");
			return taskToUpdate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void deleteTask(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaDelete<Task> delete = criteriaBuilder.createCriteriaDelete(Task.class);

		// delete from
		Root<Task> root = delete.from(Task.class);

		// where clause
		delete.where(criteriaBuilder.equal(root.get("id"), id));

		// execute delete
		session.createQuery(delete).executeUpdate();
		transaction.commit();
		System.out.println("Task deleted");

	}

	/*
	 * -----------------------------HQL Queries------------------------
	 */
	@SuppressWarnings("rawtypes")
	public List<Task> taskCreatedBy(Task task) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();

		Query query = session.createQuery("from Task where owner = :resource ");
		query.setParameter("resource", task.getOwner_id());
		@SuppressWarnings("unchecked")
		List<Task> results = query.list();
		transaction.commit();
		return results;

	}

	@SuppressWarnings("rawtypes")
	public List<Task> taskAssignedTo(Task task) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();

		Query query = session.createQuery("from Task where assigned_to_id = :resource ");
		query.setParameter("resource", task.getAssigned_to_id());
		@SuppressWarnings("unchecked")
		List<Task> results = query.list();
		transaction.commit();
		return results;

	}
	
	

}
