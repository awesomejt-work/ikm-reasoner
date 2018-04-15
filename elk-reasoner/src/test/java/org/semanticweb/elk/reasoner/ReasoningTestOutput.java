package org.semanticweb.elk.reasoner;

/*-
 * #%L
 * ELK Reasoner Core
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2018 Department of Computer Science, University of Oxford
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

/**
 * A reasoning output with the information about completeness of the result
 * 
 * @author Yevgeny Kazakov
 *
 * @param <R>
 *            the type of the reasoning result
 */
public interface ReasoningTestOutput<R> {
	
	/**
	 * @return the reasoning result represented by this output
	 */
	public R getResult();

	/**
	 * @return the information whether the reasoning result is complete
	 */
	public boolean isComplete();

}
