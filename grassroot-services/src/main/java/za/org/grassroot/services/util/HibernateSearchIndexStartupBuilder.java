package za.org.grassroot.services.util;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class HibernateSearchIndexStartupBuilder implements ApplicationListener<ApplicationReadyEvent> {
	private final Logger logger = LoggerFactory.getLogger(HibernateSearchIndexStartupBuilder.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("Building Hibernate Search index...");
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			throw new RuntimeException("Error while building Hibernate Search index");
		}
		logger.info("Building Hibernate Search Index finished");
	}
}
