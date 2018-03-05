import org.apache.poi.ss.usermodel.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Excel {
	fun open(fileName: String): Workbook {
		return WorkbookFactory.create(this::class.java.getResourceAsStream(fileName))
	}

	fun write(workbook: Workbook, fileName: String) {
		val outputPath = Paths.get(fileName)
		try {
			Files.newOutputStream(outputPath).use {
				workbook.write(it)
			}
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}
}

operator fun Sheet.get(n: Int): Row {
	return this.getRow(n) ?: this.createRow(n)
}

operator fun Row.get(n: Int): Cell = this.getCell(n)

operator fun Sheet.get(x: Int, y: Int) = this[y][x]

operator fun Sheet.set(x: Int, y: Int, value: Any) {
	val cell = this[x, y]
	when (value) {
		is String -> cell.setCellValue(value)
		is Int -> cell.setCellValue(value.toDouble())
		is Double -> cell.setCellValue(value)
		else -> throw IllegalArgumentException("文字列か数値だけにして")
	}
}