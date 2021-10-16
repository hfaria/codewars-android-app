package com.hfaria.portfolio.codewars.ui.challenge_profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.hfaria.portfolio.codewars.R

class ChallengeProfileActivity : AppCompatActivity(R.layout.challenge_profile_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ChallengeProfileFragment>(R.id.fragment_container_view)
            }
        }
    }
}
