package com.github.zxj5470

/**
 * @author: zxj5470
 * @date: 2018/3/16
 */

/**
 * construct a length-specialized membership tester
 */
fun Set(l: List<Any>):AnyToBoolean =
		{ it in l.map { it.toString() to true }.toMap() }
