/**
 * 
 */
package org.semanticweb.owlapitools.proofs.util;
/*
 * #%L
 * OWL API Proofs Model
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2015 Department of Computer Science, University of Oxford
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

import org.semanticweb.owlapitools.proofs.exception.ProofGenerationException;
import org.semanticweb.owlapitools.proofs.expressions.OWLAxiomExpression;
import org.semanticweb.owlapitools.proofs.expressions.OWLExpression;

/**
 * TODO
 * 
 * @author	Pavel Klinov
 * 			pavel.klinov@uni-ulm.de
 *
 */
public class CycleFreeProofRoot extends TransformedOWLAxiomExpression<CycleBlocking> {

	public CycleFreeProofRoot(OWLAxiomExpression root, OWLInferenceGraph iGraph) throws ProofGenerationException {
		this(root, new CycleBlocking(root, iGraph));
	}
	
	private CycleFreeProofRoot(OWLAxiomExpression expr, CycleBlocking f) {
		super(expr, f);
	}

	public CycleFreeProofRoot blockExpression(OWLExpression toBeBlocked) {
		CycleBlocking blocking = new CycleBlocking(toBeBlocked, transformation.getBlockedExpressions(), transformation.getOWLInferenceGraph());
		
		return new CycleFreeProofRoot(expression, blocking);
	}

}
