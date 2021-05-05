package ru.cib.avitoexporter.dto

import org.openqa.selenium.chrome.ChromeDriver

data class DriverInitializer(
    var driverInstance: ChromeDriver? = null,
    var errMsg: String? = null
)