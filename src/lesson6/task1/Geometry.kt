@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import lesson4.task1.roman
import java.util.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))

    operator fun plus(other: Point) : Point = Point(x + other.x, y + other.y)

    operator fun minus(other : Point) : Point = Point(x - other.x, y - other.y)

    operator fun div(koef: Double) : Point = Point(x / koef, y / koef)

    operator fun times(koef: Double) : Point = Point(x * koef, y * koef)

    fun cross(other : Point) : Double = (x * other.y - y * other.x)

    fun lenVect() : Double = Math.sqrt(x * x + y * y)

    fun getNormalized() : Point {
        val len = lenVect()
        if (len < 1e-10) {
            return Point(1.0, 0.0)
        }
        return Point(x / len, y / len)
    }

    fun getRot90() : Point = Point(-y, x)

}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
            Math.max(0.0, center.distance(other.center) - radius - other.radius)

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius + 1e-10
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) {
        throw IllegalArgumentException()
    }

    var res = Segment(points[0], points[1])
    points.forEach { p1 -> points.forEach { p2 ->
        if (p1.distance(p2) > res.begin.distance(res.end))
            res = Segment(p1, p2)
    } }

    return res
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle =
        Circle((diameter.begin + diameter.end) / 2.0,
                diameter.begin.distance(diameter.end) / 2.0)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    fun getOnePoint() : Point {
        val eps = 1e-10
        return if (Math.abs(Math.sin(angle)) < eps)
            Point(0.0, b / Math.cos(angle))
        else
            Point(-b / Math.sin(angle), 0.0)
    }

    fun getDir() : Point = Point(Math.cos(angle), Math.sin(angle))

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val eps = 1e-10
        val p1 = getOnePoint()
        val v1 = getDir()
        val p2 = other.getOnePoint()
        val v2 = other.getDir()

        val denum = v1.cross(v2)
        if (Math.abs(denum) < eps) {
            throw IllegalArgumentException()
        }

        val num = (p2 - p1).cross(v2)
        val t = num / denum

        return (p1 + v1 * t)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = lineByPoints(s.begin, s.end)

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val dir = (b - a).getNormalized()
    var ang = Math.atan2(dir.y, dir.x)
    if (ang < 0) {
        ang = Math.PI - ang
    }
    if (ang > Math.PI) {
        ang -= Math.PI
    }
    return Line(a, ang)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val mid = (a + b) / 2.0
    val dir = (b - a).getNormalized().getRot90()
    return lineByPoints(mid, mid + dir)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) {
        throw IllegalArgumentException()
    }

    var res = Pair(circles[0], circles[1])
    var maxDist = res.first.distance(res.second)
    circles.forEachIndexed { ind1, c1 -> circles.forEachIndexed { ind2, c2 ->
        if (ind1 != ind2) {
            val curDist = c1.distance(c2)
            if (c1.distance(c2) < maxDist) {
                maxDist = curDist
                res = Pair(c1, c2)
            }
        }
    } }

    return res
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle = TODO()/* {
    val center = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    val radius = (center - a).lenVect()
    return Circle(center, radius)
    // it was unreal hard
}*/

/*fun main(args: Array<String>) {
    val a = Point(0.0, 0.0)
    val b = Point(1.0, 0.0)
    val c = Point(0.0, 1.0)
    val cir = circleByThreePoints(a, b, c)
    println("Result: $cir")
}*/

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */

/*fun mccListPointPoint(ps: List<Point>, p1 : Point, p2 : Point) : Circle {
    var curCircle = circleByDiameter(Segment(p1, p2))
    ps.forEach { p ->
        if (!curCircle.contains(p)) {
            curCircle = circleByThreePoints(p, p1, p2)
        }
    }
    return curCircle
}

fun mccListPoint(ps: List<Point>, p1 : Point) : Circle {
    Collections.shuffle(ps)
    var curCircle = circleByDiameter(Segment(p1, ps[0]))
    val listFirstPs = arrayListOf<Point>()
    ps.forEach { p ->
        if (!curCircle.contains(p)) {
            curCircle = mccListPointPoint(listFirstPs, p, p1)
        }
        listFirstPs.add(p)
    }
    return curCircle
}

fun mccList(ps: List<Point>) : Circle {
    Collections.shuffle(ps)
    var curCircle = circleByDiameter(Segment(ps[0], ps[1]))
    val listFirstPs = arrayListOf<Point>()
    listFirstPs.add(ps[0])
    listFirstPs.add(ps[1])
    ps.forEach { p ->
        if (!curCircle.contains(p)) {
            curCircle = mccListPoint(listFirstPs, p)
        }
        listFirstPs.add(p)
    }
    return curCircle
}*/

fun minContainingCircle(vararg points: Point): Circle = /*mccList(points.toList())*/ TODO()


