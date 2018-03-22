/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog.plugins.pipelineprocessor.ast.expressions;

import org.antlr.v4.runtime.Token;
import org.graylog.plugins.pipelineprocessor.EvaluationContext;

public class DoubleExpression extends ConstantExpression implements NumericExpression {
    private final double value;

    public DoubleExpression(Token start, double value) {
        super(start, Double.class);
        this.value = value;
    }

    @Override
    public Object evaluateUnsafe(EvaluationContext context) {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean isIntegral() {
        return false;
    }

    @Override
    public long evaluateLong(EvaluationContext context) {
        return (long) value;
    }

    @Override
    public double evaluateDouble(EvaluationContext context) {
        return value;
    }
}
