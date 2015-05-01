package org.semanticweb.elk.util.concurrent.computation;
/*
 * #%L
 * ELK Utilities for Concurrency
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

public class ConcurrentComputationWithInputsTest {

	private static int MAX_INPUT = 100;

	private static int ROUNDS_ = 8;

	private final Random random = new Random();

	private TestInputProcessorFactory factory_;

	private ConcurrentComputationWithInputs<Integer, ?> computation_;

	void setup(int round) {
		int workers = round + 1;
		ComputationExecutor executor = new ComputationExecutor(workers,
				"test-worker");
		factory_ = new TestInputProcessorFactory(MAX_INPUT, workers);
		computation_ = new ConcurrentComputationWithInputs<Integer, TestInputProcessorFactory>(
				factory_, executor, workers, workers);
	}

	@Test
	public void test() {

		int jobs = 1;
		for (int round = 0; round < ROUNDS_; round++) {
			setup(round);
			jobs = 1 << random.nextInt(ROUNDS_);
			int sumExpected = 0;
			if (!computation_.start())
				fail();
			int sleepCountdown = 0; // sleep when reaches 0
			try {
				for (int j = 0; j < jobs; j++) {
					int nextInput = random.nextInt(MAX_INPUT) + 1;
					sumExpected += nextInput;
					for (;;) {
						if (computation_.submit(nextInput))
							break;
						// else must be interrupted
						if (!computation_.isInterrupted())
							fail();
						computation_.finish();
						// restart computation
						computation_.setInterrupt(false);
						if (!computation_.start())
							fail();
					}
					sleepCountdown -= nextInput;
					while (sleepCountdown <= 0) {
						Thread.sleep(1); // sleeping on average 1ms per input
						sleepCountdown += random.nextInt(MAX_INPUT) + 1;
					}
				}
				for (;;) {
					computation_.finish();
					if (!computation_.isInterrupted())
						break;
					// else restart
					computation_.setInterrupt(false);
					if (!computation_.start())
						fail();
				}
			} catch (InterruptedException fail) {
				fail();
			}
			assertEquals(sumExpected, factory_.getSum());
		}

	}

	@Test
	public void testWithInterrupts() {
		Thread terminator = new Thread(new Terminator(), "terminator");
		terminator.setDaemon(true);
		terminator.start();
		test();
	}

	class Terminator implements Runnable {

		@Override
		public void run() {
			for (;;) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException ignore) {
				}
				Interrupter interrupter = computation_;
				if (interrupter != null) {
					interrupter.setInterrupt(true);
				}
			}
		}
	}

}
