package ru.cib.avitoexporter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class AvitoExporterApplication

fun main(args: Array<String>) {
    runApplication<AvitoExporterApplication>(*args)
}
