package ru.cib.avitoexporter.service

import mu.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.cib.avitoexporter.domain.SearchResult
import ru.cib.avitoexporter.dto.DriverInitializer
import ru.cib.avitoexporter.repository.SearchResultRepository
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Service
class Task(
    @Value("\${webdriver.request.location}")
    private val searchLocation: String?,
    @Value("\${webdriver.request.text}")
    private val searchText: String?,
    private val searchResultRepository: SearchResultRepository,
    private val converter: Converter
) {

    @Scheduled(fixedDelay = 600000)
    private fun searchRequest() {
        try {
            logger.debug("Start of the process of parsing")
            val driver = driverInitialization()
            Thread.sleep(5000)
            if (driver.errMsg == null) {
                searchByPages(driver.driverInstance!!)
                Thread.sleep(5000)
                driver.driverInstance!!.close()
            } else {
                throw Exception(driver.errMsg)
            }
        } catch (e: Exception) {
            logger.error("Error during the process of scheduled search ${e.message}")
        }
    }

    private fun driverInitialization(): DriverInitializer {
        try {
            logger.debug("Initializing driver")
            val options = ChromeOptions().apply {
//            addArguments("window-size=1024,768", "--no-sandbox")
//            setExperimentalOption("debuggerAddress", "127.0.0.1:12000")
                setHeadless(false)
            }
            val driver = ChromeDriver(options).apply {
                manage().apply {
//                window().maximize()
                    timeouts().implicitlyWait(5, TimeUnit.SECONDS)
                }
                get("https://www.avito.ru/${converter.convertCyrillic(searchLocation?.decapitalize())}?q=${converter.formatText(searchText)}&s=104")
            }
            logger.debug("End of driver initializing")
            return DriverInitializer().apply { driverInstance = driver }
        } catch (e: Exception) {
            logger.error("Error during driver initialization: ${e.message}")
            return DriverInitializer().apply { errMsg = e.message }
        }
    }

    private fun searchByPages(driver: ChromeDriver) {
        val listOfSearchResults = mutableListOf<SearchResult>()
        logger.debug("Staring the search numbers of page")
        val paginationMenu = driver.findElementByXPath("/html/body/div[1]/div[2]/div[2]/div[3]/div[4]/div[1]")
        val listOfPages = paginationMenu.findElements(By.tagName("span"))
        val lastPage = listOfPages.size - 2
        logger.debug("Counting of pages: ${listOfPages[lastPage].text}")
        val listOfSearchElements =
            driver.findElementsByXPath("/html/body/div[1]/div[2]/div[2]/div[3]/div[3]/div[1]//div[@data-marker]/div[1]/div[2]/div[2]//a[@href]")
        listOfSearchElements.forEach {
            val link = it.getAttribute("href")
            val linkName = it.findElement(By.tagName("h3")).text
            logger.debug("Name of the title: $linkName")
            listOfSearchResults.add(SearchResult()
                .apply {
                    url = link
                    title = linkName
                }
            )
        }
        val dbResults = searchResultRepository.findAll()
        dbResults.forEach { dbResult ->
            listOfSearchResults.removeIf { dbResult.url == it.url }
        }
        searchResultRepository.saveAll(listOfSearchResults)
    }
}