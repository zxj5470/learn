package com.github.zxj5470

/**
 * @author: zxj5470
 * @date: 2018/3/16
 */

/**
 * ```scheme
 * '(a b c)
 * ```
 * @usage "a b c".asQuote()
 */
typealias Quote = List<String>
typealias AnyToBoolean = (Any) -> Boolean
typealias AnyToAny = (Any) -> Any

fun String.asQuote() = this.split(" ")

@JvmName("addDots")
fun List<String>.`add-dots`() = this.map { ("." + it) }

fun `add-dots`(ops: List<String>) = ops.`add-dots`()

data class Symbol(private val str: String) {
	override fun toString() = str
}

fun String.asSymbol() = Symbol(this)