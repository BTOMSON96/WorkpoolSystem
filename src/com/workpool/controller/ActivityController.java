package com.workpool.controller;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.workpool.entity.Activity;
import com.workpool.entity.EntryFolder;
import com.workpool.entity.Resource;
import com.workpool.entity.Task;
import com.workpool.entity.TaskType;
import com.workpool.util.HibernateUtil;

public class ActivityController extends AbstractController {

	public ArrayList<String> validateActivity(Activity activity) throws NullPointerException {

		ArrayList<String> errorMessageList = new ArrayList<>();

		Session session = HibernateUtil.getSessionFactory().openSession();
		// validate activity id
		/*
		 * Activity act = (Activity) getObjectById(Activity.class, activity.getId(),
		 * session);
		 * 
		 * if(act==null) { errorMessageList.add("Activity  id does not exist");
		 * System.out.println("Activity id does not exist"); }
		 */

		// Validating Name
		String title = activity.getTitle();

		if (title == null || title.trim().isEmpty()) {
			errorMessageList.add("no title specified");
			System.out.println("no title specified");
		}

		// validating author
		if (activity.getAuthor() != null) {

			Resource author = (Resource) getObjectById(Resource.class, activity.getAuthor().getId(), session);
			if (author == null) {
				errorMessageList.add("Author id does not exist");
				System.out.println("Author id does not exist");
			}
		} else {
			errorMessageList.add("Author id does not exist");
			System.out.println("Author id does not exist");
		}

		// Validating task type
		if (activity.getType_id() != null) {
			TaskType type = (TaskType) getObjectById(TaskType.class, activity.getType_id().getId(), session);

			if (type == null) {
				errorMessageList.add("Task type id does not exist");
				System.out.println("Task Type id does not exist");
			}
		} else {
			errorMessageList.add("Task type id does not exist");
			System.out.println("Task Type id does not exist");
		}

		// validating task id
		if (activity.getTask_id() != null) {
			Task task = (Task) getObjectById(Task.class, activity.getTask_id().getId(), session);

			if (task == null) {
				errorMessageList.add("Task id does not exist");
				System.out.println("Task id does not exist");
			}
		} else {
			errorMessageList.add("Task id does not exist");
			System.out.println("Task id does not exist");
		}

		// validate entry folder id
		if (activity.getEntry_id() != null) {
			EntryFolder entry = (EntryFolder) getObjectById(EntryFolder.class, activity.getEntry_id().getId(), session);

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

	public Activity createActivity(Activity tempActivity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Activity activity = new Activity();
		try {
			activity.setTitle(tempActivity.getTitle());
			activity.setDescription(tempActivity.getDescription());
			activity.setStart(tempActivity.getStart());
			activity.setEnd(tempActivity.getEnd());
			activity.setAuthor(tempActivity.getAuthor());
			activity.setType_id(tempActivity.getType_id());
			activity.setTask_id(tempActivity.getTask_id());
			activity.setEntry_id(tempActivity.getEntry_id());
			activity.setisOpen(tempActivity.getisOpen());

			session.save(activity);
			transaction.commit();
			System.out.println("Activity created");
            //session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return activity;
	}

	public List<Activity> getAllActivities() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Activity> criteriaQuery = criteriaBuilder.createQuery(Activity.class);
		Root<Activity> root = criteriaQuery.from(Activity.class);

		// select all
		CriteriaQuery<Activity> all = criteriaQuery.select(root);
		// sorting - what is the default order (order by title)
		all.orderBy(criteriaBuilder.asc(root.get("id")));
		TypedQuery<Activity> allQuery = session.createQuery(all);
		 //session.close();
		return allQuery.getResultList();
	}

	public Activity getActivityById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Activity activity = (Activity) getObjectById(Activity.class, id, session);
		session.close();
		return activity;
	}

	public List<Activity> getActivityByTitle(String name) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Activity> query = criteriaBuilder.createQuery(Activity.class);
		Root<Activity> root = query.from(Activity.class);
		// where clause (restrict rows returned)
		query.where(criteriaBuilder.like(root.get("title").as(String.class), name.trim() + "%"));

		// select all
		CriteriaQuery<Activity> all = query.select(root);
		TypedQuery<Activity> allQuery = session.createQuery(all);

		return allQuery.getResultList();

	}

	public Activity updateActivity(Activity activityToUpdate, Session session) {

		try {

			Transaction transaction = session.beginTransaction();
			session.update(activityToUpdate);
			transaction.commit();

			session.close();

			System.out.println("Activity updated");
			return activityToUpdate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void deleteActivity(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaDelete<Activity> delete = criteriaBuilder.createCriteriaDelete(Activity.class);

		// delete from
		Root<Activity> root = delete.from(Activity.class);

		// where clause
		delete.where(criteriaBuilder.equal(root.get("id"), id));

		// execute delete
		session.createQuery(delete).executeUpdate();

		transaction.commit();
		 //session.close();
		System.out.println("Activity deleted");

	}

	/*
	 * -----------------------------HQL Queries------------------------
	 */
	@SuppressWarnings("rawtypes")
	public List<Activity> activitiesCreatedBy(Activity activity) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();

		Query query = session.createQuery("from Activity where author = :author ");
		query.setParameter("author", activity.getAuthor());

		@SuppressWarnings("unchecked")
		List<Activity> results = query.list();
		transaction.commit();
		 //session.close();
		return results;

	}

	public List<Activity> activitiesUnderTask(Activity activity) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();

		Query query = session.createQuery("from Activity where task_id = :task_id ");
		query.setParameter("task_id", activity.getTask_id());

		@SuppressWarnings("unchecked")
		List<Activity> results = query.list();
		transaction.commit();
		//session.close();
		return results;

	}

	@SuppressWarnings("rawtypes")
	public Long countActivities() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Query query = session.createQuery("Select count(id) from Activity");

		Long results = (Long) query.uniqueResult();
		transaction.commit();
		//session.close();
		return results;

	}

	@SuppressWarnings("rawtypes")
	public List<Activity> activityCreatedBetween(Calendar start, Calendar end) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Query query = session.createQuery("FROM Activity where start between :startDate AND :endDate");
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);

		@SuppressWarnings("unchecked")
		List<Activity> results = query.list();
		transaction.commit();
		 //session.close();
		return results;

	}

}

