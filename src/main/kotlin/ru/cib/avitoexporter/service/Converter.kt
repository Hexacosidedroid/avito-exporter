package ru.cib.avitoexporter.service

import org.springframework.stereotype.Component

@Component
class Converter {

    fun formatText(searchText: String?) =
        searchText?.replace("^\\s+|\\s+$", "")?.replace(" ", "+")

    fun convertCyrilic(message: String?): String? {
        if (message?.filter { it == 'a'
                    || it == 'e'
                    || it == 'w'
                    || it == 'y'
                    || it == 'u'
                    || it == 'i'
                    || it == 'o'}
                !!.isNotEmpty())
                return message
        val mapForTranslation = hashMapOf<Char, String>().apply {
            this[' '] = " "
            this['а'] = "a"
            this['б'] = "b"
            this['в'] = "v"
            this['г'] = "g"
            this['д'] = "d"
            this['е'] = "e"
            this['ё'] = "e"
            this['ж'] = "zh"
            this['з'] = "z"
            this['и'] = "i"
            this['й'] = "y"
            this['к'] = "k"
            this['л'] = "l"
            this['м'] = "m"
            this['н'] = "n"
            this['о'] = "o"
            this['п'] = "p"
            this['р'] = "r"
            this['с'] = "s"
            this['т'] = "t"
            this['у'] = "u"
            this['ф'] = "f"
            this['х'] = "h"
            this['ц'] = "ts"
            this['ч'] = "ch"
            this['ш'] = "sh"
            this['щ'] = "sch"
            this['ъ'] = "\""
            this['ы'] = "y"
            this['ь'] = ""
            this['э'] = "e"
            this['ю'] = "yu"
            this['я'] = "ya"
            this['0'] = "0"
            this['1'] = "1"
            this['2'] = "2"
            this['3'] = "3"
            this['4'] = "4"
            this['5'] = "5"
            this['6'] = "6"
            this['7'] = "7"
            this['8'] = "8"
            this['9'] = "9"
        }
        val charArray = message.decapitalize().toCharArray()
        var translatedString = ""
        charArray.forEach {
            translatedString = "$translatedString${mapForTranslation[it]}"
        }
        return translatedString
    }
}