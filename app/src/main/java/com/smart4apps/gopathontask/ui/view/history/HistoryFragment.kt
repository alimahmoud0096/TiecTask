package com.smart4apps.gopathontask.ui.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.smart4apps.gopathontask.data.model.currencies.ConvertHistory
import com.smart4apps.gopathontask.data.model.currencies.CurrenciesModel
import com.smart4apps.gopathontask.databinding.FragmentHistoryBinding
import com.smart4apps.gopathontask.ui.adapter.ConvertedHistoryAdapter
import com.smart4apps.gopathontask.ui.view.MainActivity
import com.smart4apps.gopathontask.ui.viewmodel.HistoryViewModel
import com.smart4apps.gopathontask.ui.viewmodel.MainViewModel
import com.smart4apps.gopathontask.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private val historyViewModel: HistoryViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapter: ConvertedHistoryAdapter

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }


    private fun init() {

        binding.rvHistory.adapter=adapter


        val currentDate = Date()

        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        // Subtract three days from the current date
        calendar.add(Calendar.DAY_OF_YEAR, -3)

        val threeDaysAgo = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
        val formattedThreeDaysAgo = sdf.format(threeDaysAgo)
        calendar.time = currentDate
        // Subtract three days from the current date
        calendar.add(Calendar.DAY_OF_YEAR, -3)

        val twoDaysAgo = calendar.time
        val formattedtwoDaysAgo = sdf.format(threeDaysAgo)

        calendar.time = currentDate
        // Subtract three days from the current date
        calendar.add(Calendar.DAY_OF_YEAR, -3)

        val t0DaysAgo = calendar.time
        val t0DaysAgotwoDaysAgo = sdf.format(threeDaysAgo)

        listRatesWithBase(formattedThreeDaysAgo.toString(),null)
        listRatesWithBase(formattedtwoDaysAgo.toString(),null)
        listRatesWithBase(t0DaysAgotwoDaysAgo.toString(),null)
        listRatesWithBase()
    }


    private fun listRatesWithBase(date:String,
                                  symbols:String?) {
        mainViewModel.getHistoricalData(date, symbols).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    (activity as MainActivity).showProgress()
                    it.data?.let { rates ->
                        {

                            setupUi(rates)

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

    private fun listRatesWithBase() {
        mainViewModel.listRatesWithBase().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    (activity as MainActivity).showProgress()
                    it.data?.let { rates ->
                        {

                            setupUi(rates)

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

    private fun setupUi(rates: CurrenciesModel) {

        if(rates.historical?:false) {
            val list = rates.rateModel?.map {
                ConvertHistory(
                    rates.timestamp,
                    from = rates.base,
                    to = it.key,
                    ratio = it.value.toString()
                )
            }?.toMutableList()
            renderList(list ?: arrayListOf())
        }else{
            //setup convert two ten
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
            setupAndConvertCurrency("","",rates)
        }
    }


    fun convertCurrency(amount: Double, exchangeRate: Double): Double {
        return amount * exchangeRate
    }


    private fun setupAndConvertCurrency(selectedFromCurrency:String,selectedToCurrency:String,rates: CurrenciesModel) {
        val amountInBase =1.0
        val exchangeRateFrom = getExchangeRate(selectedFromCurrency, rates.rateModel)!!
        val exchangeRateOfTo = getExchangeRate(selectedToCurrency, rates.rateModel)!!


        val amountInOtherCurrency =
            exchangeRateOfTo / convertCurrency(amountInBase!!, exchangeRateFrom)



    }


    private fun getExchangeRate(key: String, rateModel: Map<String, Double>?): Double? {
        return rateModel?.get(key) as? Double
    }

    private fun renderList(convertHistorys: List<ConvertHistory>) {
        adapter.apply {
            addData(convertHistorys)
            notifyDataSetChanged()
        }
    }



}