package app.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * my first verticle.
 * @author ramkumarsundarajan
 *
 */
public class MyFirstVerticle extends AbstractVerticle{
	
	public void start(Future<Void> future){
		
		/**
		 * router config. 
		 */
		Router router = Router.router(vertx);
		router.route("/").handler(routingContext ->{
			
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html")
		       .end("<h1>Hello from my first Vert.x 3 application</h1>");
		});
		
		HttpServer httpServer = vertx.createHttpServer();
		httpServer.requestHandler(router::accept);
		httpServer.listen(
				 // Retrieve the port from the configuration,
		          // default to 8080.
		          config().getInteger("http.port", 8080),result->{
			if (result.succeeded()) {
	            future.complete();
	          } else {
	            future.fail(result.cause());
	          }
		});
	}

}
