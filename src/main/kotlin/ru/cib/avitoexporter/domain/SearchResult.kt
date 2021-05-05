package ru.cib.avitoexporter.domain

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "search_result")
@Access(AccessType.FIELD)
data class SearchResult(
    @field:CreationTimestamp
    var creationDate: Date? = null,
    var url: String? = null,
    var title: String? = null,
    var notify: Boolean = false
) : Domain()