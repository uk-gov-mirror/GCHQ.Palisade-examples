<!--
 Copyright 2018-2021 Crown Copyright

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->
# Examples

The example demonstrates different users querying an avro file over a REST api.

The Examples module contains all example specific modules as follows:

### [Example Library](example-library/README.md)
The policies applied when running the example are a collection of static coarse-grained (resource-level) and fine-grained (record-level) rules that can be found in the *Example Library*, and are used for prepopulating the palisade services with data.
This collection of example-specific rules, types and configurations is based around the possible policies a company might set out for users accessing sensitive employee data, depending upon their role in the company.
Provides the `ExampleUser` datatype specialisation of the [Palisade-common](https://github.com/gchq/Palisade-common) `User`, as well as Rules to be used for policy setting.

### [Example Runner](example-runner/README.md)
The example scenarios can be run using the Spring Boot REST client application in the *Example Runner*, requesting and reading for a number of different users, resources and contexts.
This simple Spring Boot application has a number of different runners for such REST clients, which also manage using the palisade-service response to connect to the appropriate data-service.

### [Performance](performance/README.md)
The performance of the palisade services compared to native file reads (and other metrics) can be measured using the *Performance* testing suite.
This performance-testing suite has a number of different trial types, datasets and policy variants to cover a reasonable number of common use-cases.

### [Deployment](deployment)
Contains all the deployment specific code and scripts. Current deployment targets are:
* [Local JVM Processes](./deployment/local-jvm/README.md)
* [Docker/Kubernetes Containers](./deployment/local-k8s/README.md)

Since scripts are all `.sh` files, the [services-manager](https://github.com/gchq/Palisade-services/blob/develop/services-manager/README.md) may be preferred for cross-platform compatibility.
