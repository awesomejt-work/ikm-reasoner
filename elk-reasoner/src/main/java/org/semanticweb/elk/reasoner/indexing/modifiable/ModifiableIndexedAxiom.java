package org.semanticweb.elk.reasoner.indexing.modifiable;

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

import org.semanticweb.elk.owl.interfaces.ElkAxiom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedAxiom;

/**
 * An {@link IndexedAxiom} that can be modified as a result of updating the
 * {@link ModifiableOntologyIndex} where this object is stored.
 * 
 * @author "Yevgeny Kazakov"
 *
 */
public interface ModifiableIndexedAxiom extends ModifiableIndexedObject,
		IndexedAxiom {

	/**
	 * Adds this {@link ModifiableIndexedAxiom} once to the given
	 * {@link ModifiableOntologyIndex}
	 * 
	 * @param index
	 *            the {@link ModifiableOntologyIndex} to which this
	 *            {@link ModifiableIndexedAxiom} should be added
	 * @param reason
	 *            the {@link ElkAxiom} that is responsible for this
	 *            {@link ModifiableIndexedAxiom}
	 * @return {@code true} if this operation was successful and {@code false}
	 *         otherwise; if {@code false} is returned, the index should not
	 *         logically change as the result of calling this method
	 */
	boolean addOccurrence(ModifiableOntologyIndex index, ElkAxiom reason);

	/**
	 * Removes this {@link ModifiableIndexedAxiom} once from the given
	 * {@link ModifiableOntologyIndex}
	 * 
	 * @param index
	 *            the {@link ModifiableOntologyIndex} from which this
	 *            {@link ModifiableIndexedAxiom} should be removed
	 * @param reason
	 *            the {@link ElkAxiom} that is responsible for this
	 *            {@link ModifiableIndexedAxiom}
	 * @return {@code true} if this operation was successful and {@code false}
	 *         otherwise; if {@code false} is returned, the index should not
	 *         logically change as the result of calling this method
	 */
	boolean removeOccurrence(ModifiableOntologyIndex index, ElkAxiom reason);

}
