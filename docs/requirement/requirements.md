# Requirements Specification
| Version | Date |	Author	|	Changes |
|--|--|--|--|
| 0.1 | 30.09.2026 |	Massimo Noto	|	First Draft	|
| 0.2 | 30.09.2026 |	Massimo Noto	|	Domain model and calculation rules based on the ABB TS Excel (B25 Informatik) |

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
| Massimo Noto | ABBTS Student  | My own needs, ABB TS Excel "Berechnungen_Promotion_if_B25_V02_00" |

## 3. System-Context and Scope

### Scope
- Recording module grades, credit points and projects (semester assignments, diploma thesis)
- Calculation of promotion group averages, earned credit points and promotion status
- Multi-user mode with login

### Out of Scope
- Integration with School Systems

## 4. Domain Model

### Study structure (curriculum B25 Informatik)
The curriculum is predefined master data. Users only enter grades.

| Study year | Semesters | Promotion groups | Projects | Total CP | Minimum CP |
|------------|-----------|------------------|----------|----------|------------|
| Aspirant   | 1–2       | IF_1_A, IF_1_D   | –        | 32       | 26         |
| Kandidat   | 3–4       | IF_2_D, IF_2_V   | PSA_IF1/PRO_SA1, PSA_IF2/PRO_SA2 | 28 | 20 |
| Diplomand  | 5–6       | IF_3_A, IF_3_T   | PSA_IF3/PRO_SA3, PRO_DA2 | 22 | 16 |

```
StudyYear (name, minimum CP)
 ├─ PromotionGroup (code)
 │   └─ Module (code, name, CP, semester)
 │        └─ Grade (entered by the user)
 └─ Project (code)
      └─ Grade (entered by the user)
```

### Modules
- A module lasting one semester is worth 4 CP, a module lasting half a semester is worth 2 CP.
- Every module has exactly **one final grade** (QV). For modules with an intermediate exam (e.g. mathematics), the school issues one combined final grade.

## 5. Calculation Rules
| ID   | Rule |
|------|------|
| CR-1 | Module grades (QV) are recorded with a precision of 0.1 (range 1.0–6.0). |
| CR-2 | A module is **passed** if its grade, rounded to 0.5 (half up), is at least 4.0. Examples: 3.8 → 4.0 (passed), 3.7 → 3.5 (failed). |
| CR-3 | Credit points are all-or-nothing: a passed module earns its full CP, a failed or not yet graded module earns 0 CP. |
| CR-4 | The promotion group average is the simple (unweighted) mean of all graded module grades in the group, rounded to 0.1 (half up). CP do not influence the group average. |
| CR-5 | A study year is **promoted** if every promotion group average is at least 4.0 **and** the earned CP of the year reach the minimum CP. |
| CR-6 | If a study year is not promoted (group average below 4.0 or minimum CP not reached), the failed modules must be repeated. |
| CR-7 | From the second study year on, promotion also requires the promotion of the previous year. |
| CR-8 | Projects are graded separately and do not count towards the CP sum of the year. |

## 6. Functional Requirements
| ID   | Requirement | Prio |
|------|--------|------|
| FR-1 | The system must provide the curriculum (study years, promotion groups, modules with code, name, CP and semester) as master data | M |
| FR-2 | The system must be able to record one final grade per module | M |
| FR-3 | The system must calculate the average of each promotion group (CR-4) | M |
| FR-4 | The system must display the CP earned per study year (CR-2, CR-3) | M |
| FR-5 | The system must be able to record and grade projects starting in the second year of study (CR-8) | M |
| FR-6 | The system must indicate whether the promotion requirements for a given study year have been met (CR-5 – CR-7) | M |
| FR-7 | The system should calculate the grade required in an open module to achieve a promotion group average of 4.0 | S |
| FR-8 | The system could display the grade development graphically | C |

Prio: M = Must, S = Should, C = Could (MoSCoW)

## 7. User Stories
All information related to grades should be accessible in one place and stored in a database. This ensures that the information is up-to-date and persistent.
With Excel, it's never 100% clear whether the information is still current.

## 8. Quality Requirements
| ID   | Requirement                                                                                                  |
|------|--------------------------------------------------------------------------------------------------------------|
| QR-1 | The calculations must strictly adhere to the calculation rules (section 5) and the academic regulations (including rounding) |
| QR-2 | The calculation logic must be verified through automated tests with at least 90% coverage            |
| QR-3 | The API should respond to every request in less than 500 ms                                                  |

## 9. Boundary Conditions
| ID | Boundary Conditions |
|--|--|
|BC-1|Implementation in Kotlin on Java 21 with Spring Boot|
|BC-2|Local data storage (H2 or SQLite) Later with PostgreSQL |
|BC-3 | Source code and documentation in a Git repository |

## 10. Open Questions
| ID   | Question |
|------|----------|
| OQ-1 | Is a promotion group average compared with 4.0 at 0.1 precision (3.9 = failed), or is it also rounded to 0.5 first? |
| OQ-2 | Must every project have a grade of at least 4.0 for promotion? What happens if a project fails? |
| OQ-3 | When a module is repeated, does the new grade replace the old one? Should the old grade be kept as history? |

## 11. Glossary
| Term                   | Definition                                                      |
|------------------------|-----------------------------------------------------------------|
| Aspirant               | Student in the first year of study (probation period, no projects) |
| Kandidat               | Student in the second year of study                             |
| Diplomand              | Student in the final year of study                              |
| CP (Credit Points)     | Unit measuring the workload of a module (4 CP per semester module, 2 CP per half-semester module) |
| QV                     | Final grade of a module (Qualifikationsverfahren), precision 0.1 |
| Promotion group        | Group of modules within a study year whose average must be at least 4.0 |
| Group average          | Simple mean of all module grades within a promotion group      |
| Minimum CP             | CP a student must earn in a study year to be promoted          |
| Project                | Semester assignment (PSA/PRO_SA) or diploma thesis (PRO_DA), graded separately without CP |
| Promotion              | Advancement to the next year of study when all requirements are met |
| Auflage                | Condition to repeat failed modules when promotion requirements are not met |
