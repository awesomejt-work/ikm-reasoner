/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2016 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.reasoner.saturation.properties;

import org.semanticweb.elk.owl.interfaces.ElkEntity;

/**
 * The result of transitive reduction that contains an element that is related
 * to every element before the relation is transitive reduced (equivalent to the
 * bottom).
 * 
 * @author Peter Skocovsky
 * 
 * @param <E>
 *            The type of elements whose relation was transitively reduced.
 */
public interface TransitiveReductionOutputExtreme<E extends ElkEntity>
		extends TransitiveReductionOutput<E> {

	E getExtremeMember();

}
