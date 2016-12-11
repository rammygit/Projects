package com.project.dashboard.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class WebController extends AbstractVerticle{ 
	
	@Override
	public void start(Future<Void> future){
		
		int port = config().getInteger("http.port", 8080);
		
		Router router = Router.router(vertx);
				
		addRoutes(router);
		
		getHttpServer(port, future,router);
	}
	
	private void addRoutes(Router router){
		router.route("/").handler(routingcontext->{
			HttpServerResponse httpServerResponse = routingcontext.response();
			httpServerResponse.putHeader("content-type", "text/html").end("<p> <i>welcome to my home page</i> </p>");
		});
		router.route("/assets/*").handler(StaticHandler.create("assets").setCachingEnabled(false));

	}
	
	
	private HttpServer getHttpServer(int port,Future<Void> future,Router router ){
		
		HttpServer server = vertx.createHttpServer().requestHandler(router::accept).listen(port, result->{
			
			if(result.succeeded()){
				future.complete();
			} else {
				future.fail(result.cause());
			}
		});
		
		return server;
	}

}
