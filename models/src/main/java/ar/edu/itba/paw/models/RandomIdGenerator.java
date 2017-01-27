package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class RandomIdGenerator implements IdentifierGenerator{
	private static final int ID_LENGTH = 12;

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		char[] characterArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		char[] userId = new char[ID_LENGTH];
		Random rand = new Random();

		int i = ID_LENGTH - 1;
		while (i >= 0) {
			userId[i] = characterArray[rand.nextInt(characterArray.length)];
			i--;
		}

		return new String(userId);
	}
}