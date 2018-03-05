@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)

    /*fun havePosition(pos: Cell) : Boolean*/
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E> (height: Int, width: Int, e: E) : Matrix<E> {
    override val height: Int

    override val width: Int

    private var values : MutableList<E>

    init {
        if (height <= 0 || width <= 0) {
            throw IllegalArgumentException()
        }
        this.height = height
        this.width = width
        values = MutableList(height * width, { ind -> e } )
    }

    override fun get(row: Int, column: Int): E  {
        val ind = row * width + column
        if (ind > values.size) {
            throw IndexOutOfBoundsException()
        }
        return values[ind]
    }

    override fun get(cell: Cell): E  = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        val ind = row * width + column
        if (ind > values.size) {
            throw IndexOutOfBoundsException()
        }
        values[ind] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?) : Boolean {
        return when {
            this === other -> true
            this.javaClass != other?.javaClass -> false
            values == (other as MatrixImpl<*>).values -> true
            else -> false
        }
    }

    override fun hashCode() : Int = values.hashCode()

    override fun toString(): String {
        if (width == 0 || height == 0) {
            return "[[]]"
        }
        val sb = StringBuilder()
        // TODO MANO
        return sb.toString()
    }

    /*override fun havePosition(pos: Cell) : Boolean =
            (pos.row in 0 until height && pos.column in 0 until width)*/
}

