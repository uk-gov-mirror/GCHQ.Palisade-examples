#! /usr/bin/env bash
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

helm dep up
helm upgrade --install --wait palisade . \
--set global.persistence.dataStores.palisade-data-store.local.hostPath=$(pwd)/resources/data, \
--set global.persistence.classpathJars.local.hostPath=$(pwd)/deployment/target \
--set global.deployment=example
