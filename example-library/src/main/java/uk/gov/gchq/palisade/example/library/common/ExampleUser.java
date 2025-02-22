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
package uk.gov.gchq.palisade.example.library.common;

import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.User;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

public class ExampleUser extends User {
    private static final long serialVersionUID = 1L;

    private EnumSet<TrainingCourse> trainingCourses = EnumSet.noneOf(TrainingCourse.class);

    public ExampleUser(final User user) {
        setUserId(user.getUserId());
        setAuths(user.getAuths());
        setRoles(user.getRoles());
    }

    public ExampleUser() {
    }

    public ExampleUser trainingCompleted(final TrainingCourse... trainingCompleted) {
        requireNonNull(trainingCompleted, "cannot add null training completed");
        trainingCourses.clear();
        trainingCourses.addAll(Arrays.asList(trainingCompleted));
        return this;
    }

    public ExampleUser trainingCompleted(final EnumSet<TrainingCourse> trainingCompleted) {
        requireNonNull(trainingCompleted, "cannot add null training completed");
        trainingCourses.clear();
        trainingCourses.addAll(trainingCompleted);
        return this;
    }

    public EnumSet<TrainingCourse> getTrainingCompleted() {
        return trainingCourses;
    }

    public void setTrainingCompleted(final TrainingCourse... trainingCompleted) {
        trainingCompleted(trainingCompleted);
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExampleUser)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final ExampleUser user = (ExampleUser) o;
        return Objects.equals(trainingCourses, user.trainingCourses);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(super.hashCode(), trainingCourses);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", ExampleUser.class.getSimpleName() + "[", "]")
                .add("trainingCourses=" + trainingCourses)
                .add(super.toString())
                .toString();
    }
}
