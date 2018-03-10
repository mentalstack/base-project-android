package com.mentalstack.baseproject

import com.mentalstack.baseproject.utils.nativeData.*
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun collectionTests() {
        val some = mapOf( 1 to 1, 2 to 2, 3 to 3, 4 to 44)

        assertEquals( 44, some.findFirst { it.key == 4 }?.second)
        assertEquals( null, some.findFirst { it.key == 5 }?.second)

        assertEquals( 3, some.toList().firstOf { if(it.second == 3) it.first else null })
        assertEquals( null, some.toList().firstOf { null })

        assertEquals( 3, some.findFirstValue(3)?.first)
        assertEquals( null, some.findFirstValue(5))

        assertEquals( 44, some.findFirstKey(4)?.second)
        assertEquals( null, some.findFirstValue(5))

        val some2 = some.toList().toMutableList()

        assertEquals( 2, some2.removeWithCond { it.first<3 }.size)
        assertEquals( 2, some2.size)
        assertEquals( true, some2.firstOf { if(it.second == 44) true else null})
    }

    @Test
    fun calendarTests() {
        val calendar = calendarFrom( year = 1000, date = 5, month = 9)
        val calendar2 = calendar.copy()

        assert(calendar.intDay() == calendar2.intDay())
        assert(calendar.intYear() == 1000)
        assert(calendar2 != calendar)
    }
}
