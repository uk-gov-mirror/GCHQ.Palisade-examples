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

# Example Library

The users, resources and policies to be used in the example are configured in the [configuration yaml](/example-library/src/main/resources/application-example.yaml).
Each of these prepopulation vaules are loaded into the appropriate service on service start-up, see the [run-example scripts](/deployment/local-jvm/example-runner).

In particular, the example deals with the following users:
- User Alice has the role HR and completed the PAYROLL_TRAINING_COURSE
- User Bob has the role ESTATES and not completed any training
- User Eve has the role IT and not completed any training

### BankDetailsRule  
The bankDetails field should be returned:
- if the user querying the file has the HR role, completed the PAYROLL_TRAINING_COURSE and the purpose of the query is SALARY

In all other cases the bankDetails field should be redacted.

### DutyOfCareRule  
This rule is concerned with the contactNumber, emergencyContacts and sex fields. These fields should be returned:
- if the user querying the file has the HR role and the purpose of the query is DUTY_OF_CARE
- if the user querying the file is the line manager of the Employee record being queried and the purpose of the query is DUTY_OF_CARE  

In all other cases these fields should be redacted.

### FirstResourceRule  
This rule is concerned with the resource file that is being requested:
- if the user has an HR role they will be able to access the first resource file

In all other cases the first resource will not be returned to the user.

### NationalityRule  
The nationality field should be returned:
- if the user querying the file has the HR role and the purpose of the query is STAFF_REPORT

In all other cases the nationality field should be redacted.

### RecordMaskingRule
This rule is concerned with the full record:
- if the user querying the file has the HR role then no modifications are made to the record
- if the user is in the management tree of the employee then no modifications are made to the record
- if the user querying the file has the ESTATES role then the DateOfBirth, HireDate, Grade and Manager fields are redacted  

In all other cases the record will have no information returned.

### ZipCodeMaskingRule
This rule is concerned with the address field:
- if the user querying the file has the HR role then the whole address is returned
- if the purpose of the query is DUTY_OF_CARE and the user querying the file is the line manager of the Employee record being queried then the whole address is returned
- if the user querying the file has the ESTATES role then the address field should be returned with the zipcode/postcode masked to reduce its precision

In all other cases the address field should be redacted.
