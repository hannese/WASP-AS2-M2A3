package furhatos.app.chineserestaurant.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.chineserestaurant.nlu.*
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

class Food: EnumEntity(stemming = true, speechRecPhrases=true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("Wonton", "Peking duck", "Fried chicken", "Fried shrimps", "Spring rolls")
    }
}

class BuyFood(var food : Food? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@food", "I want @food", "Give me @food", "I would like to buy @food")
    }
}

val Start : State = state(Interaction) {

    onEntry {
        furhat.ask("Hello. Would you like to order something?")
    }

    onResponse<Yes>{
        furhat.say("Nice.")
        goto(Intermediary)

    }

    onResponse<No>{
        furhat.say("That's sad.")
        goto(Idle)
    }
}

val Intermediary : State = state(Start) {
    onEntry {
        furhat.ask("Do you want to see the menu?")
    }

    onResponse<Yes> {
        furhat.say("Wonton")
        furhat.say("Peking duck")
        furhat.say("Fried chicken")
        furhat.say("Fried shrimps")
        furhat.say("Spring rolls")
        goto(Order)

    }

    onResponse {
        goto(Order)
    }

}

val Order : State = state(Start) {
    onEntry {
        furhat.ask("Which item would you like to take?")
    }

    onResponse<BuyFood> {
        val food = it.intent.food
        if (food != null) {
            goto(OrderReceived(food))
        } else {
            propagate()
        }
    }

}

fun OrderReceived(food : Food) : State = state(Interaction) {
    onEntry {
        furhat.say("${food.text}, good stuff!")
        goto(Order)
    }
}c