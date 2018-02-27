@file:Suppress("UNUSED_PARAMETER")
package lesson2.task1

import lesson1.task1.discriminant

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    val ageStr = age.toString()
    if (age % 100 in 5..20 || age % 10 == 0 || age % 10 >= 5) {
        return ageStr + " лет"
    }
    if (age % 10 == 1) {
        return ageStr + " год"
    }
    return ageStr + " года"
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val l1 = t1 * v1
    val l2 = t2 * v2
    val l3 = t3 * v3
    val halfLen = (l1 + l2 + l3) / 2.0
    if (halfLen <= l1) {
        return halfLen / v1
    }
    if (halfLen <= l1 + l2) {
        return t1 + (halfLen - l1) / v2
    }
    return t1 + t2 + (halfLen - l1 - l2) / v3
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    var res = 0
    if (kingX == rookX1 || kingY == rookY1) {
        res += 1
    }
    if (kingX == rookX2 || kingY == rookY2) {
        res += 2
    }
    return res
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    var mask = 0
    if (kingX == rookX || kingY == rookY) {
        mask += 1
    }
    if (kingX + kingY == bishopX + bishopY || kingX - kingY == bishopX - bishopY) {
        mask += 2
    }
    return mask
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    if (a + b < c || b + c < a || c + a < b || a <= 0 && b <= 0 && c <= 0) {
        return -1
    }

    val eps = 0.000000001
    var ta = a * a
    var tb = b * b
    var tc = c * c
    if (ta > tb) {
        ta = tb.also { tb = ta }
    }
    if (tb > tc) {
        tb = tc.also { tc = tb }
    }
    if (ta > tb) {
        ta = tb.also { tb = ta }
    }

    if (tc < ta + tb - eps) {
        return 2
    }
    if (tc > ta + tb + eps) {
        return 1
    }
    return 0
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int) : Int {
    if (Math.min(d, b) - Math.max(a, c) < 0) {
        return -1
    }
    return Math.min(d, b) - Math.max(a, c)
}
