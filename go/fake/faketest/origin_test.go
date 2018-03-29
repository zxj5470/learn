package main

import (
	"testing"
)

func TestAdd(t *testing.T) {
	if ret := add(1, 2); ret == 3 {
		t.Log("OK")
	} else {
		t.Error("Error")
	}
}
