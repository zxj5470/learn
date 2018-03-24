package main

import "fmt"

func main() {
	primes := [6]int{2, 3, 5, 7, 11, 13}
	// 1:4 is [1,4) return [3 5 7]

	var s []int = primes[1:4]
	fmt.Println(s)

	// Notes: slice return a reference!!! so change a value will change parent array
	s[2]=1
	// [3 5 7] => [3 5 1]
	fmt.Println(s)
	// [3 5 1]

	fmt.Println(primes)
	// [2 3 5 1 11 13]
}
