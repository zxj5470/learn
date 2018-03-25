package main

import (
	"gopkg.in/libgit2/git2go.v26"
	"fmt"
	"os"
)

func main() {
	repo, err := git.OpenRepository("../../")
	if err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(1)
	}
	str, er := repo.StatusFile("go/fake/99/git4go.go")
	if er == nil {
		fmt.Println(str)
	}
	fmt.Print(git.StatusWtNew)
}
