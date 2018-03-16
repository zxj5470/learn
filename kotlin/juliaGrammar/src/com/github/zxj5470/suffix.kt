package com.github.zxj5470

/**
 * @author: zxj5470
 * @date: 2018/3/16
 */
/*
(define no-suffix? (Set (append prec-assignment prec-conditional prec-lazy-or prec-lazy-and
                                prec-colon prec-decl prec-dot
                                '(-- --> -> |<:| |>:| in isa $)
                                (list ctrans-op trans-op vararg-op))))
(define (maybe-strip-op-suffix op)
 */
val `no-suffix?` = Set(`prec-assignment` + `prec-conditional`
		+ `prec-lazy-or` + `prec-lazy-and`
		+ `prec-colon` + `prec-decl` + `prec-dot`
		+ "-- --> -> <: >: in isa \$".asQuote()
		+ `ctrans-op` + `trans-op` + `vararg-op`
)


fun main(args: Array<String>) {
	println(`no-suffix?`(".'"))
}