package app.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;

/**
 * my first verticle.
 * @author ramkumarsundarajan
 *
 */
public class MyFirstVerticle extends AbstractVerticle{
	
	public void start(Future<Void> future){
		
		HttpServer httpServer = vertx.createHttpServer();
		httpServer.requestHandler(r->r.response().end("<h1>Hello from my first " +
              "Vert.x 3 application</h1>"));
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
