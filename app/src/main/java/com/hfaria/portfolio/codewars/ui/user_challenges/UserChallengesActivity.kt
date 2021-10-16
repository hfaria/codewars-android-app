package com.hfaria.portfolio.codewars.ui.user_challenges

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.hfaria.portfolio.codewars.R

class UserChallengesActivity : AppCompatActivity(R.layout.user_challenges_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<UserChallengesFragment>(R.id.fragment_container_view)
            }
        }
    }

}