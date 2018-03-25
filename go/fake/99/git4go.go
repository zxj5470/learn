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
	strs, err := repo.Remotes.List()
	for i, v := range strs {
		fmt.Println(i, v)
	}
	fmt.Print(git.StatusWtNew)
}
