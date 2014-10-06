/**
 * 
 */
package org.semanticweb.elk.reasoner.incremental;

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
import java.util.Collection;
import java.util.Map;

import org.semanticweb.elk.reasoner.ProgressMonitor;
import org.semanticweb.elk.reasoner.ReasonerComputationWithInputs;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClass;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.saturation.SaturationState;
import org.semanticweb.elk.reasoner.saturation.SaturationStatistics;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.contextinit.LinkedContextInitRule;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.ChainableSubsumerRule;
import org.semanticweb.elk.util.concurrent.computation.ComputationExecutor;

/**
 * Goes through the input class expressions and puts each context's superclass
 * for which there are changes into the ToDo queue
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * 
 * @author "Yevgeny Kazakov"
 */
public class IncrementalChangesInitialization
		extends
		ReasonerComputationWithInputs<ArrayList<Context>, ContextInitializationFactory> {

	public IncrementalChangesInitialization(
			Collection<ArrayList<Context>> inputs,
			LinkedContextInitRule changedInitRules,
			Map<IndexedClassExpression, ChainableSubsumerRule> changedCompositionRules,
			Map<IndexedClass, IndexedClassExpression> changedDefinitions,
			SaturationState<?> state, ComputationExecutor executor,
			SaturationStatistics stageStats, int maxWorkers,
			ProgressMonitor progressMonitor) {
		super(inputs, new ContextInitializationFactory(state, changedInitRules,
				changedCompositionRules, changedDefinitions, stageStats),
				executor, maxWorkers, progressMonitor);
	}
}
