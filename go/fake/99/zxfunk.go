package main

import (
	"github.com/thoas/go-funk"
	"fmt"
)

func main() {
	lists := []string{"asdf", "as", "asss"};
	fmt.Println(lists)
	lists2 := funk.Filter(lists, func(it string) bool { return len(it) < 3 })
	fmt.Println(lists2)
}
