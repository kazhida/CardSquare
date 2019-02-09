package com.abplus.cardsquare.app.ui.cardlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.abplus.cardsquare.app.R
import com.abplus.cardsquare.app.utils.GlideApp
import com.abplus.cardsquare.domain.entities.Card

/**
 * 自分のカードを表示するアクティビティ
 *
 * 初回の登録時以後は、これがアプリの起点になる
 */
class CardListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val CARDS = "CARDS"

        fun start(activity: Activity, cards: List<Card>, requestCode: Int) {
            Intent(activity, CardListActivity::class.java).let {
                it.putExtra(CARDS, cards.toTypedArray())
                activity.startActivityForResult(it, requestCode)
            }
        }
    }

    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val drawerLayout: DrawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val viewParent: ViewPager by lazy { findViewById<ViewPager>(R.id.view_pager) }
    private val navView: NavigationView by lazy { findViewById<NavigationView>(R.id.nav_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val cards = intent.getParcelableArrayExtra(CARDS).map { it as Card }
        viewParent.adapter = CardPagerAdapter(cards)

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
        menuInflater.inflate(R.menu.card_list, menu)
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

    private inner class CardPagerAdapter(private val items: List<Card>) : PagerAdapter(), ViewPager.OnPageChangeListener {

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
            private val partnerList: ListView = root.findViewById(R.id.partner_list)

            fun update(card: Card) {
                //headerCard.update(cardViewModel)
                partnerList.adapter = PartnerAdapter(card.partners)
            }
        }

        private inner class PartnerAdapter(partners: List<Card>) : BaseAdapter() {

            private val items = ArrayList<Card>().apply {
                addAll(partners)
            }

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
                private val imageView: ImageView = view.findViewById(R.id.partner_icon)
                private val nameText: TextView = view.findViewById(R.id.partner_name_text)
                private val firstNameText: TextView = view.findViewById(R.id.partner_first_name_text)
                private val familyNameText: TextView = view.findViewById(R.id.partner_family_name_text)

                fun update(card: Card) {
                    imageView.loadUrl(card.coverImageUrl)
                    nameText.text = card.handleName
                    firstNameText.text = card.firstName
                    familyNameText.text = card.familyName
                }
            }
        }
    }

    private fun ImageView.loadUrl(url: String) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .into(this)
    }
}
