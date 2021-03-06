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

package permafrost.tundra.flow;

import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.lang.flow.ExpressionEvaluator;
import com.wm.lang.flow.MalformedExpressionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Performs webMethods Integration Server flow language conditional statement evaluation against a specified scope.
 */
public class ConditionEvaluator {
    /**
     * Regular expressions to detect backwards-compatibility mode to support the previous ANTLR-based implementation of
     * the evaluate function, which allowed use of the key words: null, true and false
     */
    protected static final Pattern NULL_PATTERN = Pattern.compile("((=|==|!=|<>|>|>=|<|<=)\\s*null(\\s|$))|((^|\\s)null\\s*(=|==|!=|<>|>|>=|<|<=))");
    protected static final Pattern TRUE_PATTERN = Pattern.compile("((=|==|!=|<>|>|>=|<|<=)\\s*true(\\s|$))|((^|\\s)true\\s*(=|==|!=|<>|>|>=|<|<=))");
    protected static final Pattern FALSE_PATTERN = Pattern.compile("((=|==|!=|<>|>|>=|<|<=)\\s*false(\\s|$))|((^|\\s)false\\s*(=|==|!=|<>|>|>=|<|<=))");

    /**
     * The conditional statement to be evaluated by this object.
     */
    protected String condition;

    /**
     * Constructs a new flow condition.
     *
     * @param condition The conditional statement to be evaluated.
     */
    public ConditionEvaluator(String condition) {
        this.condition = condition;
    }

    /**
     * Evaluates the conditional statement against the given scope.
     *
     * @param scope The scope against which the conditional statement is evaluated.
     * @return True if the conditional statement evaluates to true, otherwise false.
     */
    public boolean evaluate(IData scope) {
        boolean result = true;

        if (condition != null) {
            Matcher nullMatcher = NULL_PATTERN.matcher(condition);
            Matcher trueMatcher = TRUE_PATTERN.matcher(condition);
            Matcher falseMatcher = FALSE_PATTERN.matcher(condition);

            boolean nullFound = nullMatcher.find();
            boolean trueFound = trueMatcher.find();
            boolean falseFound = falseMatcher.find();

            boolean backwardsCompatibilityRequired = nullFound || trueFound || falseFound;

            if (scope == null) {
                scope = IDataFactory.create();
            } else {
                if (backwardsCompatibilityRequired) scope = IDataUtil.clone(scope);
            }

            if (backwardsCompatibilityRequired) {
                IDataCursor cursor = scope.getCursor();
                if (nullFound) IDataUtil.put(cursor, "null", null);
                if (trueFound) IDataUtil.put(cursor, "true", "true");
                if (falseFound) IDataUtil.put(cursor, "false", "false");
                cursor.destroy();
            }

            try {
                result = ExpressionEvaluator.evalToBoolean(condition, scope);
            } catch (MalformedExpressionException ex) {
                throw new IllegalArgumentException(ex);
            }
        }

        return result;
    }

    /**
     * Evaluates the conditional statement against the given scope. This is a convenience method which constructs a new
     * Condition object and evaluates it.
     *
     * @param condition The conditional statement to be evaluated.
     * @param scope     The scope against which the conditional statement is evaluated.
     * @return True if the conditional statement evaluates to true, otherwise false.
     */
    public static boolean evaluate(String condition, IData scope) {
        ConditionEvaluator evaluator = new ConditionEvaluator(condition);
        return evaluator.evaluate(scope);
    }
}

