@file:Suppress("UNUSED_PARAMETER")
package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.squareBetweenExists

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = Math.sqrt((v.map {it * it}).sum())

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
        if (list.isEmpty()) 0.0 else (list.map { it }).sum() / list.size

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val mid = mean(list)
    for (i in 0 until list.size) {
        list[i] -= mid
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double =
        a.foldIndexed(0.0) { i, sum, el -> sum + el * b[i] }

//fun times(a: List<Int>, b: List<Int>): Int =
//        a.foldIndexed(0) { i, sum, el -> sum + el * b[i] }

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var cur = 1.0
    val xs = p.map { val last = cur; cur *= x; last }
    return times(p, xs)
}

//fun polynom(p: List<Int>, x: Int): Int {
//    var cur = 1
//    val xs = p.map { val last = cur; cur *= x; last }
//    return times(p, xs)
//}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var sum = 0.0
    return list.apply { forEachIndexed { ind, el ->
        list[ind] = sum + el
        sum += el
    } }
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var num = n
    val res = arrayListOf<Int>()
    for (i in 2 .. n) {
        while (num % i == 0) {
            res.add(i)
            num /= i
        }
    }
    return res
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String =
        factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    if (n == 0) {
        return listOf(0)
    }
    var num = n
    val res = arrayListOf<Int>()
    while (num > 0) {
        res.add(num % base)
        num /= base
    }
    return res.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String =
    (convert(n, base).map {
        if (it < 10) it.toString()[0] else ('a' + it - 10)
    }).joinToString(separator="")


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int =
        digits.fold(0) { res, cur -> res * base + cur }

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    return str.fold(0) { sum, symb ->
        if (symb.isDigit())
            sum * base + (symb - '0')
        else
            sum * base + (symb - 'a') + 10
    }
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var num = n
    val arr = listOf("I" to 1, "IV" to 4, "V" to 5, "IX" to 9, "X" to 10,
            "XL" to 40, "L" to 50, "XC" to 90, "C" to 100,
            "CD" to 400, "D" to 500, "CM" to 900, "M" to 1000).reversed()
    var ind = 0
    val ans = arrayListOf<String>()
    while (num > 0) {
        while (num > 0 && num >= arr[ind].second) {
            num -= arr[ind].second
            ans.add(arr[ind].first)
        }
        ind++
    }
    return ans.joinToString(separator = "")
}

fun main(args: Array<String>) {
    val res = roman(4497)
    println("Result: $res")
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun russianHundreds(n: Int) : String {
    assert(n < 10)
    return when (n) {
        1 -> "сто"
        2 -> "двести"
        3 -> "триста"
        4 -> "четыреста"
        5 -> "пятьсот"
        6 -> "шестьсот"
        7 -> "семьсот"
        8 -> "восемьсот"
        9 -> "девятьсот"
        else -> "not check: $n"
    }
}

fun russianDozens(n: Int) : String {
    assert(n < 10)
    return when (n) {
        1 -> "десять"
        2 -> "двадцать"
        3 -> "тридцать"
        4 -> "сорок"
        5 -> "пятьдесят"
        6 -> "шестьдесят"
        7 -> "семьдесят"
        8 -> "восемьдесят"
        9 -> "девяносто"
        else -> "not check: $n"
    }
}

fun russianTenNineeen(n: Int) : String {
    assert(n < 10)
    return when (n) {
        10 -> "десять"
        11 -> "одиннадцать"
        12 -> "двенадцать"
        13 -> "тринадцать"
        14 -> "четырнадцать"
        15 -> "пятнадцать"
        16 -> "шестнадцать"
        17 -> "семнадцать"
        18 -> "восемнадцать"
        19 -> "девятнадцать"
        else -> "not check: $n"
    }
}

fun russianUnits(n: Int) : String {
    assert(n < 10)
    return when (n) {
        1 -> "один"
        2 -> "два"
        3 -> "три"
        4 -> "четыре"
        5 -> "пять"
        6 -> "шесть"
        7 -> "семь"
        8 -> "восемь"
        9 -> "девять"
        else -> ""
    }
}

fun russianUnitsThousands(n: Int) : String {
    assert(n < 10)
    return when (n) {
        1 -> "одна тысяча"
        2 -> "две тысячи"
        3 -> "три тысячи"
        4 -> "четыре тысячи"
        5 -> "пять тысяч"
        6 -> "шесть тысяч"
        7 -> "семь тысяч"
        8 -> "восемь тысяч"
        9 -> "девять тысяч"
        else -> "тысяч"
    }
}

fun russianDozensHundreds(n: Int) : String {
    assert(n < 100)
    if (n == 0) {
        return ""
    }
    if (n < 10) {
        return russianDozens(n)
    }
    if (n % 10 == 0) {
        return russianHundreds(n / 10)
    }
    return russianHundreds(n / 10) + " " + russianDozens(n % 10)
}

fun russianAllThousands(n: Int) : String {
    assert(n < 1000)
    if (n == 0) {
        return ""
    }
    if (n < 10) {
        return russianUnitsThousands(n)
    }
    if (n % 10 == 0) {
        return russianDozensHundreds(n / 10) + " тысяч"
    }
    if (n % 100 in 10..19) {
        return if (n < 100)
            russianTenNineeen(n) + " тысяч"
        else
            russianHundreds(n / 100) + " " + russianTenNineeen(n % 100) + " тысяч"
    }
    return russianDozensHundreds(n / 10) + " " + russianUnitsThousands(n % 10)
}

fun russianUnitsDozensHundreds(n: Int) : String {
    assert(n < 1000)
    if (n == 0) {
        return ""
    }
    if (n < 10) {
        return russianUnits(n)
    }
    if (n % 10 == 0) {
        return russianDozensHundreds(n / 10)
    }
    if (n % 100 in 10..19) {
        return if (n < 100)
            russianTenNineeen(n)
        else
            russianHundreds(n / 100) + " " + russianTenNineeen(n % 100)
    }
    return russianDozensHundreds(n / 10) + " " + russianUnits(n % 10)
}

fun russian(n: Int): String {
    if (n == 0) {
        return "ноль"
    }
    if (n < 1000) {
        return russianUnitsDozensHundreds(n)
    }
    if (n % 1000 == 0) {
        return russianAllThousands(n / 1000)
    }
    return russianAllThousands(n / 1000) + " " + russianUnitsDozensHundreds(n % 1000)
}