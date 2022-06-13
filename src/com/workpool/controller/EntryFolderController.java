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

import com.workpool.entity.EntryFolder;
import com.workpool.util.HibernateUtil;

public class EntryFolderController extends AbstractController {

	public ArrayList<String> validateEntry(EntryFolder entry) {

		ArrayList<String> errorMessageList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		// validate entry folder id
		EntryFolder folder = (EntryFolder) getObjectById(EntryFolder.class, entry.getId(), session);

		if (folder == null) {
			errorMessageList.add("Activity  id does not exist");
			System.out.println("Activity id does not exist");
		}

		// Validating Name
		String name = entry.getName();

		if (name == null || name.trim().isEmpty()) {
			errorMessageList.add("no entry name specified");
			System.out.println("no entry name specified");
		}

		// check if entry name already exists
		CriteriaQuery<EntryFolder> query = criteriaBuilder.createQuery(EntryFolder.class);
		Root<EntryFolder> root = query.from(EntryFolder.class);

		// where clause
		query.where(criteriaBuilder.equal(root.get("name"), name));
		CriteriaQuery<EntryFolder> all = query.select(root);
		TypedQuery<EntryFolder> allQuery = session.createQuery(all);

		if (!allQuery.getResultList().isEmpty()) {

			errorMessageList.add("Entry folder name already exist");
			System.out.println("Entry folder name already exist");
		}

		return errorMessageList;
	}

	public EntryFolder createEntry(EntryFolder tempEntry) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		EntryFolder entry = new EntryFolder();
		try {

			entry.setName(tempEntry.getName());
			entry.setDate_created(tempEntry.getDate_created());
			entry.setDate_modified(tempEntry.getDate_modified());

			session.save(entry);
			transaction.commit();
			 //session.close();
			System.out.println("Entry Folder created");

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return entry;
	}

	public List<EntryFolder> getAllEntryFolders() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<EntryFolder> criteriaQuery = criteriaBuilder.createQuery(EntryFolder.class);
		Root<EntryFolder> root = criteriaQuery.from(EntryFolder.class);

		// select all
		CriteriaQuery<EntryFolder> all = criteriaQuery.select(root);
		TypedQuery<EntryFolder> allQuery = session.createQuery(all);
		 //session.close();
		return allQuery.getResultList();
	}

	public EntryFolder getEntryById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntryFolder folder = (EntryFolder) getObjectById(EntryFolder.class, id, session);
		//session.close();
		return folder;
	}

	public List<EntryFolder> getEntryFolderByName(String name) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<EntryFolder> query = criteriaBuilder.createQuery(EntryFolder.class);
		Root<EntryFolder> root = query.from(EntryFolder.class);
		// where clause (restrict rows returned)
		query.where(criteriaBuilder.like(root.get("name").as(String.class), name.trim() + "%"));

		// select all
		CriteriaQuery<EntryFolder> all = query.select(root);
		TypedQuery<EntryFolder> allQuery = session.createQuery(all);
		 //session.close();
		return allQuery.getResultList();

	}

	public EntryFolder updateEntry(EntryFolder entryToUpdate, Session session) {

		try {

			Transaction transaction = session.beginTransaction();

			session.update(entryToUpdate);
			transaction.commit();
			session.close();

			System.out.println("Entry folder updated");
			return entryToUpdate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void deleteEntry(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaDelete<EntryFolder> delete = criteriaBuilder.createCriteriaDelete(EntryFolder.class);

		// delete from
		Root<EntryFolder> root = delete.from(EntryFolder.class);

		// where clause
		delete.where(criteriaBuilder.equal(root.get("id"), id));

		// execute delete
		session.createQuery(delete).executeUpdate();
		transaction.commit();
		 //session.close();
		System.out.println("Entry folder deleted");

	}

}
