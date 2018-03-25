package main

import "fmt"

type String struct {
	str string
}

func (a String) length() int {
	return len(a.str)
}

func main() {
	a := String{"ice1000"}
	fmt.Println(a.length())
}
