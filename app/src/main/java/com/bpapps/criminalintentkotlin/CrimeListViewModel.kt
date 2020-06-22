package com.bpapps.criminalintentkotlin

import androidx.lifecycle.ViewModel

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100) {
            val crime = Crime(title = "Crime #$i", isSolved = i % 3 == 0)

            crimes += crime
        }
    }
}