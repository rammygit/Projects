package app.verticle;

import java.util.LinkedHashMap;
import java.util.Map;

import app.verticle.modal.Whisky;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * my first verticle.
 * @author ramkumarsundarajan
 *
 */
public class MyFirstVerticle extends AbstractVerticle{

	private Map<Integer, Whisky> products = new LinkedHashMap<>();

	public void start(Future<Void> future){

		/**
		 * load some data
		 */
		createSomeData();

		/**
		 * router config. 
		 */
		Router router = Router.router(vertx);
		router.route("/").handler(routingContext ->{

			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html")
			.end("<h1>Hello from my first Vert.x 3 application</h1>");
		});

		router.get("/api/whiskies").handler(this::getAll);

		// Serve static resources from the /assets directory
		router.route("/assets/*").handler(StaticHandler.create("assets"));

		router.route("/api/whiskies*").handler(BodyHandler.create());
		router.post("/api/whiskies").handler(this::addOne);
		router.delete("/api/whiskies/:id").handler(this::deleteOne);

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


	/**
	 * 
	 */
	private void createSomeData() {
		Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
		products.put(bowmore.getId(), bowmore);
		Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
		products.put(talisker.getId(), talisker);
	}

	/**
	 * 
	 * @param routingContext
	 */
	private void getAll(RoutingContext routingContext) {
		routingContext.response()
		.putHeader("content-type", "application/json; charset=utf-8")
		.end(Json.encodePrettily(products.values()));
	}

	/**
	 * 
	 * @param routingContext
	 */
	private void addOne(RoutingContext routingContext) {
		final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(),
				Whisky.class);
		products.put(whisky.getId(), whisky);
		routingContext.response()
		.setStatusCode(201)
		.putHeader("content-type", "application/json; charset=utf-8")
		.end(Json.encodePrettily(whisky));
	}
	
	/**
	 * 
	 * @param routingContext
	 */
	private void deleteOne(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		if (id == null) {
			routingContext.response().setStatusCode(400).end();
		} else {
			Integer idAsInteger = Integer.valueOf(id);
			products.remove(idAsInteger);
		}
		routingContext.response().setStatusCode(204).end();
	}
}
