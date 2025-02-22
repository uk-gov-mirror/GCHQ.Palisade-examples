/*
 * Copyright 2018-2021 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.palisade.example.runner.runner;

import akka.Done;
import akka.stream.javadsl.Sink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import uk.gov.gchq.palisade.example.library.common.Purpose;
import uk.gov.gchq.palisade.example.runner.config.AkkaClientWrapper;
import uk.gov.gchq.palisade.example.runner.config.RestConfiguration;
import uk.gov.gchq.syntheticdatagenerator.types.Employee;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class RestExample implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExample.class);

    private final RestConfiguration configuration;
    private final AkkaClientWrapper<Employee> client;

    public RestExample(final RestConfiguration configuration, final AkkaClientWrapper<Employee> client) {
        this.configuration = configuration;
        this.client = client;
    }

    private void makeRequest(final String userId, final String fileName, final String purpose) {
        LOGGER.info("");
        LOGGER.info("'{}' is reading '{}' with a purpose of '{}'...", userId, fileName, purpose);
        LOGGER.info("'{}' got back: ", userId);
        client.<CompletionStage<Done>>execute(userId, fileName, purpose)
                .apply(Sink.foreach(record -> LOGGER.info("{}", record)))
                .toCompletableFuture().join();
    }

    /**
     * The runner method to run some example requests through Palisade
     *
     * @param args command-line arguments
     * @throws IOException for any file system error
     */
    @Override
    public void run(final String... args) throws IOException {
        String alice = "Alice";
        String bob = "Bob";
        String eve = "Eve";

        String salary = Purpose.SALARY.name();
        String dutyOfCare = Purpose.DUTY_OF_CARE.name();
        String staffReport = Purpose.STAFF_REPORT.name();

        //Alice is reading the employee file with a purpose of SALARY
        makeRequest(alice, configuration.getFilename(), salary);

        //Alice is reading the employee file with a purpose of DUTY OF CARE
        makeRequest(alice, configuration.getFilename(), dutyOfCare);

        //Alice is reading the employee file with a purpose of STAFF REPORT
        makeRequest(alice, configuration.getFilename(), staffReport);

        //Bob is reading the employee file with a purpose of DUTY OF CARE
        makeRequest(bob, configuration.getFilename(), dutyOfCare);

        //Bob is reading the employee file with a purpose that is empty
        makeRequest(bob, configuration.getFilename(), "");

        //Eve is reading the employee file with a purpose that is empty
        makeRequest(eve, configuration.getFilename(), "");

        System.exit(0);
    }
}
