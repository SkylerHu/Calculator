package com.skyler.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;

/**
 * 计算一个正确的字符串形式运算式
 * @author HLP
 *
 */
public class MathUtil {
    /**
     * 数据栈
     */
    Stack<BigDecimal> number;
    /**
     * 符号栈
     */
    Stack<Character> operator;
    /**
     * 计算除法时，设置的结果标度
     */
    int length;

    public MathUtil(int length) {
        this.length = length;
    }

    /**
     * 获得计算结果的字符串表示形式
     * @param exp
     * @return
     */
    public String getResult(String exp) {
        this.number = new Stack<BigDecimal>();
        this.operator = new Stack<Character>();
        calculate(exp);
        return this.number.peek().toString();
    }

    /**
     * 对运算式exp进行解析，并计算
     * @param exp
     */
    public void calculate(String exp) {
        int index = 0;
        int sign = 0;
        while (index < exp.length()) {
            char op = exp.charAt(index);
            if ((op <= '9' && op >= '0') || op == '.') {
                index++;
            } else {
                if (index > 0) {
                    char opp = exp.charAt(index-1);
                    if (op == '-' && !((opp <= '9' && opp >= '0') || opp == '.')) {
                        index++;
                        continue;
                    }
                }
                if (sign != index) {
                    String numStr = exp.substring(sign, index);
                    BigDecimal num = new BigDecimal(numStr);
                    number.push(num);
                }
                analysis(op);
                sign = ++index;
            }
        }
    }

    /**
     * 对得到的数据和符号进行分析处理
     * @param op
     */
    public void analysis(char op) {
        if (operator.empty() || comparePriority(op, operator.peek())) {
            operator.push(op);
        } else {
            makeNum(op);
        }
    }

    /**
     * 计算
     * @param op
     */
    private void makeNum(char op) {
        BigDecimal result = null;
        while (!operator.empty() && !comparePriority(op, operator.peek())) {
            char op2 = operator.pop();
            if (op == ')' && op2 == '(') {
                return;
            }
            BigDecimal num1 = null;
            BigDecimal num2 = number.pop();
            if (number.empty()) {
                num1 = new BigDecimal(0);
            } else {
                num1 = number.pop();
            }
            switch (op2) {
                case '+':
                    result = num1.add(num2);
                    break;
                case '-':
                    result = num1.subtract(num2);
                    break;
                case '*':
                    result = num1.multiply(num2);
                    break;
                case '/':
                    result = num1.divide(num2, new MathContext(length));
                    break;
                default:
                    operator.pop();
                    break;
            }
            number.push(result);
        }
        if (op != ')') {
            operator.push(op);
        }
    }

    /**
     * 判断优先级：true(c1优先级高于c2)、false(c1优先级低于c2)
     * @param c1
     * @param c2
     * @return
     */
    public boolean comparePriority(char c1, char c2) {
        if (c1 == '=') {
            return false;
        } else if (c1 == ')') {
            return false;
        } else if (c1 == '(') {
            return true;
        } else if (c2 == '(') {
            return true;
        } else if ((c1 == '*' || c1 == '/') && (c2 == '-' || c2 == '+')) {
            return true;
        } else {
            return false;
        }

    }

}
