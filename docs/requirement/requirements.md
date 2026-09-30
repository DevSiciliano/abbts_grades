# Requirements Specification
| Version | Date |	Author	|	Changes |
|--|--|--|--|
| 0.1 | 30.09.2026 |	Massimo Noto	|	First Draft	|

## 1. Visions and Goals
As I need a project, to proof my JVM Backend-Skills I want to use something I can use in my daily business.


| ID  | Goal                                        |
|-----|---------------------------------------------|
| G-1 | Can check my grade even after semester ends |
| G-2 | Can check, if I passed the semester or not  |
| G-3 | I don't have to use the ABB TS Excel anymore |

## 2. Stakeholder
| Stakeholder  | Role / Interest | Sources      |
|--------------|----------------|--------------|
| Massimo Noto | ABBTS Student  | My own needs |

## 3. System-Context and Scope

### Scope
- Recording Modules, Grades, Credit Points, and Semester Assignments
- Calculation of Group Averages and Promotion Status
- Multi-user mode with login

### Out of Scope
- Integration with School Systems

## 4. Functional Requirements
| ID   | Requirement | Prio |
|------|--------|------|
| FR-1 | The system must be able to record modules by name, group (general/technical), credit points, and semester | M    |
| FR-2 |The system must be able to record one or more graded scores, each with a weighting, for each module| M    |
| FR-3 | The system must calculate the group average for the general and technical groups | M |
| FR-4 | The system must display the total number of CP earned per academic year | M |
| FR-5 | The system must be able to record and grade semester assignments starting in the second year of study | M
| FR-6 | The system must indicate whether the graduation requirements for a given academic year have been met | M
| FR-7 | The system should calculate the grade required in an open exam to achieve a group average of 4.0 | S
| FR-8 | Das System kann die Notenentwicklung grafisch darstellen | K

## 5. User Stroies
All information related to grades should be accessible in one place and stored in a database. This ensures that the information is up-to-date and persistent.
With Excel, it's never 100% clear whether the information is still current.

## 6. Quality Requirements
| ID   | Requirement                                                                                                  |
|------|--------------------------------------------------------------------------------------------------------------|
| QR-1 | The calculations must strictly adhere to the rules set forth in the academic regulations (including rounding) |
| QR-2 | The calculation logic must be verified through automated tests with at least 90% coverage            |
| QR-3 | The API should respond to every request in less than 500 ms                                                  |

## 7. Boundary Conditions
| ID | Boundary Conditions |
|--|--|
|BC-1|Implementation in Java 21 with Spring Boot|
|BC-2|Local data storage (H2 or SQLite) Later with PostgreSQL |
|BC-3 | Source code and documentation in a Git repository |

## 8. Glossary
| Term                   | Definition                                                      |
|------------------------|-----------------------------------------------------------------|
| Aspirant               | Student in the first year of study                              |
| Diplomand              | Student in the final year of study                              |
| CP (Credit Points)     | Unit measuring the workload of a module                         |
| General group          | Module group comprising the general education subjects          |
| Technical group        | Module group comprising the technical subjects                  |
| Group average          | Average grade of all modules within a group                     |
| Promotion              | Advancement to the next year of study when all requirements are met |