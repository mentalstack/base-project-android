package com.mentalstack.baseproject

import android.os.Bundle
import com.mentalstack.baseproject.utils.ui.MSActivity

class MainActivity : MSActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
