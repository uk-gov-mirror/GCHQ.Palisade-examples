#!/usr/bin/env bash
# Copyright 2020 Crown Copyright
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

FILE=example-model/target/example-model-*-exec.jar
FORMATTER=deployment/local-jvm/example-model/formatOutput.sh

# Run the formatted rest example
if [ -f $FILE ]; then
  if [ -f $FORMATTER ]; then
    java -Dlogging.level.root=ERROR -Dlogging.level.uk.gov.gchq.palisade.example.model.runner.RestExample=INFO -Dspring.profiles.active=eureka,rest -jar $FILE | $FORMATTER
  else
    echo "Cannot find formatter script -- check your 'git status'"
  fi
else
  echo "Cannot find example-model-<version>-exec.jar - have you run 'mvn install'?"
fi
