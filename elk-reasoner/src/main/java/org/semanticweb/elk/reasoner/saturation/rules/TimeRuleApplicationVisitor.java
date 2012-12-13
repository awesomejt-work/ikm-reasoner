/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.rules;
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClass;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClass.OwlThingContextInitializationRule;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDataHasValue;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDisjointnessAxiom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectIntersectionOf;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedSubClassOfAxiom;
import org.semanticweb.elk.reasoner.saturation.SaturationState.Writer;
import org.semanticweb.elk.reasoner.saturation.conclusions.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.Contradiction.BottomBackwardLinkRule;
import org.semanticweb.elk.reasoner.saturation.conclusions.ForwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.Propagation;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.util.logging.CachedTimeThread;

/**
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class TimeRuleApplicationVisitor {

	static RuleApplicationVisitor getTimeCompositionRuleApplicationVisitor(final RuleApplicationVisitor ruleAppVisitor, final RuleStatistics ruleStats) {
		return new RuleApplicationVisitor() {
			
			@Override
			public void visit(
					OwlThingContextInitializationRule rule,
					Writer writer, Context context) {
				//TODO
				ruleAppVisitor.visit(rule, writer, context);
			}

			@Override
			public void visit(IndexedDisjointnessAxiom.ThisCompositionRule rule, Writer writer,
					Context context) {
				//TODO
				ruleAppVisitor.visit(rule, writer, context);
			}

			@Override
			public void visit(
					IndexedObjectIntersectionOf.ThisCompositionRule rule,
					Writer writer, Context context) {
				ruleStats.timeObjectIntersectionOfCompositionRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(rule, writer, context);
				ruleStats.timeObjectIntersectionOfCompositionRule += CachedTimeThread.currentTimeMillis;
			}

			@Override
			public void visit(
					IndexedSubClassOfAxiom.ThisCompositionRule rule,
					Writer writer, Context context) {
				ruleStats.timeSubClassOfRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(rule, writer, context);
				ruleStats.timeSubClassOfRule += CachedTimeThread.currentTimeMillis;
			}

			@Override
			public void visit(
					IndexedObjectSomeValuesFrom.ThisCompositionRule rule,
					Writer writer, Context context) {
				ruleStats.timeObjectSomeValuesFromCompositionRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(rule, writer, context);
				ruleStats.timeObjectSomeValuesFromCompositionRule += CachedTimeThread.currentTimeMillis;
			}

			@Override
			public void visit(IndexedDisjointnessAxiom.ThisContradictionRule rule,
					Writer writer, Context context) {
				//TODO
				ruleAppVisitor.visit(rule, writer, context);
			}

			@Override
			public void visit(ForwardLink.ThisBackwardLinkRule rule, Writer writer,
					BackwardLink backwardLink) {
				//TODO
				ruleAppVisitor.visit(rule, writer, backwardLink);
			}

			@Override
			public void visit(
					Propagation.ThisBackwardLinkRule rule,
					Writer writer, BackwardLink backwardLink) {
				ruleStats.timePropagationBackwardLinkRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(rule, writer, backwardLink);
				ruleStats.timePropagationBackwardLinkRule += CachedTimeThread.currentTimeMillis;
			}

			@Override
			public void visit(BottomBackwardLinkRule rule,
					Writer writer, BackwardLink backwardLink) {
				ruleStats.timeContradictionBackwardLinkRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(rule, writer, backwardLink);
				ruleStats.timeContradictionBackwardLinkRule += CachedTimeThread.currentTimeMillis;
			}
		};
	}
	
	static DecompositionRuleApplicationVisitor getTimeDecompositionRuleApplicationVisitor(final DecompositionRuleApplicationVisitor ruleAppVisitor, final RuleStatistics ruleStats) {
		return new DecompositionRuleApplicationVisitor() {
			
			@Override
			public void visit(IndexedClass ice, Writer writer, Context context) {
				ruleStats.timeClassDecompositionRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(ice, writer, context);
				ruleStats.timeObjectSomeValuesFromCompositionRule += CachedTimeThread.currentTimeMillis;
			}

			@Override
			public void visit(IndexedDataHasValue ice, Writer writer, Context context) {
				// TODO Auto-generated method stub
				ruleAppVisitor.visit(ice, writer, context);
			}

			@Override
			public void visit(IndexedObjectIntersectionOf ice, Writer writer,
					Context context) {
				ruleStats.timeObjectIntersectionOfDecompositionRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(ice, writer, context);
				ruleStats.timeObjectIntersectionOfDecompositionRule += CachedTimeThread.currentTimeMillis;
			}

			@Override
			public void visit(IndexedObjectSomeValuesFrom ice, Writer writer,
					Context context) {
				ruleStats.timeObjectSomeValuesFromDecompositionRule -= CachedTimeThread.currentTimeMillis;
				ruleAppVisitor.visit(ice, writer, context);
				ruleStats.timeObjectSomeValuesFromDecompositionRule += CachedTimeThread.currentTimeMillis;		
			}
		};
	}
	
	
	
	/*private final RuleStatistics ruleStats_;
	private final RuleApplicationVisitor compositionRuleProcessor_;
	private final DecompositionRuleApplicationVisitor decompositionRuleProcessor_;
	
	TimeRuleApplicationVisitor(RuleApplicationVisitor processor, DecompositionRuleApplicationVisitor decompProcessor, RuleStatistics stats) {
		compositionRuleProcessor_ = processor;
		decompositionRuleProcessor_ = decompProcessor;
		ruleStats_ = stats;
	}
	
	@Override
	public void visit(
			OwlThingContextInitializationRule rule,
			Writer writer, Context context) {
		//TODO
		compositionRuleProcessor_.visit(rule, writer, context);
	}

	@Override
	public void visit(IndexedDisjointnessAxiom.ThisCompositionRule rule, Writer writer,
			Context context) {
		//TODO
		compositionRuleProcessor_.visit(rule, writer, context);
	}

	@Override
	public void visit(
			IndexedObjectIntersectionOf.ThisCompositionRule rule,
			Writer writer, Context context) {
		ruleStats_.timeObjectIntersectionOfCompositionRule -= CachedTimeThread.currentTimeMillis;
		compositionRuleProcessor_.visit(rule, writer, context);
		ruleStats_.timeObjectIntersectionOfCompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(
			IndexedSubClassOfAxiom.ThisCompositionRule rule,
			Writer writer, Context context) {
		ruleStats_.timeSubClassOfRule -= CachedTimeThread.currentTimeMillis;
		compositionRuleProcessor_.visit(rule, writer, context);
		ruleStats_.timeSubClassOfRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(
			IndexedObjectSomeValuesFrom.ThisCompositionRule rule,
			Writer writer, Context context) {
		ruleStats_.timeObjectSomeValuesFromCompositionRule -= CachedTimeThread.currentTimeMillis;
		compositionRuleProcessor_.visit(rule, writer, context);
		ruleStats_.timeObjectSomeValuesFromCompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(IndexedDisjointnessAxiom.ThisContradictionRule rule,
			Writer writer, Context context) {
		//TODO
		compositionRuleProcessor_.visit(rule, writer, context);
	}

	@Override
	public void visit(ForwardLink.ThisBackwardLinkRule rule, Writer writer,
			BackwardLink backwardLink) {
		//TODO
		compositionRuleProcessor_.visit(rule, writer, backwardLink);
	}

	@Override
	public void visit(
			Propagation.ThisBackwardLinkRule rule,
			Writer writer, BackwardLink backwardLink) {
		ruleStats_.timePropagationBackwardLinkRule -= CachedTimeThread.currentTimeMillis;
		compositionRuleProcessor_.visit(rule, writer, backwardLink);
		ruleStats_.timePropagationBackwardLinkRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(BottomBackwardLinkRule rule,
			Writer writer, BackwardLink backwardLink) {
		ruleStats_.timeContradictionBackwardLinkRule -= CachedTimeThread.currentTimeMillis;
		compositionRuleProcessor_.visit(rule, writer, backwardLink);
		ruleStats_.timeContradictionBackwardLinkRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(IndexedClass ice, Writer writer, Context context) {
		ruleStats_.timeClassDecompositionRule -= CachedTimeThread.currentTimeMillis;
		decompositionRuleProcessor_.visit(ice, writer, context);
		ruleStats_.timeObjectSomeValuesFromCompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(IndexedDataHasValue ice, Writer writer, Context context) {
		// TODO Auto-generated method stub
		decompositionRuleProcessor_.visit(ice, writer, context);
	}

	@Override
	public void visit(IndexedObjectIntersectionOf ice, Writer writer,
			Context context) {
		ruleStats_.timeObjectIntersectionOfDecompositionRule -= CachedTimeThread.currentTimeMillis;
		decompositionRuleProcessor_.visit(ice, writer, context);
		ruleStats_.timeObjectIntersectionOfDecompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(IndexedObjectSomeValuesFrom ice, Writer writer,
			Context context) {
		ruleStats_.timeObjectSomeValuesFromDecompositionRule -= CachedTimeThread.currentTimeMillis;
		decompositionRuleProcessor_.visit(ice, writer, context);
		ruleStats_.timeObjectSomeValuesFromDecompositionRule += CachedTimeThread.currentTimeMillis;		
	}*/
}
