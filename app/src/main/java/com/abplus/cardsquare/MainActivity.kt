package com.abplus.cardsquare

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.abplus.cardsquare.entities.Card

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val navView by lazy { findViewById<NavigationView>(R.id.nav_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_card -> {
                // todo 編集画面に遷移
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_notification -> {
                // Handle the camera action
            }
            R.id.nav_my_cards -> {

            }
            R.id.nav_square -> {

            }
            R.id.nav_accounts -> {

            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private inner class CardPagerAdapter : PagerAdapter(), ViewPager.OnPageChangeListener {

        private val items = ArrayList<Card>()

        fun resetCards(cards: List<Card>) {
            items.clear()
            items.addAll(cards)
            notifyDataSetChanged()
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj
        override fun getCount(): Int = items.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val factory = ViewFactory(container)

            if (position < items.size) {
                factory.update(items[position])
            }
            container.addView(factory.root)
            factory.root.tag = factory

            return factory.root
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {

        }

        private inner class ViewFactory(parent: ViewGroup) {
            val root: View = layoutInflater.inflate(R.layout.view_my_card, parent, false)
           private val headerCard = root.findViewById<CardView>(R.id.header_card)
            private val nameText = root.findViewById<TextView>(R.id.name_text)
            private val phoneticText = root.findViewById<TextView>(R.id.phonetic_text)
            private val descriptionText = root.findViewById<TextView>(R.id.description_text)
            private val addressText = root.findViewById<TextView>(R.id.address_text)
            private val partnerList = root.findViewById<ListView>(R.id.partner_list)

            fun update(card: Card) {
                //todo cardの内容を表示
            }
        }

        private inner class PartnerAdapter : BaseAdapter() {

            private val items = ArrayList<Card>()

            override fun getItem(position: Int): Any = items[position]
            override fun getItemId(position: Int): Long = position.toLong()
            override fun getCount(): Int = items.size

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = convertView ?: layoutInflater.inflate(R.layout.view_partner_item, parent, false).apply {
                    tag = PartnerViewHolder(this)
                }
                val holder = view.tag as PartnerViewHolder
                holder.update(items[position])
                return view
            }

            private inner class PartnerViewHolder(view: View) {
                private val imageView by lazy { view.findViewById<ImageView>(R.id.partner_icon) }
                private val nameText by lazy { view.findViewById<TextView>(R.id.partner_name_text) }
                private val phneticText by lazy { view.findViewById<TextView>(R.id.partner_phonetic_text) }

                fun update(card: Card) {
                    // todo cardの内容を表示
                }
            }
        }
    }
}
