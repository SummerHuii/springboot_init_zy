package top.qtcc.qiutuanallpowerfulspringboot.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 防止XSS攻击
 *
 * @author qiutuan
 * @date 2024/12/07
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 重写getParameter方法，对获取的参数值进行HTML转义，防止XSS攻击
     *
     * @param name 参数名
     * @return {@link String }
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return StringUtils.isNotBlank(value) ? StringEscapeUtils.escapeHtml4(value) : value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapeValues[i] = StringUtils.isNotBlank(values[i]) ?
                        StringEscapeUtils.escapeHtml4(values[i]) : values[i];
            }
            return escapeValues;
        }
        return null;
    }
}