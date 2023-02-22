package simpleapp.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class CORSFilter extends OncePerRequestFilter{
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
	        //logger.info("Adding CORS Headers ........................");
	        ThreadContext.put("order.id",UUID.randomUUID().toString());
	        logger.info("checking {}",ThreadContext.get("order.id"));
	        res.setHeader("Access-Control-Allow-Origin", "*");
	        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
	        res.setHeader("Access-Control-Allow-Headers", "*");
	        res.setHeader("Access-Control-Max-Age", "3600");
	        chain.doFilter(req, res);
	        logger.info("checking {}",ThreadContext.get("order.id"));
	        ThreadContext.clearAll();
	    }
}
