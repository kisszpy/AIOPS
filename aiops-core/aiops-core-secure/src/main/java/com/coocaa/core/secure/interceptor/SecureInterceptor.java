/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coocaa.core.secure.interceptor;

import com.coocaa.core.secure.utils.SecureUtil;
import com.coocaa.core.tool.api.R;
import com.coocaa.core.tool.api.ResultCode;
import com.coocaa.core.tool.constant.AiOpsConstant;
import com.coocaa.core.tool.jackson.JsonUtil;
import com.coocaa.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt拦截器校验
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class SecureInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//		if (null != SecureUtil.getUser()) {
//			return true;
//		} else {
//			log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request), JsonUtil.toJson(request.getParameterMap()));
//			R result = R.fail(ResultCode.UN_AUTHORIZED);
//			response.setCharacterEncoding(AiOpsConstant.UTF_8);
//			response.setHeader(AiOpsConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_UTF8_VALUE);
//			response.setStatus(HttpServletResponse.SC_OK);
//			try {
//				response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
//			} catch (IOException ex) {
//				log.error(ex.getMessage());
//			}
//			return false;
//		}
		return true;
	}

}
