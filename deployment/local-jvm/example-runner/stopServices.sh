#!/usr/bin/env bash
# Copyright 2018-2021 Crown Copyright
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

FILE=services-manager/target/services-manager-*-exec.jar
DIR=../Palisade-services/

if [ -d $DIR ]; then
  # Important to cd before running the jar - the working directory must be somewhere under Palisade-services
  cd $DIR
  # Stop all the services using the service manager from Palisade-services
  if [ -f $FILE ]; then
    java -jar -Dspring.profiles.active=example-libs,debug $FILE --manager.mode=shutdown
  else
    echo "Cannot find services-manager-<version>-exec.jar - have you run 'mvn install' in Palisade-services?"
  fi
else
  echo "Cannot find Palisade-services directory - have you run 'git clone'?"
fi
