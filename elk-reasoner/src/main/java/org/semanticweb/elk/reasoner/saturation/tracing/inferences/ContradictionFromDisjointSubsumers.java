/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDisjointClassesAxiom;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.AbstractConclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.DisjointSubsumerImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Contradiction;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.DisjointSubsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Subsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.ConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.ClassInferenceVisitor;

/**
 * Represents a {@link Contradiction} as the result of processing a
 * {@link Subsumer} which occurs in the same {@link IndexedDisjointClassesAxiom}
 * as some previously derived subsumer.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class ContradictionFromDisjointSubsumers extends AbstractConclusion
		implements Contradiction, ClassInference {

	/**
	 * The axiom which causes the contradiction
	 */
	private final IndexedDisjointClassesAxiom axiom_;

	/**
	 * The original {@link ElkAxiom} due to which this axiom was indexed
	 */
	private final ElkAxiom reason_;

	/**
	 * The two members that violate the disjointness axiom
	 */
	private final IndexedClassExpression[] disjointSubsumers_;

	public ContradictionFromDisjointSubsumers(DisjointSubsumer premise,
			IndexedClassExpression[] disjointSubsumers) {
		this.axiom_ = premise.getAxiom();
		this.disjointSubsumers_ = disjointSubsumers;
		this.reason_ = premise.getReason();
	}

	@Override
	public <I, O> O accept(ConclusionVisitor<I, O> visitor, I input) {
		return visitor.visit(this, input);
	}

	@Override
	public String toString() {
		return "Contradiction from disjoint subsumer " + disjointSubsumers_[1]
				+ " using " + disjointSubsumers_[0] + " due to " + reason_;
	}

	@Override
	public <I, O> O acceptTraced(ClassInferenceVisitor<I, O> visitor,
			I parameter) {
		return visitor.visit(this, parameter);
	}

	public DisjointSubsumer[] getPremises() {
		return new DisjointSubsumer[] {
				new DisjointSubsumerImpl(disjointSubsumers_[0], axiom_, reason_),
				new DisjointSubsumerImpl(disjointSubsumers_[1], axiom_, reason_) };
	}

	public ElkAxiom getReason() {
		return reason_;
	}

	@Override
	public IndexedClassExpression getInferenceContextRoot(
			IndexedClassExpression rootWhereStored) {
		return rootWhereStored;
	}

}
