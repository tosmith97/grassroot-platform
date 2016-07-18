package za.org.grassroot.core.domain;

import org.apache.commons.lang.IncompleteArgumentException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.xml.builders.TermsQueryBuilder;
import org.apache.lucene.search.*;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.annotations.Key;
import org.hibernate.search.filter.FilterKey;
import org.hibernate.search.filter.StandardFilterKey;

import java.util.Objects;
import java.util.Set;

/**
 * Serves to constraint Lucene query results to only entities with given IDs parameter.
 */
public class IdsFilterFactory {
	private Set ids;

	public void setIds(Set ids) {
		this.ids = ids;
	}

	// this should be removed in future versions of Hibernate Search
	@Key
    public FilterKey getKey() {
        StandardFilterKey key = new StandardFilterKey();
        key.addParameter(ids);
        return key;
    }

	@Factory
	public Filter getFilter() {
		if (ids == null || ids.isEmpty()) {
			throw new IncompleteArgumentException("Parameter 'ids' should be set to non-empty set of IDs");
		}
		Query query = constructTermsQuery();
		return new QueryWrapperFilter(query);
	}

	private Query constructTermsQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		for (Object id : ids) {
			Query idQuery = new TermQuery(new Term("id", String.valueOf(id)));
			booleanQuery.add(idQuery, BooleanClause.Occur.SHOULD);
		}
		return booleanQuery;
	}
}
