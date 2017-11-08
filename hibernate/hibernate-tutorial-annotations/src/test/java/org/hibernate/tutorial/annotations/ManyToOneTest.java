/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.tutorial.annotations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import junit.framework.TestCase;

/**
 * Illustrates the use of Hibernate native APIs.  The code here is unchanged from the {@code basic} example, the
 * only difference being the use of annotations to supply the metadata instead of Hibernate mapping files.
 *
 * @author Steve Ebersole
 */
public class ManyToOneTest extends TestCase {
	private SessionFactory sessionFactory;

	@Override
	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	@Override
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage() throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Person p1 = new Person();
		p1.setName("peter");
		Person p2 = new Person();
		p2.setName("mary");

		Phone ph1 = new Phone();
		ph1.setNum("11111111");
		ph1.setPerson(p1);
		
		Phone ph2 = new Phone();
		ph2.setNum("22222222");
		ph2.setPerson(p2);
		
		Phone ph3 = new Phone();
		ph3.setNum("33333333");
		ph3.setPerson(p2);
		
		session.save(p1);
		session.save(p2);
		session.save(ph1);
		session.save(ph2);
		session.save(ph3);
		
		session.getTransaction().commit();
		session.close();

		session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Phone" ).list();
		for ( Phone phone : (List<Phone>) result ) {
			System.out.println( "Phone (" + phone.getNum() + ", " + phone.getPerson().getName() + ")" );
			phone.setPerson(null);
		}
        session.getTransaction().commit();
        session.close();
        
     
	}
}
