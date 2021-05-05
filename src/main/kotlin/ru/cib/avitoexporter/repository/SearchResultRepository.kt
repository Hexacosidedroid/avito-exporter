package ru.cib.avitoexporter.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.cib.avitoexporter.domain.SearchResult

interface SearchResultRepository : JpaRepository<SearchResult, Long> {
}