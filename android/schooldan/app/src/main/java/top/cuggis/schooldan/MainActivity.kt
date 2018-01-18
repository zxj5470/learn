package top.cuggis.schooldan

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Toast
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.squareup.picasso.Picasso
import top.cuggis.schooldan.ext.changeToColor
import top.cuggis.schooldan.utils.BlurTransform


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    lateinit private var drawer: Drawer


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initDrawer()
    }




    private fun initDrawer() {
        val colorPrimary=resources.getColor(R.color.colorPrimary)
        val sourceDrawable = resources.getDrawable(R.drawable.ic_home_black_24dp)
        val menuDrawable = resources.getDrawable(R.drawable.ic_dashboard_black_24dp)


        val item1 = PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.title_home)
                .withIcon(sourceDrawable.changeToColor(colorPrimary))

        val item2 = SecondaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.title_dashboard)
                .withIcon(menuDrawable.changeToColor(colorPrimary))

        val header = ProfileDrawerItem()
                .withName("哲♂学家")
                .withEmail("zxj5470@gmail.com")
                .withIcon(resources.getDrawable(R.drawable.nanxiaoniao))

//create the drawer and remember the `Drawer` result object

        val headerResult = AccountHeaderBuilder()
                .withTextColorRes(R.color.colorAccent)
                .withHeaderBackground(R.drawable.nanxiaoniao)
                .withActivity(this)
                .addProfiles(header)
                .build()

        Picasso.with(this)
                .load(R.drawable.nanxiaoniao)
                .transform(BlurTransform(this))
                .fit()
                .placeholder(R.drawable.nanxiaoniao)
                .into(headerResult.headerBackgroundView)

        drawer = DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        SecondaryDrawerItem().withName(R.string.library_materialize_libraryName)
                )
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    val str: String = if (drawerItem is SecondaryDrawerItem) {
                        "SecondaryDrawerItem"
                    } else if (drawerItem is PrimaryDrawerItem) {
                        "PrimaryDrawerItem"
                    } else {
                        "Nothing"
                    }
                    Toast.makeText(this@MainActivity, str, Toast.LENGTH_LONG).show()
                    true
                }.build()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_MENU -> {
                if (drawer.isDrawerOpen) drawer.closeDrawer()
                else drawer.openDrawer()
            }
            KeyEvent.KEYCODE_BACK -> {
                if (drawer.isDrawerOpen) {
                    drawer.closeDrawer()
                    return !super.onKeyDown(keyCode, event)
                }
            }
        }
        return super.onKeyDown(keyCode, event)


    }
}
