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

import org.junit.Before;
import org.junit.Test;

import uk.gov.gchq.palisade.Context;
import uk.gov.gchq.palisade.User;
import uk.gov.gchq.palisade.example.library.common.Purpose;
import uk.gov.gchq.palisade.example.library.common.Role;
import uk.gov.gchq.syntheticdatagenerator.types.Employee;
import uk.gov.gchq.syntheticdatagenerator.types.Nationality;

import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NationalityTest {

    private static final User TEST_USER_NOT_HR = new User().roles("Not HR"); // Role not in HR
    private static final User TEST_USER_HR = new User().roles(Role.HR.name()); // Role is HR
    private static final NationalityRule NATIONALITY_RULE = new NationalityRule();
    private static final Context STAFF_REPORT_CONTEXT = new Context().purpose(Purpose.STAFF_REPORT.name());
    private static final Context NOT_STAFF_REPORT_CONTEXT = new Context().purpose("Not Staff Report");

    private Employee testEmployee;

    @Before
    public void setUp() {
        testEmployee = Employee.generate(new Random(1));
    }

    @Test
    public void shouldNotRedactForHRAndStaffReport() {
        // Given - Employee, Role, Reason

        // When
        Employee actual = NATIONALITY_RULE.apply(testEmployee, TEST_USER_HR, STAFF_REPORT_CONTEXT);
        Nationality actualNationality = actual.getNationality();

        // Then
        assertNotNull("Should not redact nationality for hr role and staff report purpose", actualNationality);
    }

    @Test
    public void shouldRedactForHRAndNotStaffReport() {
        // Given - Employee, Role, Reason

        // When
        Employee actual = NATIONALITY_RULE.apply(testEmployee, TEST_USER_HR, NOT_STAFF_REPORT_CONTEXT);

        // Then
        assertNull("Should redact nationality for hr role without staff report purpose", actual.getNationality());
    }

    @Test
    public void shouldRedactForNotHRAndStaffReport() {
        // Given - Employee, Role, Reason

        // When
        Employee actual = NATIONALITY_RULE.apply(testEmployee, TEST_USER_NOT_HR, STAFF_REPORT_CONTEXT);

        // Then
        assertNull("Should redact nationality for staff report purpose without hr role", actual.getNationality());
    }

    @Test
    public void shouldRedactForNotHRAndNotStaffReport() {
        // Given - Employee, Role, Reason

        // When
        Employee actual = NATIONALITY_RULE.apply(testEmployee, TEST_USER_NOT_HR, NOT_STAFF_REPORT_CONTEXT);

        // Then
        assertNull("Should redact nationality without staff report purpose and without hr role", actual.getNationality());
    }
}
