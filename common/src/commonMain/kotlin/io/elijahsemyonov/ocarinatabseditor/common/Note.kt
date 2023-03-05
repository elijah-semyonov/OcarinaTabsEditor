package io.elijahsemyonov.ocarinatabseditor.common

/// enum for all notes
sealed class Note(val octave: UInt)

class C(octave: UInt) : Note(octave)
class CSharp(octave: UInt) : Note(octave)
class D(octave: UInt) : Note(octave)
class DSharp(octave: UInt) : Note(octave)
class E(octave: UInt) : Note(octave)
class F(octave: UInt) : Note(octave)
class FSharp(octave: UInt) : Note(octave)
class G(octave: UInt) : Note(octave)
class GSharp(octave: UInt) : Note(octave)
class A(octave: UInt) : Note(octave)
class ASharp(octave: UInt) : Note(octave)
class B(octave: UInt) : Note(octave)

val Note.name: String
    get() {
        val note = when (this) {
            is C -> "C"
            is CSharp -> "C#"
            is D -> "D"
            is DSharp -> "D#"
            is E -> "E"
            is F -> "F"
            is FSharp -> "F#"
            is G -> "G"
            is GSharp -> "G#"
            is A -> "A"
            is ASharp -> "A#"
            is B -> "B"
        }
        return "$note$octave"
    }
