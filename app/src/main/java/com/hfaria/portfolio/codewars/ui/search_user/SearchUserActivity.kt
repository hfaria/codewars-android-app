package com.hfaria.portfolio.codewars.ui.search_user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.hfaria.portfolio.codewars.R

class SearchUserActivity : AppCompatActivity(R.layout.search_user_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SearchUserFragment>(R.id.fragment_container_view)
            }
        }
    }
}