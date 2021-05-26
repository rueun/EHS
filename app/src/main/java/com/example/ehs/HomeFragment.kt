package com.example.ehs

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class HomeFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    var now = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    //lateinit var text: TextView
    lateinit var calendarbtn: ImageButton

    companion object {
        const val TAG : String = "홈 프레그먼트"
        fun newInstance() : HomeFragment { // newInstance()라는 함수를 호출하면 HomeFragment를 반환함
            return HomeFragment()
        }
    }

    // 프래그먼트가 메모리에 올라갔을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "HomeFragment - onCreate() called")
    }
    // 프래그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "HomeFragment - onAttach() called")
    }

    // 뷰가 생성되었을 때 화면과 연결
    // 프레그먼트와 레이아웃을 연결시켜주는 부분이다.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "HomeFragment - onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //text = view.findViewById(R.id.textView)
        calendarbtn = view.findViewById(R.id.btn_calendar)

        calendarbtn.setOnClickListener{
            activity?.let{
                val intent = Intent(context, CalendarActivity::class.java)
                startActivity(intent) }
        }

//        text.setOnClickListener {
//            activity?.let{
//                val intent = Intent(context, CalendarActivity::class.java)
//              startActivity(intent) }
//        }


        week(Strnow)
        return view
    }

    /**
     * 특정 날짜의 같은 한 주간의 날짜 범위
     */

    fun week(eventDate: String) {
        val dateArray = eventDate.split("-").toTypedArray()
        val cal = Calendar.getInstance()
        cal[dateArray[0].toInt(), dateArray[1].toInt() - 1] = dateArray[2].toInt()

        // 일주일의 첫날을 일요일로 지정
        cal.firstDayOfWeek = Calendar.SUNDAY

        // 시작일과 특정날짜의 차이를 구한다
        val dayOfWeek = cal[Calendar.DAY_OF_WEEK] - cal.firstDayOfWeek

        // 해당 주차의 첫째날을 지정한다
        cal.add(Calendar.DAY_OF_MONTH, -dayOfWeek)
        val sf = SimpleDateFormat("yyyy-MM-dd")

        // 해당 주차의 첫째 날짜
        val startDt = sf.format(cal.time)

        // 해당 주차의 마지막 날짜 지정
        cal.add(Calendar.DAY_OF_MONTH, 6)

        // 해당 주차의 마지막 날짜
        val endDt = sf.format(cal.time)

        Log.d(TAG, "특정 날짜 = [$eventDate] >> 시작 날짜 = [$startDt], 종료 날짜 = [$endDt]")
    }

}