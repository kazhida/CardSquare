package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.abplus.cardsquare.entities.Account
import com.abplus.cardsquare.entities.Card
import com.abplus.cardsquare.entities.User
import com.abplus.cardsquare.utils.*
import com.abplus.cardsquare.views.SquareCardView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val REQUEST_ENTRY = 6592
        private const val REQUEST_CARD = 6593
    }

    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val navView by lazy { findViewById<NavigationView>(R.id.nav_view) }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val adapter: CardPagerAdapter by lazy { CardPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()

        val user = auth.currentUser
        if (user == null) {
            UserEntryActivity.start(this, REQUEST_ENTRY)
        } else {
            launchFB(LoadingDialog.show(this)) {
                val cards = store.collection("cards")
                        .whereEqualTo("owner", user.uid)
                        .get()
                        .defer()
                        .await()
                val accounts = store.collection("accounts")
                        .whereEqualTo("owner", user.uid)
                        .get()
                        .defer()
                        .await()
                loadAccounts(accounts)
                if (loadCards(cards) == 0) {
                    CardEditActivity.start(this, Card.initial(), REQUEST_CARD)
                } else {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun loadCards(task: Task<QuerySnapshot>): Int = task.onSuccess { result ->
        result.mapNotNull { document ->
            val uid: String? = document.getString("uid")
            if (uid == User.userId) {
                Card(
                        refId = document.refId,
                        uid = uid,
                        name = document.getStringOrEmpty("name"),
                        firstName = document.getStringOrEmpty("firstName"),
                        familyName = document.getStringOrEmpty("familyName"),
                        coverImageUrl = document.getStringOrEmpty("coverImageUrl"),
                        introduction = document.getStringOrEmpty("introduction"),
                        description = document.getStringOrEmpty("description")
                )
            } else {
                null
            }
        }.let {
            User.resetCards(it)
            adapter.notifyDataSetChanged()
            it.size
        }
    } ?: 0

    private fun loadAccounts(task: Task<QuerySnapshot>): Int {
        return task.onSuccess { result ->
            result.mapNotNull { document ->
                val refId: String = document.reference.id
                val id: String? = document.getString("id")
                val type: String? = document.getString("type")
                if (id != null && type != null) {
                    when (type) {
                        Account.GOOGLE -> Account.google(
                                refId = refId,
                                uid = document.getStringOrEmpty("uid"),
                                name = document.getStringOrEmpty("name"),
                                email = document.getStringOrEmpty("email")
                        )
                        Account.TWITTER -> Account.twitter(
                                refId = refId,
                                uid = document.getStringOrEmpty("uid"),
                                name = document.getStringOrEmpty("name"),
                                id = document.getLongOrZero("id"),
                                idAsString = document.getStringOrEmpty("name")
                        )
                        Account.FACEBOOK -> Account.facebook(
                                refId = refId,
                                uid = document.getStringOrEmpty("uid"),
                                name = document.getStringOrEmpty("name")
                        )
                        Account.GITHUB -> Account.github(
                                refId = refId,
                                uid = document.getStringOrEmpty("uid"),
                                name = document.getStringOrEmpty("name")
                        )
                        else -> null
                    }
                } else {
                    null
                }
            }.let {
                User.resetAccounts(it)
                it.size
            }
        } ?: 0
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) {
            // ユーザ登録しないので、終了する
            finish()
        }
    }

    private inner class CardPagerAdapter : PagerAdapter(), ViewPager.OnPageChangeListener {

        private val items: List<Card> get() = User.cards

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
            private val headerCard: SquareCardView = root.findViewById(R.id.header_card)
            private val partnerList: ListView = root.findViewById(R.id.partner_list)

            fun update(card: Card) {
                headerCard.update(card)
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
                    nameText.text = card.name
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
