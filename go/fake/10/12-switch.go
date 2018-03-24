package main

import (
	"fmt"
	"runtime"
	"time"
)

func main() {
	switch1()
	switch2()
	deferFun()
}

func switch2() {
	fmt.Println("When's Saturday?")
	today := time.Now().Weekday()
	fmt.Printf("Today is %s", today)
	switch time.Saturday {
	case today + 0:
		fmt.Println("Today.")
	case today + 1:
		fmt.Println("Tomorrow.")
	case today + 2:
		fmt.Println("In two days.")
	default:
		fmt.Println("Too far away.")
	}
}

func switch1() {
	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
		fallthrough // if no `fallthrough` it won't auto break
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.", os)
	}
}

func deferFun() {
	fmt.Println("counting")
	for i := 0; i < 10; i++ {
		defer fmt.Println(i)
	}
	fmt.Println("done")
}
