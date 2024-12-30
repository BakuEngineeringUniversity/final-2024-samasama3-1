package com.example.petpal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetPalApplication

fun main(args: Array<String>) {
	runApplication<PetPalApplication>(*args)
}
