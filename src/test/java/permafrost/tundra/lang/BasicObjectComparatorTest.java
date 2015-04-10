/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Lachlan Dowding
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package permafrost.tundra.lang;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicObjectComparatorTest {
    @Test
    public void testCompareGreaterThan() throws Exception {
        Object object1 = "XYZ";
        Object object2 = "ABC";

        assertTrue(BasicObjectComparator.INSTANCE.compare(object1, object2) > 0);
    }

    @Test
    public void testCompareLessThan() throws Exception {
        Object object1 = "ABC";
        Object object2 = "XYZ";

        assertTrue(BasicObjectComparator.INSTANCE.compare(object1, object2) < 0);
    }

    @Test
    public void testCompareEqual() throws Exception {
        Object object1 = new Integer(1);
        Object object2 = new Integer(1);

        assertTrue(BasicObjectComparator.INSTANCE.compare(object1, object2) == 0);
    }

    @Test
    public void testCompareIncomparable() throws Exception {
        Object object1 = new Object();
        Object object2 = new Object();

        assertTrue(BasicObjectComparator.INSTANCE.compare(object1, object2) != 0);
    }
}