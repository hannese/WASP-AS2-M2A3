package furhatos.app.pizzabakery.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.nlu.*
import furhatos.util.Language

import furhatos.app.pizzabakery.*





class Food: EnumEntity(stemming = true, speechRecPhrases=true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("kebab pizza", "Hawaii", "Vesuvio")
    }
}


class BuyFood(var food : Food? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@food", "I want @food", "Give me @food", "I would like to buy @food")
    }
}

fun OrderReceived(food : Food) : State = state(Interaction) {
    onEntry {
        furhat.say("${food.text}, good stuff!")
        furhat.say("15 minutes a quarter")
        goto(Idle)
    }
}

val Options = state(Interaction) {
    onEntry {
        furhat.ask("What do you want?")

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

val Start : State = state(Interaction) {

    onEntry {
        random(
                { furhat.ask("Hello?")},
                { furhat.ask("Yes?")}
        )
        goto(Options)
    }

    onResponse<Yes>{
        goto(Options)
    }

    onResponse {
        furhat.say("Bye!")
        goto(Idle)
    }
    onNoResponse { // Catches silence
        furhat.say("What is wrong with you?")
        goto(Idle)
    }
}





