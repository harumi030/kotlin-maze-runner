package mazerunner
import kotlin.random.Random

fun main() {

    val maze = Maze(10, 10).generateGrid()


    val convertedMaze = maze.map { row ->
        row.map { column ->
            if (column == 1) "\u2588\u2588" else "  "
        }
    }

    for (i in 0 until maze.size) {
        println(convertedMaze[i].joinToString(""))
    }
}

class Maze(val rows: Int, val columns: Int) {

    fun generateGrid(): Array<Array<Int>> {
        val grid = Array(rows) { Array(columns) { 1 } }
        val stack = mutableListOf<Pair<Int, Int>>()
        val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

        // Start from the entrance
        val startRow = getRandomBetween(1, rows - 2)
        stack.add(Pair(startRow, 0))
        grid[startRow][0] = 0

        while (stack.isNotEmpty()) {
            val (currentRow, currentColumn) = stack.last()
            val neighbors = directions.map { Pair(currentRow + it.first * 2, currentColumn + it.second * 2) }
                .filter { (r, c) -> r in 1 until rows - 1 && c in 1 until columns - 1 && grid[r][c] == 1 }

            if (neighbors.isNotEmpty()) {
                val (nextRow, nextColumn) = neighbors.random()
                grid[nextRow][nextColumn] = 0
                grid[(currentRow + nextRow) / 2][(currentColumn + nextColumn) / 2] = 0
                stack.add(Pair(nextRow, nextColumn))
            } else {
                stack.removeAt(stack.size - 1)
            }
        }

        // Ensure the exit is open and next to a open space
        val possibleExitRows = mutableListOf<Int>()

        for ( r in 1 until rows - 1) {
            if (grid[r][columns - 2] == 0) {
                possibleExitRows.add(r)
            }
        }
        val exitRow = possibleExitRows.random()

        // Make sure there is an exit open
        grid[exitRow][columns - 1] = 0

        return grid
    }
}

fun getRandomBetween (min: Int, max: Int) = Random.nextInt(min, max)
