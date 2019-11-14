package com.g2pdev.simpletranslator.ui

import android.os.Bundle
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
