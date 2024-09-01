package me.beanbag.nuker.chat

interface ICommandArgument {
    val subArgumentCount:Int
        get() = 1

    fun getMatch(toMatch: List<String>): MatchType

    fun getSuggestions(toMatch: List<String>): List<String>
}