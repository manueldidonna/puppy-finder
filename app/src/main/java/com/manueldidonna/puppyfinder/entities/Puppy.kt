package com.manueldidonna.puppyfinder.entities

import androidx.compose.runtime.Immutable

@Immutable
data class Puppy(
    val name: String,
    val breedName: String,
    val age: PuppyAge,
    val picture: PuppyPicture,
    val isFemale: Boolean,
    val description: String,
    val location: String,
    val weightInKilos: Double
)

@Immutable
data class PuppyPicture(
    val resource: Any,
    val aspectRatio: Float
)

@Immutable
data class PuppyAge(val years: Int, val months: Int) {
    override fun toString(): String {
        return buildString {
            if (years > 0) append(years).append(if (years == 1) " year" else " years")
            if (years > 0 && months > 0) append(", ")
            if (months > 0) append(months).append(if (months == 1) " month" else " months")
        }
    }
}

private const val FakeDescription =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam orci mauris, euismod sed gravida id, vestibulum sit amet ante. Aenean ut tellus sagittis, convallis justo et, facilisis ligula. Vestibulum non lorem commodo."

private const val FakeLocation = "Thornridge, Chicago"

val FakePuppies = listOf(
    Puppy(
        age = PuppyAge(years = 1, months = 7),
        name = "Toshi",
        breedName = "Pug",
        picture = PuppyPicture(
            resource = "https://images.unsplash.com/photo-1518020382113-a7e8fc38eac9?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1920&q=80",
            aspectRatio = 1920f / 2674f
        ),
        isFemale = true,
        description = FakeDescription,
        location = FakeLocation,
        weightInKilos = 1.5
    ),
    Puppy(
        age = PuppyAge(years = 0, months = 9),
        name = "Akiko",
        breedName = "Cockapoo",
        picture = PuppyPicture(
            resource = "https://images.unsplash.com/photo-1534361960057-19889db9621e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80",
            aspectRatio = 1920f / 1280f
        ),
        isFemale = true,
        description = FakeDescription,
        location = "Cleveland, OH, USA",
        weightInKilos = 1.5
    ),
    Puppy(
        age = PuppyAge(years = 1, months = 7),
        name = "Fluffy",
        breedName = "Cockapoo",
        picture = PuppyPicture(
            resource = "https://images.unsplash.com/photo-1591703166380-e36be05eb7bf?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80",
            aspectRatio = 1920f / 2880f
        ),
        isFemale = false,
        description = FakeDescription,
        location = "Paris, France",
        weightInKilos = 1.5
    ),
    Puppy(
        age = PuppyAge(years = 0, months = 7),
        name = "Yucky",
        breedName = "Havanese",
        picture = PuppyPicture(
            resource = "https://images.unsplash.com/photo-1583511655826-05700d52f4d9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80",
            aspectRatio = 1920f / 2875f
        ),
        isFemale = false,
        description = FakeDescription,
        location = "Austin, Texas, USA",
        weightInKilos = 1.5
    ),
)

val FakePuppyBreeds = listOf(
    "Affenpinscher",
    "Akita",
    "Alaskan Malamute",
    "Boerboel",
    "Boxer",
    "Brussels Griffon",
    "Biewer Terrier",
    "Bulldog",
    "Bullmastiff",
    "Chihuahua",
    "Chinese Crested",
    "Chinook",
    "Doberman Pinscher",
    "Dogo Argentino",
    "English Toy Spaniel",
    "Havanese",
    "Italian Greyhound",
    "Japenese Chin",
    "Maltese",
    "Papillon",
    "Pug",
    "Shih Tzu",
    "Silky Terrier",
    "Toy Fox Terrier",
    "Yorkshire Terrier",
)
