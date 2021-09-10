package com.example.phonefirebase.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import java.util.ArrayList

class PagerAdapter(var list: ArrayList<Fragment>, fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return list[position]

    }

    override fun getPageTitle(position: Int): CharSequence? {
        var typeList = arrayOf("   Chats   ", "   Groups   ")
        return typeList[position]
    }

    fun addFragment(fragment: Fragment, string: String) {
        list.add(fragment)

    }

}