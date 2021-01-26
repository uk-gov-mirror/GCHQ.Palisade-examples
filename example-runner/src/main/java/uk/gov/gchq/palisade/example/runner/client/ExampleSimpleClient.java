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

package uk.gov.gchq.palisade.example.runner.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.gov.gchq.palisade.clients.simpleclient.client.SimpleClient;
import uk.gov.gchq.palisade.clients.simpleclient.web.DataClientFactory;
import uk.gov.gchq.palisade.clients.simpleclient.web.PalisadeClient;
import uk.gov.gchq.palisade.data.serialise.AvroSerialiser;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.Employee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public class ExampleSimpleClient extends SimpleClient<Employee> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleSimpleClient.class);

    public ExampleSimpleClient(final PalisadeClient palisadeClient, final DataClientFactory dataClient) {
        super(new AvroSerialiser<>(Employee.class), palisadeClient, dataClient);
    }

    public void run(final String filename, final String userId, final String purpose) throws IOException {
        LOGGER.info("{} is reading the Employee file {} with a purpose of {}", userId, filename, purpose);
        final Stream<Stream<Employee>> results = read(filename, userId, purpose);
        LOGGER.info("{} got back:", userId);
        // We are going to read all resources and all records
        // So we won't be leaving any dangling connections
        // Therefore it is safe to flatMap and open up a connection for every resource simultaneously
        results.flatMap(Function.identity())
                .map(Employee::toString)
                .forEach(LOGGER::info);
    }

    /**
     * Given a name for either a directory of many files, or a single file, containing Employee AVRO data,
     * format this fileName to a URI resourceId, then read from the SimpleClient.
     *
     * @param fileName   the absolute or (if it exists locally) relative filename for the resource
     * @param userId     the user id
     * @param purpose    the purpose
     * @return a stream of Employee objects from palisade
     * @throws IOException if an exception occurred deserialising data
     */
    @Override
    public Stream<Stream<Employee>> read(final String fileName, final String userId, final String purpose) throws IOException {
        final File file;
        if (!Path.of(fileName).isAbsolute()) {
            // If a relative path is requested, this implies it is available locally
            file = new File(fileName).getCanonicalFile();
        } else {
            // Otherwise, keep it as it is
            file = new File(fileName);
        }

        // Get a file:$fileName URI
        String resourceId = file.toURI().toString();

        // Check that the resourceId ending is consistent with the fileName
        // ie. "../some/directory/" should still have a trailing slash "file:/root/some/directory/"
        // fileName could have been a DOS path
        // resourceId is a URI, so only a UNIX path
        if ((fileName.endsWith("/") || fileName.endsWith("\\")) && !resourceId.endsWith("/")) {
            resourceId += "/";
        }
        LOGGER.debug("Formatted fileName {} to file {} to resourceId {}", fileName, file, resourceId);
        return super.read(resourceId, userId, purpose);
    }
}
