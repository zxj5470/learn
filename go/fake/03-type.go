package main

import (
	"fmt"
	"math"
	"math/cmplx"
	"testing"
)

var (
	ToBe          = false
	MaxInt uint64 = 1<<64 - 1
	z             = cmplx.Sqrt(-5 + 12i)
)

func BenchmarkSinh(b *testing.B) {
	for i := 0; i < b.N; i++ {
		math.Sinh(complex(2.5, 3.5))
	}
}

func main() {
	fmt.Printf("Type: %T Value: %v\n", ToBe, ToBe)
	fmt.Printf("Type: %T Value: %v\n", MaxInt, MaxInt)
	fmt.Printf("Type: %T Value: %v\n", z, z)
}
