package com.bpapps.criminalintentkotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "TAG.CrimeListFragment"

class CrimeListFragment : Fragment() {
    private lateinit var crimeListViewModel: CrimeListViewModel
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crimeListViewModel = ViewModelProvider(this).get(CrimeListViewModel::class.java)

        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}  ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_crime_list, container, false) as RecyclerView

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()
        return view
    }

    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    //=========================================================================================
//=========================================================================================
//=========================================================================================
    private abstract inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        abstract fun bind(crime: Crime)
    }

    private inner class NotDangerousCrimeHolder(view: View) : CrimeHolder(view) {

        private lateinit var crime: Crime

        private val titleTextView: AppCompatTextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: AppCompatTextView = itemView.findViewById(R.id.crime_date)

        init {
            itemView.setOnClickListener(this)
        }

        override fun bind(crime: Crime) {
            this.crime = crime
            this.titleTextView.text = this.crime.title
            this.dateTextView.text = this.crime.date.toString()
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class DangerousCrimeHolder(view: View) : CrimeHolder(view) {

        private lateinit var crime: Crime

        private var specialCrimeTitle: AppCompatTextView =
            itemView.findViewById(R.id.textViewSpecialCrimeShower)
        private var buttonCall911: AppCompatButton = itemView.findViewById(R.id.buttonCall911)

        override fun bind(crime: Crime) {
            this.crime = crime
            this.specialCrimeTitle.text = this.crime.title
            this.specialCrimeTitle.setBackgroundColor(resources.getColor(R.color.red))
            this.buttonCall911.setOnClickListener {
                this.onClick(buttonCall911)
            }
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.buttonCall911) {
                Toast.makeText(context, "${crime.title} pressed\ncall 911", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "${crime.title} pressed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val holder: CrimeHolder
            if (viewType == 1) {
                holder = NotDangerousCrimeHolder(
                    layoutInflater.inflate(
                        R.layout.list_item_crime,
                        parent,
                        false
                    )
                )
            } else {
                holder = DangerousCrimeHolder(
                    layoutInflater.inflate(
                        R.layout.list_item_special_crime,
                        parent,
                        false
                    )
                )
            }

            return holder
        }

        override fun getItemCount() = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemViewType(position: Int): Int {
            return if (crimes[position].isSolved) 1 else 0
        }
    }
}













