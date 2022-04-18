package com.careers.edvora.data.mapper

import com.careers.edvora.domain.model.Ride
import com.careers.edvora.domain.model.State

fun List<Ride>.toStates() = map {
    State(it.state, it.city)
}

fun List<State>.toStateNames() = map {
    it.stateName
}.distinct()

fun List<State>.toCityNames() = map {
    it.cityName
}.distinct()

fun List<State>.toCityNames(state: String) = filter {
    it.stateName == state
}.map { it.cityName }.distinct()