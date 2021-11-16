package ru.iu3.weatherapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.iu3.weatherapplication.networking.data.models.DaysInformation
import ru.iu3.weatherapplication.networking.data.models.HoursInformation
import ru.iu3.weatherapplication.networking.data.models.OneCallWeatherInformation
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round


class ViewPagerAdapter(weather: LiveData<OneCallWeatherInformation<DaysInformation, HoursInformation>?>) :
    RecyclerView.Adapter<PagerVH>() {
    private val weather: LiveData<OneCallWeatherInformation<DaysInformation, HoursInformation>?> =
        weather

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weather_information, parent, false)
        )

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {

        val temp: ArrayList<TextView> = arrayListOf()
        temp.add(findViewById(R.id.temp1))
        temp.add(findViewById(R.id.temp2))
        temp.add(findViewById(R.id.temp3))
        temp.add(findViewById(R.id.temp4))
        temp.add(findViewById(R.id.temp5))
        temp.add(findViewById(R.id.temp6))
        temp.add(findViewById(R.id.temp7))
        temp.add(findViewById(R.id.temp8))

        val date: ArrayList<TextView> = arrayListOf()
        date.add(findViewById(R.id.date1))
        date.add(findViewById(R.id.date2))
        date.add(findViewById(R.id.date3))
        date.add(findViewById(R.id.date4))
        date.add(findViewById(R.id.date5))
        date.add(findViewById(R.id.date6))
        date.add(findViewById(R.id.date7))
        date.add(findViewById(R.id.date8))


        val image: ArrayList<ImageView> = arrayListOf()
        image.add(findViewById(R.id.ImageWeatherView1))
        image.add(findViewById(R.id.ImageWeatherView2))
        image.add(findViewById(R.id.ImageWeatherView3))
        image.add(findViewById(R.id.ImageWeatherView4))
        image.add(findViewById(R.id.ImageWeatherView5))
        image.add(findViewById(R.id.ImageWeatherView6))
        image.add(findViewById(R.id.ImageWeatherView7))
        image.add(findViewById(R.id.ImageWeatherView8))

        val name = findViewById<TextView>(R.id.name)
        if (position == 0)
            name.setText(R.string.item_weather_infotmation_daily)
        else
            name.setText(R.string.item_weather_infotmation_hourly)



        weather.observeForever {
            if (position == 0) {
                for (i in 0..7) {
                    weather.value?.daily?.get(i)?.weather?.get(0)?.icon?.let { it1 ->
                        loadPicture(
                            it1, image[i], this.context
                        )
                    }
                    temp[i].setText(
                        weather.value?.daily?.get(i)?.temp?.min?.let { it1 ->
                            round(it1).toInt().toString()
                        } + " / " + weather.value?.daily?.get(i)?.temp?.max?.let { it1 ->
                            round(it1).toInt().toString()
                        }
                    )
                    date[i].setText(
                        weather.value?.timezone_offset?.let { it1 ->
                            weather.value?.daily?.get(i)?.let { it2 ->
                                dateTime(
                                    it2.dt,
                                    it1, "EEE"
                                )
                            }
                        }
                    )
                }
            } else {
                var j = 0
                for (i in 0..21 step 3) {
                    weather.value?.hourly?.get(i)?.weather?.get(0)?.icon?.let { it1 ->
                        loadPicture(
                            it1, image[j], this.context
                        )
                    }
                    temp[j].setText(
                        weather.value?.hourly?.get(i)?.temp?.let { it1 ->
                            round(it1).toInt().toString()
                        }
                    )
                    date[j].setText(
                        weather.value?.timezone_offset?.let { it1 ->
                            weather.value?.hourly?.get(i)?.let { it2 ->
                                dateTime(
                                    it2.dt,
                                    it1,
                                    "HH:mm"
                                )
                            }
                        }
                    )
                    j += 1
                }
            }
        }

    }


    private fun dateTime(
        time: Int,
        zone: Int,
        format: String = "EEE, yyyy-MM-dd HH:mm:ss"
    ): String {
        return try {
            val sdf = SimpleDateFormat(format)
            val netDate = Date((time.plus(zone)).toLong() * 1000)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun loadPicture(icon: String, image: ImageView, context: Context) {
        val urlPicture = "https://openweathermap.org/img/wn/" + icon + "@2x.png"
        Glide
            .with(context.applicationContext)
            .load(urlPicture)
            .into(image);
    }


}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)