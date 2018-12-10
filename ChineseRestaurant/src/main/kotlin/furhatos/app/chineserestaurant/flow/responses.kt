package furhatos.app.chineserestaurant.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.chineserestaurant.nlu.*
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

val Cost:State = state(Interaction) {

    onEntry {
        furhat.say("It costs 345 dollars.")
        goto(AskDelivery)
    }

}

class DeliveryOptions: EnumEntity(stemming = true, speechRecPhrases=true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("home", "office", "park", "wherever")
    }
}


class DeliveryResponse(var option : DeliveryOptions? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@option", "I want it @option", "Send it to @option", "I want it to the @option", "Send it to the @option")
    }
}

fun sendDelivery(option : DeliveryOptions):State = state(Interaction) {

    onEntry {
        furhat.say("I will send it to ${option.text}. That will take 15 minutes. Thank you for your order. Take care. Buy!")
        goto(Idle)
    }
}

val AskDelivery:State = state(Interaction) {

    onEntry {
        furhat.ask("To where do you want it to be delivered?")
    }

    onResponse<DeliveryResponse> {
        val deliveryOption = it.intent.option
        if (deliveryOption != null) {
            goto(sendDelivery(deliveryOption))
        } else {
            propagate()
        }
    }

    onResponse{
        furhat.say("Doh!")
        propagate()
    }

    onNoResponse{
        propagate()
    }

}