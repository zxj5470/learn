package main

import (
	"fmt"
	"math"
)

func main() {
	var x, y = 3, 4
	var f float64 = math.Sqrt(float64(x*x + y*y))
	var z uint = uint(f)
	fmt.Println(x, y, z)
	v := 42
	fmt.Printf("v is of type %T\n", v)
	i := 42           // int
	ff := 3.142       // float64
	g := 0.867 + 0.5i // complex128
	fmt.Println(i, ff, g)
}
