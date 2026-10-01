package local.noto.grades.repositories

import local.noto.grades.models.StudyYear
import org.springframework.data.jpa.repository.JpaRepository

interface StudyYearRepository: JpaRepository<StudyYear, Long>