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
/*
 * Bidirectional OneToMany Test.
 * Unidirectional OneToMany is inefficient and requires a link table in database.
 * 
 * link table is table which contains the primary keys from two tables that you would like to link together
 * 
 * reference:
 * https://vladmihalcea.com/2017/03/29/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
 * 
 * */
public class OneToManyTest extends TestCase {
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
		
		House h1 = new House();
		h1.setName("Phonix");
		
		Room r1 = new Room();
		r1.setPurpose("Phonix Bedroom");
		r1.setHouse(h1);
		
		House h2 = new House();
		h2.setName("Rose");
		
		Room r2 = new Room();
		r2.setPurpose("Rose bedroom");
		r2.setHouse(h2);
		
		Room r3 = new Room();
		r3.setPurpose("Rose dining room");
		r3.setHouse(h2);
		
		h1.getRooms().add(r1);
		h2.getRooms().add(r2);
		h2.getRooms().add(r3);
		
		session.save(h1);
		session.save(h2);
		
		session.getTransaction().commit();
		session.close();

		session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from House" ).list();
		for ( House house : (List<House>) result ) {
			System.out.println( "House (" + house.getName() + ")" );
			List<Room> rooms = house.getRooms();
			if(rooms != null && !rooms.isEmpty()) {
				for(Room rm : rooms) {
					System.out.println( "Room of House (" + rm.getPurpose() + ", " + rm.getHouse().getName() + ")" );
				}
			}
			
		}
        session.getTransaction().commit();
        session.close();
        
     
	}
}
