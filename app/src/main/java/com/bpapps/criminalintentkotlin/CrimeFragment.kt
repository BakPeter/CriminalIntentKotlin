package com.bpapps.criminalintentkotlin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment

private val  TAG  = "TAG" + CrimeFragment::class.java.simpleName

class CrimeFragment : Fragment() {

    private lateinit var crime: Crime
    private lateinit var titleField: AppCompatEditText
    private lateinit var dateButton: AppCompatButton
    private lateinit var solvedCheckBox: AppCompatCheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)

        titleField = view.findViewById(R.id.crime_title) as AppCompatEditText
        dateButton = view.findViewById(R.id.crime_date) as AppCompatButton
        solvedCheckBox = view.findViewById(R.id.crime_solved) as AppCompatCheckBox

        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        titleField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = s.toString()
                Log.d(TAG, crime.toString())
            }
        })

        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
                Log.d(TAG, crime.toString())
            }
        }
    }
}