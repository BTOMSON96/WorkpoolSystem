package com.workpool.controller;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AbstractController {

	@SuppressWarnings("rawtypes")
	public static Object getObjectById(Class c, long id, Session session) {

		// Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Query query = session.createQuery("from  " + c.getName() + " where id=" + id);
		if (query.getResultList().isEmpty()) {
			return null;
		} else {

			transaction.commit();
			return query.getResultList().get(0);
		}

	}

	public static String encryptPassword(String password) throws NoSuchAlgorithmException {
		String encrypted = null;

		MessageDigest message = MessageDigest.getInstance("MD5");
		message.update(password.getBytes());
		byte[] bytes = message.digest();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		encrypted = builder.toString();

		return encrypted;
	}

}
