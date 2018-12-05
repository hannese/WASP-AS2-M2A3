package furhatos.app.pizzabakery

import furhatos.app.pizzabakery.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class PizzabakerySkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
