package movie.filters

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.*

@Component
@Order(1)
class RequestResponseLoggingFilter: Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val req: HttpServletRequest = request as HttpServletRequest
        val res: HttpServletResponse = response as HttpServletResponse
        println("Request ${req.method} : ${req.requestURI}")
        chain?.doFilter(request, response)
        println("Response ${res.status} : ${res.contentType}")
    }
}