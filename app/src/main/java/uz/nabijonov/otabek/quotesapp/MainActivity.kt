package uz.nabijonov.otabek.quotesapp

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import uz.nabijonov.otabek.quotesapp.adapter.QuotesAdapter
import uz.nabijonov.otabek.quotesapp.model.QuotesModel

class MainActivity : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var userArrayList: ArrayList<QuotesModel>
    private lateinit var myadapter: QuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recycler.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myadapter = QuotesAdapter(userArrayList, copyListener)
        recycler.adapter = myadapter
        getUserData()
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Quotes")
        dbref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(QuotesModel::class.java)
                        userArrayList.add(user!!)
                    }
                }
                myadapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_LONG).show()
            }
        })
    }


    private val copyListener: CopyListener = object : CopyListener {
        override fun onCopyClicked(text: String) {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copied data", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@MainActivity, "Copied", Toast.LENGTH_LONG).show()
        }
    }
}
