package furhatos.app.chineserestaurant

import furhatos.app.chineserestaurant.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class ChineserestaurantSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
