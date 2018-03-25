package main

import (
	"net/http"
)

type helloHandler struct{}

func (h *helloHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	switch r.URL.Path {
	case "/":
		w.Write([]byte("Hello, world!"))
	case "/favicon.ico":
		http.ServeFile(w,r,"img/logo.ico")
	default:
		w.Write([]byte("other"))
	}
}

func main() {
	http.Handle("/", &helloHandler{})
	http.ListenAndServe(":12345", nil)
}
