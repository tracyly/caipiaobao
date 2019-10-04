package com.fenghuang.caipiaobao.ui.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.fenghuang.caipiaobao.test.TestFragment
import com.pingerx.mqtt.MqttConfig
import com.pingerx.mqtt.MqttManager
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch


@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testPrint() {
        println("--------->test MainActivity print")

    }

    @Test
    fun testMqttConnect() {
        MqttManager.getInstance().init(mActivityRule.activity, MqttConfig().create())
        MqttManager.getInstance().connect()
    }

    @Test
    fun startFragment() {
        mActivityRule.activity.start(TestFragment())
    }

    @Test
    fun blockingTest() {
        // Add your own intent extras here if applicable.
        //mActivityRule.launchActivity(Intent())
        startFragment()
        CountDownLatch(1).await()
    }
}

