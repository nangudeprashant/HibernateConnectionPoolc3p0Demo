package com.javaLive.databaseUtil;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author JavaLive.com 
 *         This is a project on how to use C3P0 connection pool
 *         framework with Hibernate. Basically what a connection pool does is to
 *         create a number of connections (a pool of connections) with the
 *         database server and keep them idle. Every time a query comes up the
 *         application picks one of the pooled connections and uses that to
 *         interact with the database. Connection pools substantially help
 *         performance because your application doesn’t have to create a new
 *         connection to the database server every time a query is submitted.
 * 
 *         It can use one of the already established ones from the connection
 *         pool. Additionally if your already established connections are not
 *         enough, it can automatically create more connections to satisfy as
 *         much requests as possible. Hibernate has a connection pooling
 *         mechanism as standard, but it’s not very useful for production use
 *         and for applications that have to deal with frequent and time
 *         consuming database interaction.
 */
public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				// Create registry
				registry = new StandardServiceRegistryBuilder().configure().build();
				// Create MetadataSources
				MetadataSources sources = new MetadataSources(registry);
				// Create Metadata
				Metadata metadata = sources.getMetadataBuilder().build();
				// Create SessionFactory
				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}
	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
