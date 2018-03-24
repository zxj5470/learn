package main

import "fmt"

func main() {

	arrayOne := [3]string{"Apple", "Mango", "Banana"}
	fmt.Println("\n---------------Example1--------------------\n")
	for index, element := range arrayOne {
		fmt.Println(index)
		fmt.Println(element)

	}
	strDict := map[string]string{"Japan": "Tokyo", "China": "Beijing", "Canada": "Ottawa"}
	fmt.Println("\n---------------Example2--------------------\n")
	for index, element := range strDict {
		fmt.Println("Index :", index, " Element :", element)

	}
	fmt.Println("\n---------------Example3--------------------\n")
	for key := range strDict {
		fmt.Println(key)

	}
	fmt.Println("\n---------------Example4--------------------\n")
	for _, value := range strDict {
		fmt.Println(value)

	}
	j := 0
	fmt.Println("\n---------------Example5--------------------\n")
	for range strDict {
		fmt.Println(j)
		j++

	}
}
