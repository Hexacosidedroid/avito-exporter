package ru.cib.avitoexporter.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SeleniumConfig(
    @Value("\${webdriver.path}")
    private val pathToDriver: String?,
    @Value("\${webdriver.browser}")
    private val typeOfDriver: String?
) {
    @Bean
    fun pathEnvironment() {
        System.setProperty("webdriver.${typeOfDriver?.decapitalize()}.driver", "$pathToDriver")
    }
}