/*
 * Hylin - Hypixel API Wrapper in Kotlin
 * Copyright (C) 2022  Skytils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package skytils.hylin.extension

import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * This code was unmodified and taken under CC BY-SA 3.0 license
 * @link https://stackoverflow.com/a/22186845
 * @author jpdymond
 */
fun Double.roundToPrecision(precision: Int): Double {
    val scale = 10.0.pow(precision).toInt()
    return (this * scale).roundToInt().toDouble() / scale
}

/**
 * This code was unmodified and taken under CC BY-SA 3.0 license
 * @link https://stackoverflow.com/a/22186845
 * @author jpdymond
 */
fun Float.roundToPrecision(precision: Int): Float {
    val scale = 10.0.pow(precision).toInt()
    return (this * scale).roundToInt().toFloat() / scale
}
