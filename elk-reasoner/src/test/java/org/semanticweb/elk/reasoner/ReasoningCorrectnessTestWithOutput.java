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
package org.semanticweb.elk.reasoner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.semanticweb.elk.testing.TestInput;
import org.semanticweb.elk.testing.TestManifestWithOutput;
import org.semanticweb.elk.testing4.PolySuite4;

/**
 * Runs tests for all pairs of test input and expected output in the test
 * directory.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * @author Peter Skocovsky
 * 
 * @param <I> 
 * @param <O>
 *            The type of test output.
 * @param <TM> 
 * @param <TD> 
 */
@RunWith(PolySuite4.class)
public abstract class ReasoningCorrectnessTestWithOutput<I extends TestInput, O, TM extends TestManifestWithOutput<I, O>, TD extends ReasoningTestWithOutputDelegate<O>>
		extends BaseReasoningCorrectnessTest<I, O, TM, TD> {

	public ReasoningCorrectnessTestWithOutput(final TM testManifest,
			final TD testDelegate) {
		super(testManifest, testDelegate);
	}

	/**
	 * Checks that the actual test output is the same as the expected output.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		getDelegate().initWithOutput();
		final O actualOutput = getDelegate().getActualOutput();
		getManifest().compare(actualOutput);
	}

}