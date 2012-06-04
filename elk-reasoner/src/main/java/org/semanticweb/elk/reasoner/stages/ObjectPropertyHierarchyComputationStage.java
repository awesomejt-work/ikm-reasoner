/*
 * #%L
 * ELK Reasoner
 * 
 * $Id$
 * $HeadURL$
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
package org.semanticweb.elk.reasoner.stages;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.semanticweb.elk.reasoner.ProgressMonitor;
import org.semanticweb.elk.reasoner.saturation.properties.ObjectPropertyHierarchyComputation;

/**
 * The reasoner stage, which purpose is to compute the object property hierarchy
 * of the given ontology
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class ObjectPropertyHierarchyComputationStage extends
		AbstractReasonerStage {

	// logger for this class
	private static final Logger LOGGER_ = Logger
			.getLogger(ObjectPropertyHierarchyComputationStage.class);

	/**
	 * the computation used for this stage
	 */
	private final ObjectPropertyHierarchyComputation computation;
	/**
	 * the number of workers used in the computation for this stage
	 */
	private final int workerNo;
	/**
	 * the progress monitor used to report progress of this stage
	 */
	private final ProgressMonitor progressMonitor;

	public ObjectPropertyHierarchyComputationStage(
			AbstractReasonerState reasoner) {
		super(reasoner);
		this.workerNo = reasoner.getNumberOfWorkers();
		this.progressMonitor = reasoner.getProgressMonitor();
		this.computation = new ObjectPropertyHierarchyComputation(
				reasoner.getStageExecutor(), workerNo, progressMonitor,
				reasoner.ontologyIndex);
	}

	@Override
	public String getName() {
		return "Object Property Hierarchy Computation";
	}

	@Override
	public boolean done() {
		return reasoner.doneObjectPropertyHierarchyComputation;
	}

	@Override
	public List<ReasonerStage> getDependencies() {
		return Collections.emptyList();
	}

	@Override
	public void execute() {
		if (LOGGER_.isInfoEnabled())
			LOGGER_.info(getName() + " using " + workerNo + " workers");
		computation.process();
		if (isInterrupted())
			return;
		reasoner.doneObjectPropertyHierarchyComputation = true;
	}

	@Override
	public void printInfo() {
	}

}
