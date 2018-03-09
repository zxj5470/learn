/**
 * @ref https://gist.github.com/kuwalab/c5a41b6942939e09982a
 */
fun main(args: Array<String>) {
	Excel.open("test.xlsx").use {
		val sheet = it.getSheetAt(0)
		for(row in 0 until 3){
			for(col in 0 until 3){
				println("${col+1}行,${(row+65).toChar()}列: ${sheet[row,col]}")
			}
		}
	}
}