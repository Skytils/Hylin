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

package skytils.hylin.skyblock.dungeons

enum class DungeonClass(val className: String) {
    ARCHER("Archer"),
    BERSERK("Berserk"),
    MAGE("Mage"),
    HEALER("Healer"),
    TANK("Tank"),
    EMPTY("Empty");

    override fun toString(): String {
        return this.className
    }

    companion object {
        fun getClassFromName(name: String): DungeonClass {
            return values().find { it.className.lowercase() == name.lowercase() }
                ?: throw IllegalArgumentException("No class could be found for the name $name")
        }
    }
}