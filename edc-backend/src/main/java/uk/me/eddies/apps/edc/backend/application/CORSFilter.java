package uk.me.eddies.apps.edc.backend.application;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;

@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {
	
	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		if (preflightRequest(request)) {
			request.abortWith(Response.ok().build());
			return;
		}
	}

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		if (!crossOriginRequest(request)) {
			return;
		}

		response.getHeaders().add("Access-Control-Allow-Origin", "*");
		if (preflightRequest(request)) {
			// Further CORS headers needed
			response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
		}
	}

	private static boolean preflightRequest(ContainerRequestContext request) {
		return crossOriginRequest(request) && "OPTIONS".equalsIgnoreCase(request.getMethod());
	}

	private static boolean crossOriginRequest(ContainerRequestContext request) {
		return request.getHeaderString("Origin") != null;
	}
}
