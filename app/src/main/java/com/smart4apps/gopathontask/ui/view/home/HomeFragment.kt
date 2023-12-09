package com.smart4apps.gopathontask.ui.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.smart4apps.gopathontask.R
import com.smart4apps.gopathontask.data.model.currencies.ConvertHistory
import com.smart4apps.gopathontask.data.model.currencies.CurrenciesModel
import com.smart4apps.gopathontask.databinding.FragmentHomeBinding
import com.smart4apps.gopathontask.ui.view.MainActivity
import com.smart4apps.gopathontask.ui.viewmodel.HistoryViewModel
import com.smart4apps.gopathontask.ui.viewmodel.MainViewModel
import com.smart4apps.gopathontask.utils.Status
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var amountInBase: Double? = 1.0
    private lateinit var selectedFromCurrency: String
    private lateinit var selectedToCurrency: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listRatesWithBase()
        setupClickListners()
    }

    private fun setupClickListners() {
        binding.reverse.setOnClickListener {
            selectedFromCurrency = binding.etSpinnerFrom.text.toString()
            selectedToCurrency = binding.etSpinnerTo.text.toString()
            binding.etSpinnerFrom.setText(selectedToCurrency)
            binding.etSpinnerTo.setText(selectedFromCurrency)
            listRatesWithBase()
        }
    }

    private fun init() {


        binding.btnShowDetails.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }

    }

    private fun setupFromSpinner(keys: Set<String>?) {

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                (keys ?: arrayListOf<String>()).toList()
//                getCurrencyList()
            )
        binding.etSpinnerFrom.setAdapter(adapter)
        binding.etSpinnerFrom.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("TAG", "onItemSelectedFrom:${position} ")
                selectedFromCurrency = binding.etSpinnerFrom.text.toString()

            }
        }
    }

    private fun setupToSpinner(keys: Set<String>?) {

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                (keys ?: arrayListOf<String>()).toList()//getCurrencyList()
            )
        binding.etSpinnerTo.setAdapter(adapter)
        binding.etSpinnerTo.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("TAG", "onItemSelectedFrom:${position} ")
                selectedToCurrency = binding.etSpinnerTo.text.toString()
                listRatesWithBase()

            }
        }
    }


    private fun listRatesWithBase() {
        mainViewModel.listRatesWithBase().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    (activity as MainActivity).showProgress()
                    it.data?.let { rates ->
                        {

                            setupUi(rates)
                         /*   historyViewModel.insertConvertHistory(
                                ConvertHistory(
                                    timestamp = rates.timestamp,
                                    date = rates.date,
                                    from = selectedFromCurrency,
                                    to = selectedToCurrency,
                                    amount = amountInBase.toString(),
                                    ratio = binding.etSpinnerToInput.text.toString()
                                )
                            )*/
                        }
                    }
                }

                Status.LOADING -> {
                    (activity as MainActivity).showProgress()
                }

                Status.ERROR -> {
                    //Handle Error
                    (activity as MainActivity).hideProgress()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    fun convertCurrency(amount: Double, exchangeRate: Double): Double {
        return amount * exchangeRate
    }


    private fun setupUi(rates: CurrenciesModel) {
        setupFromSpinner(rates.rateModel?.keys)
        setupToSpinner(rates.rateModel?.keys)

        if (::selectedToCurrency.isInitialized) {
            setupAndConvertCurrency(rates)
        } else {
            selectedFromCurrency = rates.base!!
            selectedToCurrency = rates.rateModel?.keys?.toList()?.get(0)!!
            binding.etSpinnerFrom.setText(selectedFromCurrency)
            binding.etSpinnerTo.setText(selectedToCurrency)
            setupAndConvertCurrency(rates)
        }

    }

    private fun setupAndConvertCurrency(rates: CurrenciesModel) {
        amountInBase = (when {
            binding.etSpinnerFromInput.text.isNullOrEmpty() -> {
                1
            }

            else -> {
                binding.etSpinnerFromInput.text
            }
        }).toString().toDouble()
        val exchangeRateFrom = getExchangeRate(selectedFromCurrency, rates.rateModel)!!
        val exchangeRateOfTo = getExchangeRate(selectedToCurrency, rates.rateModel)!!


        val amountInOtherCurrency =
            exchangeRateOfTo / convertCurrency(amountInBase!!, exchangeRateFrom)



        binding.etSpinnerToInput.setText(amountInOtherCurrency.toString())
     }

    private fun getExchangeRate(key: String, rateModel: Map<String, Double>?): Double? {
        return rateModel?.get(key) as? Double
    }

    /* companion object {
         */
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     *//*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}