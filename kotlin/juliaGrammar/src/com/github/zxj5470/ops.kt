package com.github.zxj5470

/**
 * @author: zxj5470
 * @date: 2018/3/16
 */
/*
(define unary-ops (append! '(|<:| |>:|)
                           (add-dots '(+ - ! ~ ¬ √ ∛ ∜))))
 */
val `unary-ops` = "<: >:".asQuote() + `add-dots`("+ - ! ~ ¬ √ ∛ ∜".asQuote())
val `unary-op?` = Set(`unary-ops`)

val `maybe-strip-op-suffix`: AnyToAny = { it ->
	if (it is Symbol) {

	} else {
		it
	}
}

fun main(args: Array<String>) {
	println(`unary-op?`("<:"))
	println(`unary-op?`("$"))
	println(`unary-op?`(".+"))
}