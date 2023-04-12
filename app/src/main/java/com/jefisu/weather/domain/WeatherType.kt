package com.jefisu.weather.domain

import androidx.annotation.DrawableRes
import com.jefisu.weather.R
import java.time.LocalTime

private val isNight = LocalTime.now().hour !in 6..17

enum class WeatherType(
    val description: String,
    @DrawableRes val iconRes: Int,
    val code: Int
) {
    SunnyClear(
        description = if (isNight) "Clear" else "Sunny",
        iconRes = if (isNight) R.drawable.moon else R.drawable.sun,
        code = 1000
    ),
    PartlyCloudy(
        description = "Partly Cloudy",
        iconRes = if (isNight) R.drawable.covered_moon else R.drawable.cloud_sun,
        code = 1003
    ),
    Cloudy(
        description = "Cloudy",
        iconRes = R.drawable.cloudy_2,
        code = 1006
    ),
    Overcast(
        description = "Overcast",
        iconRes = R.drawable.cloudy_2,
        code = 1009
    ),
    Mist(
        description = "Mist",
        iconRes = R.drawable.cloudy,
        code = 1030
    ),
    PatchyRainPossible(
        description = "Patchy rain possible",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1063
    ),
    PatchySnowPossible(
        description = "Patchy snow possible",
        iconRes = R.drawable.snow,
        code = 1066
    ),
    PatchySleetPossible(
        description = "Patchy sleet possible",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1069
    ),
    PatchFreezingDrizzlePossible(
        description = "Patchy freezing drizzle possible",
        iconRes = R.drawable.cloudy_snowing,
        code = 1072
    ),
    ThunderyOutbreaksPossible(
        description = "Thundery outbreaks possible",
        iconRes = R.drawable.stormy_weather,
        code = 1087
    ),
    BlowingSnow(
        description = "Blowing snow",
        iconRes = R.drawable.cloudy_snowing,
        code = 1114
    ),
    Blizzard(
        description = "Blizzard",
        iconRes = R.drawable.cloudy_snowing,
        code = 1117
    ),
    Fog(
        description = "Fog",
        iconRes = R.drawable.cloudy_snowing,
        code = 1135
    ),
    FreezingFog(
        description = "Freezing fog",
        iconRes = R.drawable.cloudy_snowing,
        code = 1147
    ),
    PatchyLightDrizzle(
        description = "Patchy light drizzle",
        iconRes = R.drawable.raining,
        code = 1150
    ),
    LightDrizzle(
        description = "Light drizzle",
        iconRes = R.drawable.raining,
        code = 1153
    ),
    FreezingDrizzle(
        description = "Freezing drizzle",
        iconRes = R.drawable.cloudy_snowing,
        code = 1168
    ),
    HeavyFreezingDrizzle(
        description = "Heavy freezing drizzle",
        iconRes = R.drawable.cloudy_snowing,
        code = 1171
    ),
    PatchyLightRain(
        description = "Patchy light rain",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1180
    ),
    LightRain(
        description = "Light rain",
        iconRes = R.drawable.raining,
        code = 1183
    ),
    ModerateRainAtTimes(
        description = "Moderate rain at times",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1186
    ),
    ModerateRain(
        description = "Moderate rain",
        iconRes = R.drawable.raining,
        code = 1189
    ),
    HeavyRainAtTimes(
        description = "Heavy rain at times",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1192
    ),
    HeavyRain(
        description = "Heavy rain",
        iconRes = R.drawable.raining,
        code = 1195
    ),
    LightFreezingRain(
        description = "Light freezing rain",
        iconRes = R.drawable.cloudy_snowing,
        code = 1198
    ),
    LightSleet(
        description = "Light sleet",
        iconRes = R.drawable.raining_snow,
        code = 1201
    ),
    ModerateOrHeavySleet(
        description = "Moderate or heavy sleet",
        iconRes = R.drawable.raining_snow,
        code = 1204
    ),
    PatchLightSnow(
        description = "Patch light snow",
        iconRes = if (isNight) R.drawable.moon_cloudy_snowing else R.drawable.sun_cloudy_snowing,
        code = 1210
    ),
    LightSnow(
        description = "Light snow",
        iconRes = R.drawable.cloudy_snowing,
        code = 1213
    ),
    PatchyModerateSnow(
        description = "Patchy moderate snow",
        iconRes = if (isNight) R.drawable.moon_cloudy_snowing else R.drawable.sun_cloudy_snowing,
        code = 1216
    ),
    ModerateSnow(
        description = "Moderate snow",
        iconRes = R.drawable.cloudy_snowing,
        code = 1219
    ),
    PatchyHeavySnow(
        description = "Patchy heavy snow",
        iconRes = if (isNight) R.drawable.moon_cloudy_snowing else R.drawable.sun_cloudy_snowing,
        code = 1222
    ),
    HeavySnow(
        description = "Heavy snow",
        iconRes = R.drawable.cloudy_snowing,
        code = 1225
    ),
    IcePellets(
        description = "Ice pellets",
        iconRes = R.drawable.cloudy_snowing,
        code = 1237
    ),
    LightRainShower(
        description = "Light rain shower",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1240
    ),
    ModerateOrHeavyRainShower(
        description = "Moderate or heavy rain shower",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1243
    ),
    TorrentialRainShower(
        description = "Torrential rain shower",
        iconRes = if (isNight) R.drawable.moon_raining else R.drawable.cloud_sun,
        code = 1246
    ),
    LightSleetShowers(
        description = "Light sleet showers",
        iconRes = if (isNight) R.drawable.moon_raining_snowing else R.drawable.cloud_sun,
        code = 1249
    ),
    ModerateOrHeavySleetShowers(
        description = "Moderate or heavy sleet showers",
        iconRes = if (isNight) R.drawable.moon_raining_snowing else R.drawable.cloud_sun,
        code = 1252
    ),
    LightSnowShowers(
        description = "Light snow showers",
        iconRes = if (isNight) R.drawable.moon_cloudy_snowing else R.drawable.sun_cloudy_snowing,
        code = 1255
    ),
    ModerateOrHeavySnowShowers(
        description = "Moderate or heavy snow showers",
        iconRes = if (isNight) R.drawable.moon_cloudy_snowing else R.drawable.sun_cloudy_snowing,
        code = 1258
    ),
    LightShowersIcePellets(
        description = "Light showers of ice pellets",
        iconRes = if (isNight) R.drawable.moon_cloudy_snowing else R.drawable.sun_cloudy_snowing,
        code = 1261
    ),
    ModerateOrHeavySnowShowersIcePellets(
        description = "Moderate or heavy showers of ice pellets",
        iconRes = if (isNight) R.drawable.moon_cloudy_snowing else R.drawable.sun_cloudy_snowing,
        code = 1264
    ),
    PatchLightRainThunder(
        description = "Patchy light rain with thunder",
        iconRes = R.drawable.sun_storm,
        code = 1273
    ),
    ModerateOrHeavyRainThunder(
        description = "Moderate or heavy rain with thunder",
        iconRes = R.drawable.stormy_weather,
        code = 1276
    ),
    PatchyLightSnowThunder(
        description = "Patchy light snow with thunder",
        iconRes = R.drawable.storm_snow,
        code = 1279
    ),
    ModerateHeavySnowThunder(
        description = "Moderate or heavy snow with thunder",
        iconRes = R.drawable.storm_snow,
        code = 1282
    )
}