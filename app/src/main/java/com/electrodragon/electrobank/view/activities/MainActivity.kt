package com.electrodragon.electrobank.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.electrodragon.electrobank.R
import com.electrodragon.electrobank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}