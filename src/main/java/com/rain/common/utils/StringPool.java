package com.rain.common.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符串常量池
 * <p>
 * 提供常用的字符串常量，避免重复创建字符串对象，提高性能和代码可读性。
 * 所有常量均为 public static final，可直接通过类名访问。
 *
 * @author xueyu
 */
public interface StringPool {

    // ---------------------------------------------------------------- 基础符号

    String AMPERSAND = "&";
    String AND = "and";
    String AT = "@";
    String ASTERISK = "*";
    String BACK_SLASH = "\\";
    String COLON = ":";
    String COMMA = ",";
    String DASH = "-";
    String DOLLAR = "$";
    String DOT = ".";
    String DOTDOT = "..";
    String EMPTY = "";
    String EQUALS = "=";
    String SLASH = "/";
    String HASH = "#";
    String HAT = "^";
    String LEFT_BRACE = "{";
    String LEFT_BRACKET = "(";
    String LEFT_CHEV = "<";
    String NEWLINE = "\n";
    String DOT_NEWLINE = ",\n";
    String PERCENT = "%";
    String PIPE = "|";
    String PLUS = "+";
    String QUESTION_MARK = "?";
    String EXCLAMATION_MARK = "!";
    String QUOTE = "\"";
    String RETURN = "\r";
    String TAB = "\t";
    String RIGHT_BRACE = "}";
    String RIGHT_BRACKET = ")";
    String RIGHT_CHEV = ">";
    String SEMICOLON = ";";
    String SINGLE_QUOTE = "'";
    String BACKTICK = "`";
    String SPACE = " ";
    String TILDA = "~";
    String LEFT_SQ_BRACKET = "[";
    String RIGHT_SQ_BRACKET = "]";
    String UNDERSCORE = "_";
    String CRLF = "\r\n";
    String DOUBLE_SLASH = "//";

    // ---------------------------------------------------------------- 文件扩展名

    String DOT_CLASS = ".class";
    String DOT_JAVA = ".java";
    String DOT_XML = ".xml";
    String DOT_JSON = ".json";
    String DOT_TXT = ".txt";

    // ---------------------------------------------------------------- 布尔值

    String TRUE = "true";
    String FALSE = "false";
    String YES = "yes";
    String NO = "no";
    String ON = "on";
    String OFF = "off";
    String Y = "y";
    String N = "n";

    // ---------------------------------------------------------------- 数字

    String ZERO = "0";
    String ONE = "1";
    String TWO = "2";
    String TEN = "10";

    // ---------------------------------------------------------------- 特殊字符串

    String NULL = "null";
    String NUM = "NUM";
    String SQL = "sql";
    String DOLLAR_LEFT_BRACE = "${";
    String HASH_LEFT_BRACE = "#{";

    // ---------------------------------------------------------------- 字符集

    String UTF_8 = "UTF-8";
    String US_ASCII = "US-ASCII";
    String ISO_8859_1 = "ISO-8859-1";
    Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;

    // ---------------------------------------------------------------- HTML 实体

    String HTML_NBSP = "&nbsp;";
    String HTML_AMP = "&amp;";
    String HTML_QUOTE = "&quot;";
    String HTML_LT = "&lt;";
    String HTML_GT = "&gt;";

    // ---------------------------------------------------------------- JSON

    String JSON_EMPTY_OBJECT = "{}";
    String JSON_EMPTY_ARRAY = "[]";

    // ---------------------------------------------------------------- HTTP

    String HTTP = "http://";
    String HTTPS = "https://";
    String LOCALHOST = "localhost";

    // ---------------------------------------------------------------- 时间格式

    String DATE_PATTERN = "yyyy-MM-dd";
    String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    String TIME_PATTERN = "HH:mm:ss";
    String DATETIME_PATTERN_MILLS = "yyyy-MM-dd HH:mm:ss.SSS";

    // ---------------------------------------------------------------- 数组

    String[] EMPTY_ARRAY = new String[0];

    /**
     * UTF-8 编码的换行符字节数组
     * 注意: 此数组为只读，请勿修改其内容
     */
    byte[] BYTES_NEW_LINE = "\n".getBytes(StandardCharsets.UTF_8);
}
