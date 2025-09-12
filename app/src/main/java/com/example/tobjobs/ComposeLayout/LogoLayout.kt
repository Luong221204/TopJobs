package com.example.tobjobs.ComposeLayout

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Dimension.Companion.wrapContent
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionScene

@OptIn(ExperimentalMotionApi::class)
val motionScene1 = MotionScene {
    val appName = createRefFor("app_name")
    val slogan = createRefFor("slogan")
    val title = createRefFor("title")
    val nested = createRefFor("nested")

    val startl = constraintSet("start") {
        constrain(appName) {
            alpha = 1f
            width = Dimension.value(200.dp)
            height = wrapContent
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 48.dp)
            customFloat("fontSize", 45f)

        }
        constrain(slogan) {
            alpha = 1f
            width = Dimension.value(300.dp)
            height = wrapContent
            start.linkTo(appName.start)
            end.linkTo(appName.end)
            top.linkTo(appName.bottom, 8.dp)
            customFloat("fontSize", 18f)

        }
        constrain(title) {
            width = Dimension.value(200.dp)
            height = wrapContent
            alpha = 1f
            start.linkTo(slogan.start)
            end.linkTo(slogan.end)
            top.linkTo(slogan.bottom, 28.dp)
            customFloat("fontSize", 26f)

        }
        constrain(nested) {
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
            top.linkTo(title.bottom, 16.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    val end = constraintSet("end") {
        constrain(appName) {
            alpha = 1f
            width = wrapContent
            height = wrapContent
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 4.dp)
            customFloat("fontSize", 26f)

        }
        constrain(slogan) {
            alpha = 1f
            width = wrapContent
            height = wrapContent
            start.linkTo(appName.start)
            end.linkTo(appName.end)
            top.linkTo(appName.bottom)
            customFloat("fontSize", 9f)

        }
        constrain(title) {
            alpha = 1f
            width = wrapContent
            height = wrapContent
            start.linkTo(slogan.start)
            end.linkTo(slogan.end)
            top.linkTo(slogan.bottom, 18.dp)

            // custom properties (textSize)
            customFloat("fontSize", 26f)
        }
        constrain(nested) {
            width = Dimension.matchParent
            height = Dimension.fillToConstraints
            top.linkTo(title.bottom, 16.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    defaultTransition(
        from = startl,
        to = end
    ) {
    }
}