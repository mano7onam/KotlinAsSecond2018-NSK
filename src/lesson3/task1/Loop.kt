@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var num = n
    var cnt = 0
    while (num > 0) {
        num /= 10
        cnt++
    }
    return Math.max(1, cnt) // max for 0 case
}

fun digitNumber(n: Long): Int = n.toString().length

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var a = 1
    var b = 1
    for (i in 1 until n) {
        val c = a + b
        a = b
        b = c
    }
    return a
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun gcd(a: Int, b: Int) : Int {
    if (b == 0) {
        return a
    }
    return gcd(b, a % b)
}

fun lcm(m: Int, n: Int) : Int {
    if (n == 0) {
        return m
    }
    if (m == 0) {
        return n
    }
    return (m / gcd(m, n)) * n
}


/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    if (n < 2) return n
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return m
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return n / m
    }
    return 1
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = (gcd(m, n) == 1)

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    if (m < 0 && n < 0) {
        return false
    }
    if (m <= 0 && n >= 0) {
        return true
    }

    var num = 1
    while (num * num <= n) {
        if (num * num >= m) {
            return true
        }
        num++
    }

    return false
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var res = 0.0

    var sgn = -1
    var cur = 2
    var elem = x
    while (Math.abs(elem) >= eps) {
        res += elem

        elem *= sgn
        elem *= x * x
        elem /= cur * (cur + 1)

        sgn *= -1
        cur += 2
    }

    return res
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var res = 0.0

    var sgn = -1
    var cur = 1
    var elem = 1.0
    while (Math.abs(elem) >= eps) {
        res += elem

        elem *= sgn
        elem *= x * x
        elem /= cur * (cur + 1)

        sgn *= -1
        cur += 2
    }

    return res
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var res = 0
    var num = n
    while (num > 0) {
        res *= 10
        res += num % 10
        num /= 10
    }
    return res
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean = (n == revert(n))

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val last = n % 10
    var num = n / 10
    while (num > 0) {
        if (num % 10 != last) {
            return false
        }
        num /= 10
    }
    return true
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun getFirstNumWithSqrLen(len : Int) : Int {
    if (len <= 0) {
        return 0
    }
    if (len == 1) {
        return 1
    }

    var l = 1
    var r = 1000000000
    while (l < r - 1) {
        val m = (l + r) / 2
        val mmLen = digitNumber(m.toLong() * m.toLong())
        if (mmLen >= len) {
            r = m
        }
        else {
            l = m
        }
    }

    return r
}

fun getDigitInNumber(n : Int, ind: Int) : Int = (n.toString()[ind].toString()).toInt()

fun squareSequenceDigit(n: Int): Int {
    val first = "149162536496481100121144"
    if (n - 1 < first.length) {
        return first[n - 1].toString().toInt()
    }

    var allLen = 0
    var lastNum = 0
    var rest = 0
    for (curLen in 1..n) {
        val l = getFirstNumWithSqrLen(curLen)
        val r = getFirstNumWithSqrLen(curLen + 1) - 1
        val cut = (r - l + 1) * curLen
        if (allLen + cut >= n) {
            allLen += cut
            lastNum = r - (allLen - n) / curLen
            rest = curLen - 1 - (allLen - n) % curLen
            break
        }
        allLen += cut
    }

    return getDigitInNumber(lastNum * lastNum, rest)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var allLen = 0
    var lastNum = 0
    var rest = 0
    for (num in 1 .. n + 1) {
        val cut = digitNumber(fib(num))
        if (allLen + cut >= n) {
            allLen += cut
            lastNum = num
            rest = cut - (allLen - n) - 1
            break
        }
        allLen += cut
    }

    return getDigitInNumber(fib(lastNum), rest)
}

fun main(args: Array<String>) {
    // Решаем x^2 - 3*x + 2 = 0
    for (i in 1..30) {
        val res = squareSequenceDigit(i)
        println("Root product: $res")
    }

}

