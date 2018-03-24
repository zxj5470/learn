package main

import "fmt"

func forLegacy() {
	for i := 0; i < 10; i += 4 {
		fmt.Println(i)
	}
}

func forLegacy2() {
	i := 0
	for i < 100 {
		i++
	}
	fmt.Println(23333)
}

func forWhile() {
	i := 10
	for i < 100 {
		i += 30
		fmt.Println(i)
	}
}

func while() {
	i := 10
	for {
		if i >= 100 {
			break
		}
		i += 30
		fmt.Println(i)
	}
}

func main() {
	forLegacy()
	forLegacy2()
	forWhile()
	while()
}
