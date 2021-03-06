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

package permafrost.tundra.data;

import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.util.coder.IDataCodable;
import permafrost.tundra.lang.BooleanHelper;

/**
 * Defines a single criterion used by the IDataComparator class.
 */
public class IDataComparisonCriterion implements IDataCodable {

    protected String key, pattern;
    protected IDataComparisonType type;
    protected boolean descending;

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key The IData key on which the comparison will be made: the values associated with this key will be
     *            compared in ascending order.
     */
    public IDataComparisonCriterion(String key) {
        this(key, false);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key        The IData key on which the comparison will be made.
     * @param descending If true, the values associated with this key will be compared in descending order, otherwise
     *                   they will be compared in ascending order.
     */
    public IDataComparisonCriterion(String key, boolean descending) {
        this(key, (IDataComparisonType)null, (String)null, descending);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key  The IData key on which the comparison will be made.
     * @param type The type of comparison to be used; must be one of the enumeration values in the IDataComparisonType
     *             enumeration.
     */
    public IDataComparisonCriterion(String key, String type) {
        this(key, IDataComparisonType.normalize(type), null);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key  The IData key on which the comparison will be made.
     * @param type The type of comparison to be used; must be one of the enumeration values in the IDataComparisonType
     *             enumeration.
     */
    public IDataComparisonCriterion(String key, IDataComparisonType type) {
        this(key, type, null, false);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key        The IData key on which the comparison will be made.
     * @param type       The type of comparison to be used; must be one of the enumeration values in the
     *                   IDataComparisonType enumeration.
     * @param descending If true, the values associated with this key will be compared in descending order, otherwise
     *                   they will be compared in ascending order.
     */
    public IDataComparisonCriterion(String key, String type, boolean descending) {
        this(key, IDataComparisonType.normalize(type), null, descending);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key        The IData key on which the comparison will be made.
     * @param type       The type of comparison to be used; must be one of the enumeration values in the
     *                   IDataComparisonType enumeration.
     * @param descending If true, the values associated with this key will be compared in descending order, otherwise
     *                   they will be compared in ascending order.
     */
    public IDataComparisonCriterion(String key, IDataComparisonType type, boolean descending) {
        this(key, type, null, descending);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key     The IData key on which the comparison will be made.
     * @param type    The type of comparison to be used; must be one of the enumeration values in the
     *                IDataComparisonType enumeration.
     * @param pattern If the type of comparison is DATETIME or DURATION, this is the pattern to be used to parse the
     *                DATETIME or DURATION value.
     */
    public IDataComparisonCriterion(String key, String type, String pattern) {
        this(key, IDataComparisonType.normalize(type), pattern);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key     The IData key on which the comparison will be made.
     * @param type    The type of comparison to be used; must be one of the enumeration values in the
     *                IDataComparisonType enumeration.
     * @param pattern If the type of comparison is DATETIME or DURATION, this is the pattern to be used to parse the
     *                DATETIME or DURATION value.
     */
    public IDataComparisonCriterion(String key, IDataComparisonType type, String pattern) {
        this(key, type, pattern, false);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key        The IData key on which the comparison will be made.
     * @param type       The type of comparison to be used; must be one of the enumeration values in the
     *                   IDataComparisonType enumeration.
     * @param pattern    If the type of comparison is DATETIME or DURATION, this is the pattern to be used to parse the
     *                   DATETIME or DURATION value.
     * @param descending If true, the values associated with this key will be compared in descending order, otherwise
     *                   they will be compared in ascending order.
     */
    public IDataComparisonCriterion(String key, String type, String pattern, boolean descending) {
        this(key, IDataComparisonType.normalize(type), pattern, descending);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param key        The IData key on which the comparison will be made.
     * @param type       The type of comparison to be used; must be one of the enumeration values in the
     *                   IDataComparisonType enumeration.
     * @param pattern    If the type of comparison is DATETIME or DURATION, this is the pattern to be used to parse the
     *                   DATETIME or DURATION value.
     * @param descending If true, the values associated with this key will be compared in descending order, otherwise
     *                   they will be compared in ascending order.
     */
    public IDataComparisonCriterion(String key, IDataComparisonType type, String pattern, boolean descending) {
        initialize(key, type, pattern, descending);
    }

    /**
     * Constructs a new IDataComparisonCriterion object.
     *
     * @param document An IData document containing the following keys: key, type, pattern, descending?
     */
    public IDataComparisonCriterion(IData document) {
        setIData(document);
    }

    /**
     * Returns the key used to identify the values for comparison.
     *
     * @return The key used to identify the values for comparison.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the type of value being compared.
     *
     * @return The type of value being compared.
     */
    public IDataComparisonType getType() {
        return type;
    }

    /**
     * If the type of value is DATETIME or DURATION, returns the pattern used to parse the value.
     *
     * @return If the type of value is DATETIME or DURATION, this is the pattern used to parse the value.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Returns true if the comparison is in descending order.
     *
     * @return True if the comparison is in descending order.
     */
    public boolean isDescending() {
        return descending;
    }

    /**
     * Returns true if the comparison is in ascending order.
     *
     * @return True if the comparison is in ascending order.
     */
    public boolean isAscending() {
        return !isDescending();
    }

    /**
     * Returns an IData representation of this comparison criterion.
     *
     * @return An IData representation of this comparison criterion.
     */
    public IData getIData() {
        IData output = IDataFactory.create();
        IDataCursor cursor = output.getCursor();

        IDataUtil.put(cursor, "key", key);
        IDataUtil.put(cursor, "type", type.toString().toLowerCase());
        if (pattern != null) IDataUtil.put(cursor, "pattern", pattern);
        IDataUtil.put(cursor, "descending?", "" + descending);

        cursor.destroy();

        return output;
    }

    /**
     * Sets all the values of this comparison criterion object using the given IData.
     *
     * @param document An IData document containing the following keys: key, type, pattern, descending?
     */
    public void setIData(IData document) {
        if (document == null) throw new NullPointerException("document must not be null");

        IDataCursor cursor = document.getCursor();
        String key = IDataUtil.getString(cursor, "key");
        String type = IDataUtil.getString(cursor, "type");
        String pattern = IDataUtil.getString(cursor, "pattern");
        String descending = IDataUtil.getString(cursor, "descending?");
        if (descending == null) descending = IDataUtil.getString(cursor, "descending");
        cursor.destroy();

        initialize(key, type, pattern, descending);
    }

    /**
     * Initializes all the values of this comparison criterion object.
     *
     * @param key        The key to use in the comparison.
     * @param type       The type of value the key is associated with.
     * @param pattern    The pattern to use to parse the value the key is associated with.
     * @param descending True if the comparison should be in descending order.
     */
    protected void initialize(String key, String type, String pattern, String descending) {
        initialize(key, IDataComparisonType.normalize(type), pattern, BooleanHelper.parse(descending, false));
    }

    /**
     * Initializes all the values of this comparison criterion object.
     *
     * @param key        The key to use in the comparison.
     * @param type       The type of value the key is associated with.
     * @param pattern    The pattern to use to parse the value the key is associated with.
     * @param descending True if the comparison should be in descending order.
     */
    protected void initialize(String key, IDataComparisonType type, String pattern, boolean descending) {
        if (key == null) throw new NullPointerException("key must not be null");

        this.key = key;
        this.type = IDataComparisonType.normalize(type);
        this.pattern = pattern;
        this.descending = descending;
    }

    /**
     * Returns an IDataComparisonCriterion[] given an IData[].
     *
     * @param criteria The comparison criteria specified as an IData[].
     * @return An IDataComparisonCriterion[] representing the given criteria.
     */
    public static IDataComparisonCriterion[] of(IData[] criteria) {
        if (criteria == null) return null;

        IDataComparisonCriterion[] output = new IDataComparisonCriterion[criteria.length];

        for (int i = 0; i < criteria.length; i++) {
            output[i] = new IDataComparisonCriterion(criteria[i]);
        }

        return output;
    }

    /**
     * Returns a string representation of this criterion object.
     *
     * @return a string representation of this criterion object.
     */
    @Override
    public String toString() {
        return String.format(">>>%s:key=%s,type=%s,pattern=%s,descending=%s<<<", this.getClass().getSimpleName(), key, type, pattern, descending);
    }
}
