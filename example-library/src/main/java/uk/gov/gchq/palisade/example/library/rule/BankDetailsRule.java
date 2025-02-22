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

package uk.gov.gchq.palisade.example.library.rule;

import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.library.common.ExampleUser;
import uk.gov.gchq.palisade.example.library.common.Purpose;
import uk.gov.gchq.palisade.example.library.common.Role;
import uk.gov.gchq.palisade.example.library.common.TrainingCourse;
import uk.gov.gchq.palisade.rule.Rule;
import uk.gov.gchq.syntheticdatagenerator.types.Employee;

import java.util.EnumSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class BankDetailsRule implements Rule<Employee> {
    public BankDetailsRule() {
    }

    private Employee redactRecord(final Employee redactedRecord) {
        redactedRecord.setBankDetails(null);
        redactedRecord.setTaxCode(null);
        redactedRecord.setSalaryAmount(-1);
        redactedRecord.setSalaryBonus(-1);
        return redactedRecord;
    }

    public Employee apply(final Employee record, final User user, final Context context) {
        if (null == record) {
            return null;
        }
        requireNonNull(user);
        requireNonNull(context);

        if (user instanceof ExampleUser) {
            ExampleUser exampleUser = (ExampleUser) user;
            EnumSet<TrainingCourse> trainingCompleted = exampleUser.getTrainingCompleted();
            Set<String> roles = exampleUser.getRoles();
            String purpose = context.getPurpose();

            if (trainingCompleted.contains(TrainingCourse.PAYROLL_TRAINING_COURSE) &&
                    purpose.equals(Purpose.SALARY.name()) &&
                    roles.contains(Role.HR.name())) {
                return record;
            }
        }
        return redactRecord(record);
    }
}
