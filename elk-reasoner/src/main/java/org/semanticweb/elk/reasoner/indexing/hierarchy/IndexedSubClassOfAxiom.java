package org.semanticweb.elk.reasoner.indexing.hierarchy;
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

import org.semanticweb.elk.reasoner.indexing.rules.ChainImpl;
import org.semanticweb.elk.reasoner.indexing.rules.ChainMatcher;
import org.semanticweb.elk.reasoner.indexing.rules.CompositionRules;
import org.semanticweb.elk.reasoner.indexing.rules.NewContext;
import org.semanticweb.elk.reasoner.indexing.rules.RuleEngine;
import org.semanticweb.elk.reasoner.saturation.classes.PositiveSuperClassExpression;

public class IndexedSubClassOfAxiom extends IndexedAxiom {

	protected final IndexedClassExpression subClass, superClass;

	protected IndexedSubClassOfAxiom(IndexedClassExpression subClass,
			IndexedClassExpression superClass) {
		this.subClass = subClass;
		this.superClass = superClass;
	}

	@Override
	protected void updateOccurrenceNumbers(int increment) {

		if (increment > 0) {
			registerCompositionRule();
		} else {
			deregisterCompositionRule();
		}
	}

	public void registerCompositionRule() {
		CompositionRuleMatcher matcher = new CompositionRuleMatcher();
		subClass.getCreate(matcher).addToldSuperClassExpression(superClass);
	}

	public void deregisterCompositionRule() {
		CompositionRuleMatcher matcher = new CompositionRuleMatcher();
		ThisCompositionRule rule = subClass.find(matcher);
		if (rule != null) {
			rule.removeToldSuperClassExpression(superClass);
			if (rule.isEmpty())
				subClass.remove(matcher);
		} else {
			// TODO: throw/log something, this should never happen
		}
	}

	private static class ThisCompositionRule extends ChainImpl<CompositionRules>
			implements CompositionRules {

		/**
		 * Correctness of axioms deletions requires that
		 * toldSuperClassExpressions is a List.
		 */
		private List<IndexedClassExpression> toldSuperClassExpressions_;

		ThisCompositionRule(CompositionRules tail) {
			super(tail);
			this.toldSuperClassExpressions_ = new ArrayList<IndexedClassExpression>(
					1);
		}

		protected void addToldSuperClassExpression(
				IndexedClassExpression superClassExpression) {
			toldSuperClassExpressions_.add(superClassExpression);
		}

		/**
		 * @param superClassExpression
		 * @return true if successfully removed
		 */
		protected void removeToldSuperClassExpression(
				IndexedClassExpression superClassExpression) {
			toldSuperClassExpressions_.remove(superClassExpression);
		}

		/**
		 * @return {@code true} if this rule never does anything
		 */
		private boolean isEmpty() {
			return toldSuperClassExpressions_.isEmpty();
		}

		@Override
		public void apply(RuleEngine ruleEngine, NewContext context) {

			for (IndexedClassExpression implied : toldSuperClassExpressions_)
				ruleEngine.derive(context, new PositiveSuperClassExpression(
						implied));
		}

	}

	private class CompositionRuleMatcher implements
			ChainMatcher<CompositionRules, ThisCompositionRule> {

		@Override
		public ThisCompositionRule createNew(CompositionRules tail) {
			return new ThisCompositionRule(tail);
		}

		@Override
		public ThisCompositionRule match(CompositionRules chain) {
			if (chain instanceof ThisCompositionRule)
				return (ThisCompositionRule) chain;
			else
				return null;
		}

	}

}