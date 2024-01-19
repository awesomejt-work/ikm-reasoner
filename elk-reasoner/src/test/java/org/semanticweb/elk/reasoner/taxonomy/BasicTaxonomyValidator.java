/**
 * 
 */
package org.semanticweb.elk.reasoner.taxonomy;
/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.elk.owl.interfaces.ElkEntity;
import org.semanticweb.elk.reasoner.taxonomy.DepthFirstSearch.Direction;
import org.semanticweb.elk.reasoner.taxonomy.model.Taxonomy;
import org.semanticweb.elk.reasoner.taxonomy.model.TaxonomyNode;

/**
 * Applies those validators which are simple visitors
 * 
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 * 
 * @param <T> 
 */
public class BasicTaxonomyValidator<T extends ElkEntity> implements TaxonomyValidator<T> {

	private final List<TaxonomyNodeVisitor<T>> visitors_ = new ArrayList<TaxonomyNodeVisitor<T>>();
	
	/**
	 * 
	 */
	public BasicTaxonomyValidator() {
	}
	
	public BasicTaxonomyValidator<T> add(TaxonomyNodeVisitor<T> visitor) {
		visitors_.add(visitor);
		
		return this;
	}

	@Override
	public void validate(final Taxonomy<T> taxonomy) throws InvalidTaxonomyException {
		DepthFirstSearch<T> dfs = new DepthFirstSearch<T>();

		dfs.run(taxonomy.getBottomNode(), Direction.UP, new TaxonomyNodeVisitor<T>() {

			@Override
			public void visit(TaxonomyNode<T> node,
					List<TaxonomyNode<T>> pathFromStart) throws InvalidTaxonomyException {
				for (TaxonomyNodeVisitor<T> visitor : visitors_) {
					visitor.visit(node, pathFromStart);
				}
			}});
	}
}