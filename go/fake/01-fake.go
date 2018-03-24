package main

import "fmt"

// 0 函数参数写法
func add(x, y int, z int) int {
	return x + y + z
}

// 1 多值返回
func swap(x, y string) (string, string) {
	return y, x
}

// 2 命名返回值
func split(sum int) (x, y int) {
	x = sum * 4 / 9
	y = sum - x
	return
}

// 3 变量初始化
var i, j int = 1, 2

func main() {
	fmt.Println(add(42, 13, 23))
	a, b := swap("hello", "world")
	fmt.Println(a, b)
	// 4 短变量声明(不过个人还是习惯写var顺手23333)
	k := 3
	var c, python, java = true, false, "no!"
	fmt.Println(i, j, k, c, python, java)
}
